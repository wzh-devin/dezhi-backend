# 基础镜像
FROM registry.cn-hangzhou.aliyuncs.com/wzh-devin/eclipse-temurin:17

# 作者
LABEL devin=<wzh.devin@gmail.com>

# 工作区
WORKDIR /app
COPY /serve/target/dezhi-serve-0.0.1.jar ./dezhi-serve-0.0.1.jar

# 修改容器时区
RUN rm -f /etc/localtime \
&& ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo "Asia/Shanghai" > /etc/timezone

# 设置暴露端口
EXPOSE 13001

CMD ["sh", "-c", "java ${JAVA_OPTS} -jar dezhi-serve-0.0.1.jar"]