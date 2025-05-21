# 基础镜像
FROM openjdk:17-jdk

# 作者
LABEL devin=<wzh.devin@gmail.com>

# 工作区
WORKDIR /app
COPY ./serve/target/dezhi-serve.jar ./app.jar

# 设置时区
ENV TZ=Asia/Shanghai
ENV LANG=C.UTF-8

# 设置暴露端口
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]