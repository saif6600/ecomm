# Production-Ready E-Commerce Web Application

A complete full-stack e-commerce platform with React + Vite frontend and Spring Boot backend.

## Tech Stack
- **Frontend:** React (Vite), Tailwind CSS, Axios, React Router DOM, Context API
- **Backend:** Java 17, Spring Boot, Spring Security (JWT), Spring Data JPA, MySQL

## Monorepo Structure
```
/backend   -> Spring Boot API
/frontend  -> React UI
schema.sql -> MySQL schema
```

## Implemented Features
### User
- Registration + login with JWT
- Product browsing with search/category filtering
- Product details
- Cart add/update/remove
- Checkout to place order
- Order history

### Admin
- Admin login (seeded admin user)
- Add/delete products (update API included)
- View all users
- View all orders

## Setup Instructions

### 1) MySQL
```bash
mysql -u root -p < schema.sql
```

### 2) Backend
```bash
cd backend
cp .env.example .env   # optional; or set env vars directly
mvn spring-boot:run
```

Backend runs at `http://localhost:8080`.

Default admin credentials:
- Email: `admin@ecomm.com`
- Password: `Admin@123`

### 3) Frontend
```bash
cd frontend
cp .env.example .env
npm install
npm run dev
```

Frontend runs at `http://localhost:5173`.

## Environment Variables
### Backend
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `JWT_SECRET` (Base64 encoded 32+ byte key)
- `JWT_EXPIRATION_MS`
- `CORS_ALLOWED_ORIGINS`

### Frontend
- `VITE_API_URL`

## API Documentation

### Auth
- `POST /api/auth/register`
- `POST /api/auth/login`

### Products
- `GET /api/products?search=&category=`
- `GET /api/products/{id}`
- `POST /api/products/admin` (ADMIN)
- `PUT /api/products/admin/{id}` (ADMIN)
- `DELETE /api/products/admin/{id}` (ADMIN)

### Cart (USER)
- `GET /api/cart`
- `POST /api/cart`
- `PUT /api/cart/{cartItemId}`
- `DELETE /api/cart/{cartItemId}`

### Orders
- `POST /api/orders` (USER)
- `GET /api/orders/me` (USER)
- `GET /api/admin/orders` (ADMIN)

### User Management (ADMIN)
- `GET /api/admin/users`

## Scalability and Production Notes
- Layered architecture (Controller / Service / Repository)
- DTO-based request/response contracts
- Centralized exception handling
- BCrypt password hashing
- Stateless JWT authentication and RBAC
- Externalized configuration with environment variables
- CORS policy configuration
- Modular frontend with route guards and shared contexts
