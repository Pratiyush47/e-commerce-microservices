# E-Commerce Microservices Architecture

A complete microservices-based e-commerce platform built with Spring Boot, Docker, and modern DevOps practices.

## 📋 Project Overview

This project implements a scalable, distributed e-commerce system with 4 independent microservices, an API Gateway, and monitoring stack.

### Architecture Components

**Services:**
- API Gateway (Port 8080) - Central entry point
- User Service (Port 8001) - Authentication & user management
- Product Service (Port 8002) - Product catalog & inventory
- Order Service (Port 8003) - Order processing
- Payment Service (Port 8005) - Payment processing
- H2 In-Memory Database (Per Service)
- Prometheus (Port 9090) - Metrics Collection
- Grafana (Port 3000) - Dashboard & Visualization

## 🚀 Services Description

### 1. API Gateway (Port 8000)
- Central entry point for all client requests
- Routes requests to appropriate microservices
- Load balancing and request/response filtering
- Spring Cloud Gateway based

### 2. User Service (Port 8001)
- User registration and authentication
- JWT token generation
- User profile management
- Database: H2

**Endpoints:**
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login
- `GET /api/users/{id}` - Get user details
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### 3. Product Service (Port 8002)
- Product catalog management
- Inventory management
- Product search and filtering
- Database: H2

**Endpoints:**
- `POST /api/products` - Create product
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get by category
- `GET /api/products/search?name=query` - Search products
- `GET /api/products/available` - Get available products
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### 4. Order Service (Port 8003)
- Order processing and management
- Order tracking
- Integrates with Product Service for pricing
- Database: H2

**Endpoints:**
- `POST /api/orders` - Create order
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/user/{userId}` - Get orders by user
- `GET /api/orders/status/{status}` - Get orders by status
- `PUT /api/orders/{id}` - Update order
- `DELETE /api/orders/{id}` - Delete order

### 5. Payment Service (Port 8004)
- Payment processing
- Transaction management
- Payment history tracking
- Database: H2

**Endpoints:**
- `POST /api/payments` - Process payment
- `GET /api/payments` - Get all payments
- `GET /api/payments/{id}` - Get payment by ID
- `GET /api/payments/transaction/{transactionId}` - Get by transaction ID
- `GET /api/payments/order/{orderId}` - Get payments by order
- `GET /api/payments/user/{userId}` - Get payments by user
- `GET /api/payments/status/{status}` - Get by status
- `PUT /api/payments/{id}` - Update payment
- `DELETE /api/payments/{id}` - Delete payment

## 📦 Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Framework | Spring Boot | 3.1.5 |
| Cloud | Spring Cloud Gateway | 2022.0.4 |
| Database | H2 (Development) | Latest |
| Database | MySQL (Production Ready) | 8.0.33 |
| ORM | Spring Data JPA | 3.1.5 |
| API Documentation | SpringDoc OpenAPI | 2.0.2 |
| Monitoring | Prometheus | Latest |
| Visualization | Grafana | Latest |
| Containerization | Docker | Latest |
| Orchestration | Docker Compose | 3.8 |
| Java | OpenJDK | 17 |

## 📁 Project Structure
e-commerce-microservices/ │ ├── api-gateway/ │ ├── src/main/java/com/ecommerce/gateway/ │ ├── pom.xml │ └── Dockerfile │ ├── user-service/ │ ├── src/main/java/com/ecommerce/user/ │ ├── pom.xml │ └── Dockerfile │ ├── product-service/ │ ├── src/main/java/com/ecommerce/product/ │ ├── pom.xml │ └── Dockerfile │ ├── order-service/ │ ├── src/main/java/com/ecommerce/order/ │ ├── pom.xml │ └── Dockerfile │ ├── payment-service/ │ ├── src/main/java/com/ecommerce/payment/ │ ├── pom.xml │ └── Dockerfile │ ├── docker-compose.yml ├── prometheus.yml └── README.md

## 🛠️ Prerequisites

- Java 17 or higher
- Maven 3.8.1 or higher
- Docker & Docker Compose
- Git
- Postman (for API testing) - Optional

## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/Pratiyush47/e-commerce-microservices.git
cd e-commerce-microservices
```

### 2. Build All Services

```bash
mvn clean install
```

### 3. Run with Docker Compose

```bash
docker-compose up -d
```

### 4. Verify Services are Running

```bash
# Check if all containers are up
docker-compose ps
```
## 🌐 Access Services

Once all services are running, you can access them at:

