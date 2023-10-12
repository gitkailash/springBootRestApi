# Spring Boot REST API Project

This is a simple Spring Boot project for building and running a RESTful API. The API allows you to manage users, including adding, updating, deleting, and retrieving user information.

## Project Structure

- `src/main/java`: Contains the Java source code for the project.
  - `com.spring.restapi.restapi`: The root package for the application.
    - `controller`: Contains the API controllers for managing users.
    - `dto`: Data Transfer Objects for mapping between API requests and database entities.
    - `exception`: Custom exceptions for handling errors.
    - `service`: Business logic and service classes.
  - `RestapiApplication.java`: The main Spring Boot application class.
  - `docConfig`: Swagger configuration.
- `src/main/resources`: Contains application configuration files.
  - `application.properties`: Application-specific configuration properties.
- `pom.xml`: Maven project configuration.

## Dependencies

- Spring Boot: The core framework for building Java applications.
- Springfox: Used for integrating Swagger 2 for API documentation.
- Springdoc: Another option for OpenAPI documentation.
- Other dependencies are managed through Maven.

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/gitkailash/springBootRestApi.git
   cd springBootRestApi
   
2. Build and run the application:
  mvn spring-boot:run


3. Access the Swagger UI for API documentation:
  Swagger UI URL: http://localhost:8080/swagger-ui/index.html
  **API Endpoints**
  /api/users: Get all users (GET).
  /api/users/{userId}: Get a user by ID (GET).
  /api/users: Add a new user (POST).
  /api/users: Update an existing user (PUT).
  /api/users/{userId}: Delete a user by ID (DELETE).

