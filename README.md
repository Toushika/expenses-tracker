# Expense Tracker Microservices

This repository contains two Spring Boot microservices for an **Expense Tracker** application:

1. **Auth Management (`auth-management`)** – Handles user signup, login, and JWT authentication.
2. **Expense Management (`expense-management`)** – Manages user expenses and requires JWT authentication.

---

## Architecture

```
Client (Web)
       |
       v
  ----------------
  | Auth Service  |
  ----------------
       |
   JWT Token
       |
  ----------------
  | Expense Service |
  ----------------
```

## Auth Management Service

**Base URL:** `/auth`

### APIs

| Method | Endpoint    | Description                  |
| ------ | ----------- | ---------------------------- |
| POST   | `/signup`   | Register a new user          |
| POST   | `/login`    | Authenticate a user          |
| GET    | `/validate` | Validate JWT & get user info |

**Security:**

* JWT authentication using a secret key
* Token expiration configurable in `application.properties`

## Expense Management Service

**Base URL:** `/expense`

### APIs

| Method | Endpoint              | Description                                    |
| ------ | --------------------- | ---------------------------------------------- |
| GET    | `/index`              | Test endpoint (`Hello Expenses`)               |
| POST   | `/save`               | Add a new expense                              |
| GET    | `/getAll`             | List all expenses for the user                 |
| GET    | `/get?category=XYZ`   | List expenses filtered by category (paginated) |
| PUT    | `/update/{expenseId}` | Update an existing expense                     |
| DELETE | `/delete`             | Delete all expenses for the user               |
| DELETE | `/delete/{expenseId}` | Delete a single expense by ID                  |

**Authorization:**

* Requires JWT token in `Authorization` header (`Bearer <token>`)
* Validates token via Auth Management service (`AUTH_VALIDATE_SERVICE_URL`)


### Prerequisites

* Java 21
* Gradle 8+
* MongoDB

### Running the Services

```bash
# Start Auth Management
cd auth-management
./gradlew bootRun

# Start Expense Management
cd ../expense-management
./gradlew bootRun
```

## Technologies

* Java 21
* Spring Boot 3.x
* Spring Web & Security (JWT)
* MongoDB
* Logback
* Gradle

---

## Notes

* Keep the JWT secret secure.
* Ensure MongoDB is running locally or update the connection URI.
* Expense Management validates JWT via Auth Management service.
