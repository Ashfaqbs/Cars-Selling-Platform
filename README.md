# Car Selling Application

This application is designed to manage a car selling platform with different roles such as `USER`, `SELLER`, and `ADMIN`. The application uses JWT for authentication and allows various operations based on user roles.

## Features

- **Public APIs**:
  - Welcome message.
  - User registration (for both `SELLER` and `BUYER` roles only ).

- **Authenticated APIs**:
  - `SELLER` APIs for creating, reading, updating, and deleting cars.
  - `BUYER` APIs for viewing car listings.
  - `ADMIN` APIs for managing users.

- **Security**:
  - JWT-based authentication.
  - Role-based authorization.


### Authentication
- JWT: The application uses JWT for authentication. Tokens must be passed in the Authorization header as Bearer <token>.
- Basic Auth: Basic authentication is not allowed as per the final configuration.


### How to Run
- Clone the repository.
- Configure the database settings in application.properties.
- Run the Spring Boot application using:
```
mvn spring-boot:run
```
Use Postman or any other API testing tool to interact with the endpoints.


### Notes
- Only SELLERS can perform CRUD operations on cars they own.
- BUYERS can only view cars.
- ADMINS have full access to user management.
- The application restricts the registration of ADMIN roles via the public registration API.