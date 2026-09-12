# ISPL Device Repair Services & Careers Platform

A server-side rendered Spring Boot monolith for device repair services, job listings, and admin management. The platform is built with Java 21, Spring Boot, Thymeleaf, MySQL, Flyway, and basic Razorpay integration.

## Features

- Responsive storefront for device categories and repair services
- Admin dashboard for CRUD of devices, nested services, careers, and about content
- BCrypt-backed user authentication with role-based access control
- REST API for public and admin data access
- Flyway migrations and seed data for device categories, service catalog, careers, company info, and default admin
- Razorpay payment stub with GST-aware order creation and callback handling
- OpenAPI docs via SpringDoc

## Prerequisites

- Java 21
- Maven 3.9+
- MySQL 8+
- Optional: Razorpay account credentials

## Local execution

1. Create MySQL database
   ```sql
   CREATE DATABASE ispl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   CREATE USER 'ispl_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON ispl.* TO 'ispl_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

2. Copy the template file and configure your environment
   ```bash
   cp application-template.properties src/main/resources/application.properties
   ```
   Update database and Razorpay values inside the file.

3. Run the app
   ```bash
   ./mvnw spring-boot:run
   ```

4. Open the app
   - Storefront: http://localhost:8080/
   - Admin: http://localhost:8080/login
   - OpenAPI UI: http://localhost:8080/swagger-ui/index.html

## Default admin account

- Username: admin
- Password: Admin@123
- Email: admin@ispl.local

This account is created by the Flyway seed script using a BCrypt hash.

## Environment variables

The application will also fall back to H2 in-memory storage if you do not set DB_* variables. For a real MySQL setup, use:

```bash
export DB_URL="jdbc:mysql://localhost:3306/ispl?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export DB_USERNAME="ispl_user"
export DB_PASSWORD="your_password"
export DB_DRIVER="com.mysql.cj.jdbc.Driver"
export RAZORPAY_KEY_ID="your_key_id"
export RAZORPAY_KEY_SECRET="your_key_secret"
```

## Validation

```bash
./mvnw test
```

This project is designed as a robust monolithic application with server-side rendering and a secure admin workflow, while remaining easy to run locally for demos and feature development.
