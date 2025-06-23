# Passman

A simple, user-centric password manager REST API built with Spring Boot. Each user's passwords are securely encrypted and stored in a user-specific JSON file, never shared or exposed to other users. No database is required.

## Features
- Add a password for a service (password is encrypted before storage)
- Retrieve the encrypted password for a service (per user)
- Retrieve the original (decrypted) password for a service and username (per user)
- List all password entries for the current user
- Set or load the password storage file location (user-defined JSON file, per user)
- All data is stored in a local `.json` file per user
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
├── passwords_<username>.json                   # Local password storage (per user, default)
├── pom.xml                                     # Maven dependencies
├── Dockerfile                                  # Docker build file
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

## API Endpoints (All endpoints require a `username` parameter)

### Add a Password
- **URL:** `POST /api/passwords/add?username=<username>`
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
curl -X POST "http://localhost:8080/api/passwords/add?username=abhi18" \
  -H "Content-Type: application/json" \
  -d '{"service":"example.com","username":"yourusername","password":"yourpassword"}'
```

### Get Encrypted Password
- **URL:** `GET /api/passwords/get?username=<username>&service=<serviceName>`
- **Response:**
  - The encrypted password string, or `Service not found` if not present

#### Example (curl):
```sh
curl "http://localhost:8080/api/passwords/get?username=abhi18&service=example.com"
```

### Get All Password Entries
- **URL:** `GET /api/passwords/all?username=<username>`
- **Response:**
  - JSON array of all password entries (with encrypted passwords) for the user

#### Example (curl):
```sh
curl "http://localhost:8080/api/passwords/all?username=abhi18"
```

### Get Original (Decrypted) Password
- **URL:** `GET /api/passwords/get-original?username=<username>&service=<serviceName>&user=<user>`
- **Response:**
  - The original (decrypted) password string, or `Service not found` if not present

#### Example (curl):
```sh
curl "http://localhost:8080/api/passwords/get-original?username=abhi18&service=example.com&user=yourusername"
```

### Set Storage File Location (per user)
- **URL:** `POST /api/passwords/set-storage-file?username=<username>&path=/path/to/your/passwords.json`
- **Response:**
  - Confirmation message or error

#### Example (curl):
```sh
curl -X POST "http://localhost:8080/api/passwords/set-storage-file?username=abhi18&path=/home/abhi18/my_passwords.json"
```

### Load Passwords from a File (per user)
- **URL:** `POST /api/passwords/load-passwords?username=<username>&path=/path/to/your/passwords.json`
- **Response:**
  - JSON array of all password entries from the specified file for the user

#### Example (curl):
```sh
curl -X POST "http://localhost:8080/api/passwords/load-passwords?username=abhi18&path=/home/abhi18/my_passwords.json"
```

## How It Works
- Passwords are encrypted using AES before being stored in a `.json` file.
- Each user's passwords are stored in their own file, never shared with others.
- When you add a password, the plaintext password is encrypted and saved.
- When you retrieve a password, you get the encrypted string (not the plaintext password).
- The encryption key is hardcoded for demo purposes (see `EncryptUtil.java`).
- You can set or load the password file location to any `.json` file on your system (per user).

## Security Notes
- **This project is for learning/demo purposes only.**
- The encryption key is hardcoded and should not be used in production.
- Endpoints are open (no authentication). For real use, add authentication and secure the key.
- File path access is not restricted—do not expose this to the public without validation.
- For true privacy, implement authentication and access control.

## Customization
- To change the encryption key, edit `EncryptUtil.java`.
- To add authentication, update `SecurityConfig.java` and controller logic.
- To use a database, refactor `PasswordService.java` and update dependencies.

## License
This project is open source and available under the MIT License.
