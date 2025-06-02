# Bước 1: Build ứng dụng bằng Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder
# Đặt thư mục làm việc là /app
WORKDIR /app
# Sao chép toàn bộ nội dung từ thư mục gốc của build context
COPY . .
# Chuyển thư mục làm việc đến AnimalFeed_webApp/ nơi có pom.xml
WORKDIR /app/AnimalFeed_webApp
# Chạy lệnh Maven để build
RUN mvn clean package -DskipTests

# Bước 2: Chạy WAR bằng Tomcat 10.1.31 (Java 17)
FROM tomcat:10.1.31-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
# Sao chép file WAR từ giai đoạn builder
COPY --from=builder /app/AnimalFeed_webApp/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]