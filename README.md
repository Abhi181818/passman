# Passman

A simple password manager REST API built with Spring Boot. This project allows you to securely store, retrieve, and manage encrypted passwords for different services, using a local JSON file as storage. No database is required.

## Features
- Add a password for a service (password is encrypted before storage)
- Retrieve the encrypted password for a service
- All data is stored in a local `passwords.json` file
- No database required
- Endpoints are open (no authentication) for demo purposes
- Built with Java 17 and Spring Boot 3

## Project Structure
```
passman/
├── src/
│   ├── main/
│   │   ├── java/com/passman/passman/
│   │   │   ├── PassmanApplication.java         # Main Spring Boot application
│   │   │   ├── config/SecurityConfig.java      # Security configuration (open endpoints)
│   │   │   ├── controller/PasswordController.java # REST API endpoints
│   │   │   ├── dto/AddPasswordRequest.java     # DTO for add password requests
│   │   │   ├── model/PasswordEntry.java        # Password entry model
│   │   │   ├── service/PasswordService.java    # Business logic and file storage
│   │   │   └── util/EncryptUtil.java           # AES encryption/decryption
│   │   └── resources/
│   │       └── application.properties          # Spring Boot config
│   └── test/
│       └── java/com/passman/passman/PassmanApplicationTests.java
├── passwords.json                              # Local password storage
├── pom.xml                                     # Maven dependencies
└── README.md                                   # Project documentation
```

## Requirements
- Java 17 or higher
- Maven 3.6+

## Getting Started

### 1. Clone the repository
```sh
git clone https://github.com/Abhi181818/passman.git
cd passman
```

### 2. Build the project
```sh
./mvnw clean package
```

### 3. Run the application
```sh
./mvnw spring-boot:run
```
The server will start on `http://localhost:8080` by default.

## API Endpoints

### Add a Password
- **URL:** `POST /api/passwords/add`
- **Content-Type:** `application/json`
- **Request Body:**
  ```json
  {
    "service": "example.com",
    "username": "yourusername",
    "password": "yourpassword"
  }
  ```
- **Response:**
  - `"Password added successfully!"` on success

#### Example (curl):
```sh
curl -X POST http://localhost:8080/api/passwords/add \
  -H "Content-Type: application/json" \
  -d '{"service":"example.com","username":"yourusername","password":"yourpassword"}'
```

### Get Encrypted Password
- **URL:** `GET /api/passwords/get?service=<serviceName>`
- **Response:**
  - The encrypted password string, or `Service not found` if not present

#### Example (curl):
```sh
curl "http://localhost:8080/api/passwords/get?service=example.com"
```

## How It Works
- Passwords are encrypted using AES before being stored in `passwords.json`.
- When you add a password, the plaintext password is encrypted and saved.
- When you retrieve a password, you get the encrypted string (not the plaintext password).
- The encryption key is hardcoded for demo purposes (see `EncryptUtil.java`).

## Security Notes
- **This project is for learning/demo purposes only.**
- The encryption key is hardcoded and should not be used in production.
- Endpoints are open (no authentication). For real use, add authentication and secure the key.

## Customization
- To change the encryption key, edit `EncryptUtil.java`.
- To add authentication, update `SecurityConfig.java`.
- To use a database, refactor `PasswordService.java` and update dependencies.

## License
This project is open source and available under the MIT License.