| Service | URL | Port |
|---------|-----|------|
| API Gateway | http://localhost:8080 | 8080 |
| User Service | http://localhost:8001 | 8001 |
| Product Service | http://localhost:8002 | 8002 |
| Order Service | http://localhost:8003 | 8003 |
| Payment Service | http://localhost:8005 | 8005 |
| Prometheus | http://localhost:9090 | 9090 |
| Grafana | http://localhost:3000 | 3000 |

**Grafana Login Credentials:**
- Username: `admin`
- Password: `admin`

## 📊 API Documentation

Each service has Swagger/OpenAPI documentation available at:

- **API Gateway:** http://localhost:8000/swagger-ui.html
- **User Service:** http://localhost:8001/swagger-ui.html
- **Product Service:** http://localhost:8002/swagger-ui.html
- **Order Service:** http://localhost:8003/swagger-ui.html
- **Payment Service:** http://localhost:8004/swagger-ui.html

## 📈 Monitoring & Metrics

### Prometheus Metrics
- Access Prometheus: http://localhost:9090
- Metrics are collected from all microservices
- Query metrics using PromQL

### Grafana Dashboards
- Access Grafana: http://localhost:3000
- Pre-configured dashboards for monitoring
- Real-time visualization of service metrics

## 🧪 Testing the APIs

### Using Postman

1. **Import API Collection**
   - Open Postman
   - Create requests for each service endpoint

2. **Test User Service**
```
POST http://localhost:8000/api/users/register Body: { "username": "testuser", "email": "test@example.com", "password": "password123" }
```

3. **Test Product Service**
```
GET http://localhost:8000/api/products
```


4. **Create Order**
```
POST http://localhost:8000/api/orders Body: { "userId": 1, "productId": 1, "quantity": 2 }
```
### Using cURL

```bash
# Register User
curl -X POST http://localhost:8000/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
  

# Get Products

curl -X GET http://localhost:8000/api/products


# Create Order

curl -X POST http://localhost:8000/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"productId":1,"quantity":2}'
  ```

## 🔧 Troubleshooting

### Common Issues

**1. Services not starting**
```bash
# Check logs
docker-compose logs -f

# Restart services
docker-compose restart
```
**2. Port already in use**

```bash
# Kill process on port
# Linux/Mac:
lsof -ti:8000 | xargs kill -9

# Windows:
netstat -ano | findstr :8000
taskkill /PID <PID> /F
```
**3. Database connection errors**

- Ensure H2 database is properly initialized
- Check docker-compose.yml for correct environment variables
- Verify all services are running: `docker-compose ps`

**4. API Gateway not routing requests**

- Check if API Gateway container is running
- Verify service discovery is working
- Check gateway configuration in `application.yml`

## 📚 Project Documentation

### Service Dependencies

- **API Gateway** → Routes to all services
- **Order Service** → Depends on Product Service & User Service
- **Payment Service** → Depends on Order Service
- **Product Service** → Independent
- **User Service** → Independent

### Database Schema

Each service has its own H2 database with tables:
- **User Service:** users, roles, permissions
- **Product Service:** products, categories, inventory
- **Order Service:** orders, order_items
- **Payment Service:** payments, transactions

### Environment Variables

All services use environment variables for configuration:
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - DB username
- `SPRING_DATASOURCE_PASSWORD` - DB password
- `SERVER_PORT` - Service port
- `MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE` - Metrics exposure

## 🚀 Deployment

### Docker Compose Production Setup

```bash
# Build images
docker-compose build

# Start services in background
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f [service-name]

# Remove volumes (careful - deletes data)
docker-compose down -v
```

### Scaling Services

```bash
# Scale a specific service
docker-compose up -d --scale product-service=3

# Scale multiple services
docker-compose up -d --scale order-service=2 --scale payment-service=2
```

### Health Checks

Each service includes health check endpoints:
- `GET /actuator/health` - Service health status
- `GET /actuator/metrics` - Application metrics
- `GET /actuator/prometheus` - Prometheus metrics

## 📝 Contributing

### Guidelines

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Standards

- Follow Spring Boot best practices
- Use meaningful commit messages
- Write unit tests for new features
- Document API changes

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Pratiyush Singh**
- GitHub: [@Pratiyush47](https://github.com/Pratiyush47)
- Email: pratiyush379@gmail.com

## 🙏 Acknowledgments

- Spring Boot Documentation
- Spring Cloud Gateway Documentation
- Docker & Docker Compose Community