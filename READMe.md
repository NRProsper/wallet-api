# Budget Buddy Backend API

The Budget Buddy Backend API is a robust and scalable Spring Boot application that powers the Budget Buddy app. It provides endpoints for user authentication, account management, category management, and transaction tracking. This API is hosted and can be accessed via the provided base URL.

---

## Base URL

The API is hosted at:  
👉 [https://wallet-api-oi3v.onrender.com](https://wallet-api-oi3v.onrender.com)

---

## Features

### 1. **Authentication**
- **Register a User**: Create a new user account.
- **Login**: Authenticate and receive a JWT token for authorized requests.

### 2. **Account Management**
- Create, delete, and retrieve accounts.
- Top up accounts and record expenses.

### 3. **Category Management**
- Create, update, delete, and retrieve expense categories.

### 4. **Transaction Management**
- Retrieve paginated transactions for a specific account.
- Retrieve all transactions for analytics and visualization.

---

## API Endpoints

### 1. **Health Check**
- **GET `/`**: Check if the API is running.

### 2. **Authentication**
- **POST `/api/auth/sign-up`**: Register a new user.
- **POST `/api/auth/login`**: Log in a user and receive a JWT token.

### 3. **Account Management**
- **POST `/api/accounts`**: Create a new account.
- **DELETE `/api/accounts/{account_id}`**: Delete an account.
- **GET `/api/accounts`**: Get all accounts for the authenticated user.
- **GET `/api/accounts/{account_id}`**: Get a specific account.
- **POST `/api/accounts/{account_id}/top-up`**: Top up an account.
- **POST `/api/accounts/{account_id}/spend`**: Record an expense.

### 4. **Category Management**
- **POST `/api/categories`**: Create a new category.
- **GET `/api/categories`**: Get all categories for the authenticated user.
- **DELETE `/api/categories/{category_id}`**: Delete a category.
- **PUT `/api/categories/{category_id}`**: Update a category.

### 5. **Transaction Management**
- **GET `/api/transactions/{account_id}`**: Get paginated transactions for a specific account.
- **GET `/api/transactions/all`**: Get all transactions for the authenticated user (used for analytics and visualization).

---

## Technologies Used

- **Backend**:
    - Spring Boot (Java framework)
    - Spring Security (for authentication and authorization)
    - JWT (JSON Web Tokens for secure authentication)
    - Hibernate (for ORM and database management)
    - PostgreSQL (database)
    - Maven (for dependency management)

- **Hosting**:
    - Render (for hosting the backend API)

---