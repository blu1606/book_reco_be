# Sử dụng image OpenJDK chính thức
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file pom.xml và source code vào container
COPY pom.xml ./
COPY src ./src
COPY mvnw ./
COPY mvnw.cmd ./
COPY .mvn ./.mvn

# Cấp quyền thực thi cho mvnw
RUN chmod +x mvnw

# Tải dependencies trước (tận dụng cache)
RUN ./mvnw dependency:resolve

# Build ứng dụng
RUN ./mvnw package -DskipTests

# Copy file jar đã build ra ngoài
RUN cp target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"] 