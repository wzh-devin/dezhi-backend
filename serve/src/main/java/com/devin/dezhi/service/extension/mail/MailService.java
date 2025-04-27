package com.devin.dezhi.service.extension.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 2025/4/27 17:18.
 *
 * <p>
 *     邮箱服务
 * </p>
 * @author <a href="https://github.com/wzh-devin">devin</a>
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮箱验证码.
     * @param to 发送到的邮箱
     * @param code 验证码
     */
    public void sendEmailCode(final String to, final Integer code) {
        try {
            mailSender.send(generateMailMessage(to, code));
        } catch (MessagingException e) {
            log.error("邮件信息构建异常，{} :: 邮件发送失败，请重试！！！", e.getMessage());
        }
    }

    /**
     * 生成邮件信息.
     * @param to 发送到的目标邮箱
     * @param code 验证码
     * @return SimpleMailMessage
     */
    private MimeMessage generateMailMessage(final String to, final Integer code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String htmlContent = """
                <div style="
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    max-width: 600px;
                    margin: 20px auto;
                    padding: 30px;
                    border-radius: 10px;
                    background: #f8f9fa;">
                
                    <p style="font-size: 16px; color: #666;">
                        您的验证码为（有效期为一分钟）
                    </p>
                
                    <div style="
                        background: #e8f4ff;
                        padding: 20px;
                        margin: 30px 0;
                        border-radius: 8px;
                        text-align: center;
                        border: 2px dashed #4a90e2;">
                
                        <span style="
                            font-size: 32px;
                            font-weight: 700;
                            color: #2c3e50;
                            letter-spacing: 4px;">
                            %s
                        </span>
                    </div>
                
                    <p style="font-size: 14px; color: #999; text-align: center;">
                        * 请勿将此验证码分享给他人
                    </p>
                
                </div>
                """.formatted(code);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("DeZhi 博客验证码");
        helper.setText(htmlContent, true);

        return message;
    }
}
