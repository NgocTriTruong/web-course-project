# Bước 1: Dùng Maven + Java 17 để build WAR
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

# Bước 2: Chạy WAR bằng Tomcat 10.1.31 (Java 17)
FROM tomcat:10.1.31-jdk17-temurin

# Xoá ứng dụng mặc định
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR đã build sang Tomcat
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
