https://roadmap.sh/projects/scalable-ecommerce-platform
# E-Commerce Platform - Microservices Architecture

A comprehensive microservices-based e-commerce platform built with Spring Boot, Spring Cloud, and Docker. This project demonstrates modern cloud-native application development with service discovery, centralized configuration, API gateway routing, and centralized logging.

## Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Technologies & Stack](#technologies--stack)
- [Microservices](#microservices)
- [Setup & Installation](#setup--installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Docker Deployment](#docker-deployment)
- [Configuration Management](#configuration-management)
- [Logging & Monitoring](#logging--monitoring)
- [Database](#database)
- [Service Communication](#service-communication)
- [Development](#development)
- [Troubleshooting](#troubleshooting)

---

## Project Overview

This e-commerce platform implements a distributed microservices architecture following best practices in cloud-native development. Each business domain is isolated in its own microservice, communicating through well-defined APIs.

**Key Features:**
- Service discovery and registration using Netflix Eureka
- Centralized configuration management with Spring Cloud Config Server
- API Gateway for request routing and load balancing
- Microservices for User, Product, Cart, Order, and Notification management
- Payment service integration
- Centralized logging with ELK Stack (Elasticsearch, Logstash, Kibana)
- Docker containerization for all services
- H2 embedded database for development and testing

---

## Architecture

### High-Level Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                     Client Applications                      │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
        ┌────────────────────────────────┐
        │     API Gateway (8080)         │
        │  Request Routing & Load Bal.   │
        └────────────────────────────────┘
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼
   ┌─────────┐    ┌─────────┐    ┌──────────┐
   │ User    │    │ Product │    │ Cart     │
   │Service  │    │ Service │    │ Service  │
   │(8081)   │    │(8082)   │    │(8085)    │
   └─────────┘    └─────────┘    └──────────┘
        │                │                │
        ▼                ▼                ▼
   ┌─────────┐    ┌─────────┐    ┌──────────┐
   │Order    │    │Notif    │    │ Payment  │
   │Service  │    │Service  │    │ Service  │
   │(8083)   │    │(8084)   │    │          │
   └─────────┘    └─────────┘    └──────────┘

┌─────────────────────────────────────────────────────────────┐
│                  Infrastructure Services                     │
├─────────────────────────────────────────────────────────────┤
│ Eureka Server (8761) - Service Discovery                    │
│ Config Server (8888) - Centralized Configuration            │
│ Elasticsearch (9200) - Log Storage                          │
│ Logstash (5500) - Log Processing                            │
│ Kibana - Log Visualization                                  │
└─────────────────────────────────────────────────────────────┘
```

---

## Project Structure

```
ecommerce-platform/
│
├── api-gateway/                 # API Gateway Service
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/
│       ├── java/
│       └── resources/
│
├── cart-service/                # Shopping Cart Service
│   ├── Dockerfile
│   ├── pom.xml
│   ├── data/                    # H2 Database files
│   └── src/main/
│
├── config-server/               # Centralized Configuration Server
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/
│
├── eureka-server/               # Service Discovery Server
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/
│
├── notification-service/        # Notification Service
│   ├── Dockerfile
│   ├── pom.xml
│   ├── data/
│   └── src/main/
│
├── order-service/               # Order Management Service
│   ├── Dockerfile
│   ├── pom.xml
│   ├── data/
│   └── src/main/
│
├── payment-service/             # Payment Processing Service
│   ├── pom.xml
│   └── src/main/
│
├── product-service/             # Product Catalog Service
│   ├── Dockerfile
│   ├── pom.xml
│   ├── data/
│   └── src/main/
│
├── user-service/                # User Management Service
│   ├── Dockerfile
│   ├── pom.xml
│   ├── logback-spring.xml
│   ├── data/
│   └── src/main/
│
├── data/                        # Shared database files
│   ├── cartdb.mv.db
│   ├── devdb.mv.db
│   ├── proddb.mv.db
│   └── *.trace.db
│
├── pom.xml                      # Parent POM (Multi-module Maven)
├── docker-compose.yml           # Docker Compose Configuration
├── logstash.conf                # Logstash Configuration
└── README.md                    # This file
```

---

## Technologies & Stack

### Core Technologies
- **Java 17** - Programming language
- **Spring Boot 3.2.1** - Framework for building microservices
- **Spring Cloud 2023.0.1** - Microservices patterns
- **Maven** - Build automation tool

### Key Dependencies

| Service/Component | Technology | Purpose |
|------------------|-----------|---------|
| API Gateway | Spring Cloud Gateway | Request routing and load balancing |
| Service Discovery | Netflix Eureka | Service registration and discovery |
| Configuration | Spring Cloud Config | Centralized configuration management |
| Database | H2 | In-memory/file-based SQL database |
| ORM | JPA/Hibernate | Object-relational mapping |
| Security | Spring Security | Authentication and authorization |
| Logging | Logback, SLF4J | Structured logging |
| Search/Logging | Elasticsearch | Centralized log storage |
| Log Pipeline | Logstash | Log processing and forwarding |
| Data Binding | Jackson | JSON serialization/deserialization |
| Validation | Jakarta Bean Validation | Input validation |
| Utility | Lombok | Reduce boilerplate code |

### Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Elasticsearch 8.17.3** - Log storage and search
- **Logstash 8.17.3** - Log processing
- **Kibana** - Log visualization (optional)

---

## Microservices

### 1. **Eureka Server** (Port: 8761)
Service discovery and registration center.

**Purpose:**
- Maintains registry of all microservices
- Enables client-side service discovery
- Provides health checking for registered services

**Key Configuration:**
```yaml
server:
  port: 8761
eureka:
  server:
    enable-self-preservation: true
```

**Dashboard:** `http://localhost:8761`

---

### 2. **Config Server** (Port: 8888)
Centralized configuration management.

**Purpose:**
- Provides dynamic configuration to all microservices
- Manages environment-specific properties
- Supports Git-based configuration storage

**Supported Profiles:**
- `dev` - Development environment
- `prod` - Production environment

---

### 3. **API Gateway** (Port: 8080)
Central entry point for all client requests.

**Purpose:**
- Routes requests to appropriate microservices
- Implements cross-cutting concerns (authentication, rate limiting)
- Provides single entry point for client applications
- Uses Spring Cloud Gateway (reactive)

**Service Routes:**
- `/user/**` → User Service (8081)
- `/product/**` → Product Service (8082)
- `/order/**` → Order Service (8083)
- `/notification/**` → Notification Service (8084)
- `/cart/**` → Cart Service (8085)
- `/payment/**` → Payment Service

---

### 4. **User Service** (Port: 8081)
Manages user authentication and profiles.

**Key Features:**
- User registration and authentication
- User profile management
- Role-based access control (RBAC)

**Database:**
- H2 Database (`devdb.mv.db` for dev, `proddb.mv.db` for prod)
- Path: `./data/devdb`

**Key Endpoints:**
- `GET /user/all` - Retrieve all users
- `GET /user/{id}` - Get user by ID
- `POST /user/register` - Register new user
- `POST /user/login` - User login

---

### 5. **Product Service** (Port: 8082)
Manages product catalog and inventory.

**Key Features:**
- Product CRUD operations
- Inventory management
- Product search and filtering
- Category management

**Database:**
- H2 Database (`proddb.mv.db`)

**Key Endpoints:**
- `GET /product/all` - List all products
- `GET /product/{id}` - Get product details
- `POST /product/create` - Add new product
- `PUT /product/{id}` - Update product
- `DELETE /product/{id}` - Delete product

---

### 6. **Cart Service** (Port: 8085)
Manages shopping carts.

**Key Features:**
- Add/remove items from cart
- Update cart quantities
- Cart persistence
- Cart calculation (subtotal, tax, total)

**Database:**
- H2 Database (`cartdb.mv.db`)

**Key Endpoints:**
- `GET /cart/{userId}` - Get user's cart
- `POST /cart/{userId}/add` - Add item to cart
- `DELETE /cart/{userId}/remove/{productId}` - Remove item
- `PUT /cart/{userId}/clear` - Clear cart

---

### 7. **Order Service** (Port: 8083)
Manages orders and order history.

**Key Features:**
- Order creation and tracking
- Order status management
- Order history and reports
- Inventory allocation

**Database:**
- H2 Database (`orderdb.mv.db`)

**Key Endpoints:**
- `GET /order/all` - List all orders
- `GET /order/{id}` - Get order details
- `POST /order/create` - Create new order
- `PUT /order/{id}/status` - Update order status

---

### 8. **Notification Service** (Port: 8084)
Handles notifications and communication.

**Key Features:**
- Email notifications
- SMS notifications
- Notification templates
- Event-driven notifications

**Database:**
- H2 Database (`notificationdb.mv.db`)

**Key Endpoints:**
- `POST /notification/send` - Send notification
- `GET /notification/templates` - List notification templates

---

### 9. **Payment Service**
Processes payments and transactions.

**Key Features:**
- Payment processing
- Transaction management
- Payment status tracking
- Refund handling

**Key Endpoints:**
- `POST /payment/process` - Process payment
- `GET /payment/{transactionId}` - Get payment details
- `POST /payment/{transactionId}/refund` - Refund payment

---

## Setup & Installation

### Prerequisites

Before getting started, ensure you have the following installed:

1. **Java 17 or higher**
   ```bash
   java -version
   ```

2. **Maven 3.6.0 or higher**
   ```bash
   mvn -version
   ```

3. **Docker & Docker Compose** (for containerization)
   ```bash
   docker --version
   docker-compose --version
   ```

4. **Git**
   ```bash
   git --version
   ```

### Installation Steps

1. **Clone the repository**
   ```bash
   cd /Users/divaykansal/Downloads
   cd ecommerce-platform
   ```

2. **Clean and build the project**
   ```bash
   mvn clean install
   ```
   
   This will:
   - Build all microservices
   - Run tests
   - Package JAR files
   - Download dependencies

3. **Build Docker images (optional)**
   ```bash
   docker-compose build
   ```

---

## Running the Application

### Option 1: Run Individual Services Locally

Start services in this order:

1. **Start Eureka Server**
   ```bash
   cd eureka-server
   mvn spring-boot:run
   # Access at: http://localhost:8761
   ```

2. **Start Config Server** (in a new terminal)
   ```bash
   cd config-server
   mvn spring-boot:run
   # Access at: http://localhost:8888
   ```

3. **Start API Gateway** (in a new terminal)
   ```bash
   cd api-gateway
   mvn spring-boot:run
   # Access at: http://localhost:8080
   ```

4. **Start User Service** (in a new terminal)
   ```bash
   cd user-service
   mvn spring-boot:run
   # Access at: http://localhost:8081
   ```

5. **Start Product Service** (in a new terminal)
   ```bash
   cd product-service
   mvn spring-boot:run
   # Access at: http://localhost:8082
   ```

6. **Start Cart Service** (in a new terminal)
   ```bash
   cd cart-service
   mvn spring-boot:run
   # Access at: http://localhost:8085
   ```

7. **Start Order Service** (in a new terminal)
   ```bash
   cd order-service
   mvn spring-boot:run
   # Access at: http://localhost:8083
   ```

8. **Start Notification Service** (in a new terminal)
   ```bash
   cd notification-service
   mvn spring-boot:run
   # Access at: http://localhost:8084
   ```

9. **Start Payment Service** (in a new terminal)
   ```bash
   cd payment-service
   mvn spring-boot:run
   ```

### Option 2: Run with Docker Compose (Recommended)

```bash
# Navigate to project root
cd /Users/divaykansal/Downloads/ecommerce-platform

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

**Wait for services to stabilize (~30-60 seconds)**

---

## API Endpoints

### Base URL
- **Local:** `http://localhost:8080`
- **Docker:** `http://localhost:8080`

### Available Endpoints

#### User Service
```
GET    /user/all
GET    /user/{id}
POST   /user/register
POST   /user/login
PUT    /user/{id}
DELETE /user/{id}
```

#### Product Service
```
GET    /product/all
GET    /product/{id}
GET    /product/search?query=
POST   /product/create
PUT    /product/{id}
DELETE /product/{id}
```

#### Cart Service
```
GET    /cart/{userId}
POST   /cart/{userId}/add
PUT    /cart/{userId}/update/{itemId}
DELETE /cart/{userId}/remove/{productId}
PUT    /cart/{userId}/clear
```

#### Order Service
```
GET    /order/all
GET    /order/{id}
GET    /order/user/{userId}
POST   /order/create
PUT    /order/{id}/status
```

#### Notification Service
```
POST   /notification/send
GET    /notification/templates
GET    /notification/{id}
```

#### Payment Service
```
POST   /payment/process
GET    /payment/{transactionId}
POST   /payment/{transactionId}/refund
```

### Example API Calls

**Create a User**
```bash
curl -X POST http://localhost:8080/user/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

**Get All Products**
```bash
curl -X GET http://localhost:8080/product/all
```

**Add to Cart**
```bash
curl -X POST http://localhost:8080/cart/1/add \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
```

---

## Docker Deployment

### Building Docker Images

Each service has a `Dockerfile` for containerization:

```bash
# Build all services
docker-compose build

# Build a specific service
docker-compose build user-service
```

### Docker Compose Configuration

The `docker-compose.yml` file orchestrates:

1. **Eureka Server** - Service discovery
2. **Config Server** - Configuration management
3. **API Gateway** - Request routing
4. **User, Product, Cart, Order, Notification Services** - Microservices
5. **Elasticsearch, Logstash** - Logging infrastructure
6. **Network** - Custom bridge network (`ecommerce-network`)

### Running with Docker Compose

```bash
# Start all services in detached mode
docker-compose up -d

# View logs for all services
docker-compose logs -f

# View logs for specific service
docker-compose logs -f user-service

# Stop all services
docker-compose down

# Remove all containers and volumes
docker-compose down -v
```

### Individual Service Deployment

```bash
# Build specific service
docker build -t user-service:latest ./user-service

# Run specific service
docker run -p 8081:8081 -e SPRING_PROFILES_ACTIVE=dev user-service:latest

# Tag and push to registry
docker tag user-service:latest myregistry/user-service:latest
docker push myregistry/user-service:latest
```

---

## Configuration Management

### Application Profiles

Each service supports multiple profiles:

- **default** - Default configuration (for infrastructure services)
- **dev** - Development environment
- **prod** - Production environment

### Configuration Files

Located in `src/main/resources/`:

- `application.yml` - Base configuration
- `application-dev.yml` - Development-specific settings
- `application-prod.yml` - Production-specific settings

### Example Configuration (User Service)

**application.yml:**
```yaml
server:
  port: 8081
spring:
  application:
    name: user-service
  config:
    import: optional:configserver:http://localhost:8888

eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka-server:8761/eureka/
```

**application-dev.yml:**
```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/devdb
    driver-class-name: org.h2.Driver
    username: sa
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.H2Dialect
  h2:
    console:
      enabled: true
      path: /h2-console
```

### Setting Active Profile

**Via Environment Variable:**
```bash
export SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run
```

**Via Command Line:**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

**Via Docker:**
```yaml
environment:
  - SPRING_PROFILES_ACTIVE=dev
```

---

## Logging & Monitoring

### Logging Architecture

The platform implements centralized logging using ELK Stack:

```
Services
   │
   ├─→ Logback (Logging Framework)
   │
   └─→ Logstash (Port 5500)
        │
        └─→ Elasticsearch (Port 9200)
             │
             └─→ Kibana (Visualization)
```

### Logstash Configuration

**logstash.conf:**
```logstash
input {
  tcp {
    port => 5000
    codec => json_lines
  }
}

output {
  elasticsearch {
    hosts => ["http://elasticsearch:9200"]
    index => "logs-%{+YYYY.MM.dd}"
  }
}
```

### Accessing Logs

1. **Access Elasticsearch:**
   ```bash
   curl http://localhost:9200/_cat/indices
   ```

2. **View Logs via Kibana:** (if running)
   ```
   http://localhost:5601
   ```

3. **View Docker Logs:**
   ```bash
   docker-compose logs -f service-name
   ```

### Log Levels

Configure in `application.yml`:

```yaml
logging:
  level:
    root: INFO
    com.example: DEBUG
    org.springframework.cloud: DEBUG
```

### Health Checks

Each service exposes health endpoints:

```bash
curl http://localhost:8081/actuator/health
```

---

## Database

### H2 Database

All services use H2 embedded database for development and testing.

### Database Files

- **User Service:** `./data/devdb.mv.db`
- **Product Service:** `./data/proddb.mv.db`
- **Cart Service:** `./data/cartdb.mv.db`
- **Order Service:** `./data/orderdb.mv.db`
- **Notification Service:** `./data/notificationdb.mv.db`

### H2 Console Access

For services with H2 console enabled:

```
URL: http://localhost:PORT/h2-console
Default JDBC URL: jdbc:h2:file:./data/DBNAME
Username: sa
Password: (leave blank)
```

### JPA/Hibernate Configuration

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update          # Auto-create/update schema
    database-platform: org.hibernate.dialect.H2Dialect
    show-sql: false            # Show SQL in logs
    properties:
      hibernate:
        format_sql: true
```

### Switching to Production Database

For production, replace H2 with MySQL:

1. Add MySQL dependency to `pom.xml`:
   ```xml
   <dependency>
     <groupId>com.mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>8.0.33</version>
   </dependency>
   ```

2. Update `application-prod.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://mysql-host:3306/ecommerce_db
       driver-class-name: com.mysql.cj.jdbc.Driver
       username: root
       password: ${DB_PASSWORD}
     jpa:
       database-platform: org.hibernate.dialect.MySQL8Dialect
   ```

---

## Service Communication

### Inter-Service Communication Patterns

1. **Service Discovery (Eureka)**
   - Services register with Eureka
   - Clients discover services dynamically
   - Load balancing across instances

2. **Synchronous Communication (REST)**
   - Services call each other via REST APIs
   - Using `RestTemplate` or `WebClient`

3. **Example: Order Service calling Cart Service**
   ```java
   @Autowired
   private RestTemplate restTemplate;
   
   public Cart getCartByUserId(Long userId) {
       return restTemplate.getForObject(
           "http://cart-service/cart/{userId}",
           Cart.class,
           userId
       );
   }
   ```

### Resilience & Fault Tolerance

Implement circuit breakers, retries, and timeouts:

- **Spring Cloud Resilience4j** - Add to enhance fault tolerance
- **Timeout Configuration** - Prevent hanging requests
- **Fallback Methods** - Graceful degradation

---

## Development

### IDE Setup

**IntelliJ IDEA:**
1. Open project: `File → Open`
2. Select project root directory
3. Maven projects auto-detected
4. Enable annotation processing: `Settings → Build, Execution, Deployment → Compiler → Annotation Processors`

**VS Code:**
1. Install extensions: Spring Boot Extension Pack
2. Open project folder
3. Maven projects auto-detected

### Code Structure

Each service follows this structure:

```
service-name/src/main/
├── java/com/example/
│   └── service/
│       ├── controller/
│       │   └── *Controller.java
│       ├── service/
│       │   └── *Service.java
│       ├── repository/
│       │   └── *Repository.java
│       ├── entity/
│       │   └── *.java
│       ├── dto/
│       │   └── *.java
│       ├── exception/
│       │   └── *.java
│       └── Application.java
└── resources/
    ├── application.yml
    ├── application-dev.yml
    ├── application-prod.yml
    └── logback-spring.xml
```

### Best Practices

1. **Always include service name in logs:**
   ```java
   private static final Logger log = LoggerFactory.getLogger(UserService.class);
   ```

2. **Use DTOs for API responses:**
   ```java
   @Data
   public class UserDTO {
       private Long id;
       private String username;
       private String email;
   }
   ```

3. **Implement proper exception handling:**
   ```java
   @ExceptionHandler(EntityNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
           .body(new ErrorResponse(ex.getMessage()));
   }
   ```

4. **Add validation annotations:**
   ```java
   @Valid
   @RequestBody UserDTO userDTO
   ```

### Testing

Run tests for specific service:

```bash
cd user-service
mvn test
```

Run tests for all services:

```bash
mvn test
```

---

## Troubleshooting

### Common Issues and Solutions

#### 1. **Port Already in Use**
```bash
# Find process using port
lsof -i :8081

# Kill process
kill -9 <PID>
```

#### 2. **Services Not Registering with Eureka**
- Check Eureka server is running: `http://localhost:8761`
- Verify `eureka.client.serviceUrl.defaultZone` configuration
- Check firewall settings

#### 3. **Config Server Connection Issues**
```bash
# Verify Config Server is running
curl http://localhost:8888/actuator/health

# Check config import in application.yml
spring:
  config:
    import: optional:configserver:http://localhost:8888
```

#### 4. **Database Connection Errors**
```bash
# Clear data directory
rm -rf ./data/*.mv.db
rm -rf ./data/*.trace.db

# Restart services (creates fresh databases)
mvn spring-boot:run
```

#### 5. **Docker Container Exits Immediately**
```bash
# Check logs
docker-compose logs service-name

# Verify environment variables
docker-compose config | grep -A 5 "service-name"
```

#### 6. **Elasticsearch Not Starting**
```bash
# Check memory requirements (needs at least 512MB)
docker-compose logs elasticsearch

# Increase Docker memory allocation in Docker Desktop settings
```

#### 7. **Service Discovery Takes Long**
- Initial registration: 30 seconds
- Service availability in Eureka: 30 seconds
- Total startup time: 2-3 minutes for all services

#### 8. **API Gateway Routes Not Working**
```bash
# Test direct service
curl http://localhost:8081/user/all

# Test via gateway
curl http://localhost:8080/user/all

# Check gateway configuration
curl http://localhost:8080/actuator/gateway/routes
```

### Debug Mode

**Enable debug logging:**

```yaml
logging:
  level:
    root: DEBUG
    org.springframework.cloud: DEBUG
    org.springframework.web: DEBUG
```

**Enable Spring Boot DevTools:**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### Performance Monitoring

**Check service health:**
```bash
curl http://localhost:8081/actuator/health
```

**Monitor memory usage:**
```bash
docker stats
```

**Check database connections:**
```bash
curl http://localhost:8081/actuator/env | grep datasource
```

---

## Project Maintenance

### Updating Dependencies

```bash
# Check for updates
mvn versions:display-dependency-updates

# Update to latest compatible versions
mvn versions:use-latest-versions
```

### Cleaning Build Artifacts

```bash
# Clean all services
mvn clean

# Remove Docker images
docker-compose down --rmi all

# Remove volumes (databases)
docker-compose down -v
```

### Backup Databases

```bash
# Copy database files
cp -r ./data ./data.backup

# Export from Elasticsearch
curl http://localhost:9200/logs-*/_search > logs_backup.json
```

---

## Contributing

When adding new features or services:

1. Create new service following existing patterns
2. Update `docker-compose.yml` with new service
3. Add service dependency declarations
4. Update routing in API Gateway
5. Document in this README

---

## Support & Resources

### Documentation Links
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [Spring Cloud Documentation](https://docs.spring.io/spring-cloud/docs/)
- [Spring Cloud Netflix Eureka](https://docs.spring.io/spring-cloud-netflix/reference/spring-cloud-netflix.html)
- [Docker Documentation](https://docs.docker.com/)

### Community Resources
- Spring Community Forums
- Stack Overflow (tag: spring-boot, spring-cloud)
- GitHub Issues

---

## License

This project is provided as-is for educational and development purposes.

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | 2024 | Initial release with 8 microservices |
| 1.1.0 | 2024 | Added ELK Stack integration |
| 1.2.0 | 2026 | Documentation updates |

---

## Contact & Support

For questions or issues, please refer to the project documentation or contact the development team.

---

**Last Updated:** April 17, 2026
**Project Version:** 1.2.0
**Java Version:** 17
**Spring Boot Version:** 3.2.1
**Spring Cloud Version:** 2023.0.1

