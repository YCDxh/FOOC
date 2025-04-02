# 使用官方 JDK 基础镜像
FROM openjdk:8-jdk-alpine

# 复制 JAR 包到镜像中 将 your-project.jar 替换为你的 JAR 文件名
COPY backend-0.0.1-SNAPSHOT.jar backend-0.0.1-SNAPSHOT.jar

# 暴露应用端口（根据实际端口修改）
EXPOSE 8081

# 设置启动命令
ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]

