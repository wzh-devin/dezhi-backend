package com.devin.dezhi.common.utils;

import cn.hutool.crypto.digest.DigestUtil;
import com.devin.dezhi.model.FileInfo;
import com.devin.dezhi.exception.VerifyException;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.SetBucketPolicyArgs;
import io.minio.http.Method;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 2025/6/1 17:09.
 *
 * <p></p>
 *
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MinioTemplate {

    /**
     * 公共读取权限策略，用来设置统一的URL访问.
     */
    private static final String POLICY_PUBLIC_READ = """
            {
               "Version": "2012-10-17",
               "Statement": [
                 {
                   "Effect": "Allow",
                   "Principal": "*",
                   "Action": [
                       "s3:GetObject",
                       "s3:GetObjectVersion"
                   ],
                   "Resource": ["arn:aws:s3:::%s/*"]
                 }
               ]
             }
            """;

    /**
     * 公开读权限URL.
     */
    private static final String PUBLIC_READ_URL = "http://%s/%s/%s";

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.bucket-name}")
    private String bucketName;

    private final MinioClient minioClient;

    /**
     * 初始化存储桶.
     */
    @PostConstruct
    public void init() throws Exception {
        createCustomBucket();
    }

    /**
     * 创建自定义策略存储桶.
     */
    public void createCustomBucket() throws Exception {
        createDefaultBucket();
        // 设置自定义权限
        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(String.format(POLICY_PUBLIC_READ, bucketName))
                .build());
    }

    /**
     * 创建默认存储桶.
     */
    public void createDefaultBucket() throws Exception {
        // 判断存储桶是否存在
        if (hasBucket()) {
            return;
        }
        // 创建存储桶
        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucketName)
                .skipValidation(true)
                .build());
    }

    /**
     * 上传文件.
     *
     * @param fileInfo 文件信息
     * @return URL
     */
    public String uploadFile(final FileInfo fileInfo) throws Exception {
        // 判断存储桶是否存在
        if (!hasBucket()) {
            throw new VerifyException("存储桶不存在，请重新输入桶名...");
        }
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .contentType(fileInfo.getContentType())
                .object(fileInfo.getFileName())
                .stream(fileInfo.getInputStream(), fileInfo.getSize(), -1)
                .build());
        return generatePublicReadUrl(fileInfo.getFileName());
    }

    /**
     * 上传文件并获取临时访问地址.
     *
     * @param fileInfo 文件信息
     * @return URL
     */
    public String uploadFileAndGetTempUrl(final FileInfo fileInfo) throws Exception {
        // 判断存储桶是否存在
        if (!hasBucket()) {
            throw new VerifyException("存储桶不存在，请重新输入桶名...");
        }
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .contentType(fileInfo.getContentType())
                .object(fileInfo.getFileName())
                .stream(fileInfo.getInputStream(), fileInfo.getSize(), -1)
                .build());
        return generateTempUrl(fileInfo.getFileName(), fileInfo.getExpireTime());
    }

    /**
     * 从MinIO下载文件并返回字节数组.
     *
     * @param key 对象键
     * @return 文件字节数组
     */
    public byte[] downloadAsBytes(final String key) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;

        try (InputStream inputStream = downloadAsStream(key)) {
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        }

        return baos.toByteArray();
    }

    /**
     * 获取文件的输入流（适用于大文件，避免全部加载到内存）.
     *
     * @param key 对象键
     * @return 文件输入流
     */
    public InputStream downloadAsStream(final String key) throws Exception {
        // 判断存储桶是否存在
        if (!hasBucket()) {
            throw new VerifyException("存储桶不存在，请重新输入桶名...");
        }
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build());
    }

    /**
     * 删除存储桶.
     */
    public void delBucket() throws Exception {
        if (!hasBucket()) {
            return;
        }
        minioClient.removeBucket(RemoveBucketArgs.builder()
                .bucket(bucketName)
                .build());
    }

    /**
     * 删除文件.
     *
     * @param key Minio中存储的文件名
     */
    public void delFile(final String key) throws Exception {
        if (!hasFile(key)) {
            return;
        }
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build());
    }

    /**
     * 生成公开读权限URL.
     *
     * @param key Minio中存储的文件名
     * @return public-read URL
     */
    public String generatePublicReadUrl(final String key) throws Exception {
        if (!hasFile(key)) {
            throw new VerifyException("文件不存在，请重新输入文件名...");
        }
        return String.format(PUBLIC_READ_URL, endpoint, bucketName, key);
    }

    /**
     * 获取临时URL.
     *
     * @param key        Minio中存储的文件名
     * @param expireTime 过期时间 (SECONDS秒级)
     * @return 临时URL
     */
    public String generateTempUrl(final String key, final int expireTime) throws Exception {
        if (!hasFile(key)) {
            throw new VerifyException("文件不存在，请重新输入文件名...");
        }
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .method(Method.GET)
                .object(key)
                .expiry(expireTime, TimeUnit.SECONDS)
                .build());
    }

    /**
     * 判断文件是否存在.
     *
     * @param fileName 文件名
     * @return Boolean
     */
    public Boolean hasFile(final String fileName) throws Exception {
        if (!hasBucket()) {
            throw new VerifyException("存储桶不存在，请重新输入桶名...");
        }
        Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(bucketName)
                .build());
        for (Result<Item> item : objects) {
            String name = item.get().objectName();
            if (name.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断存储桶是否存在.
     *
     * @return Boolean
     */
    public boolean hasBucket() throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 生成文件的md5值.
     *
     * @param bytes 字节数组
     * @return md5
     */
    public String generateMd5(final byte[] bytes) {
        return DigestUtil.md5Hex(bytes);
    }
}
