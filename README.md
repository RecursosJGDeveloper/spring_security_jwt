# 🔐 Spring Boot JWT Authentication

Aplicación de autenticación y autorización con Spring Boot 4.0.0 y JWT (JSON Web Tokens).

## 📋 Descripción

Este proyecto implementa un sistema completo de autenticación basado en tokens JWT, utilizando Spring Security 6 y Spring Boot 4. Incluye endpoints para registro de usuarios, inicio de sesión y protección de rutas mediante tokens.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 4.0.0**
- **Spring Security 6**
- **Spring Data JPA**
- **MySQL 8**
- **JWT (JSON Web Tokens)** - jjwt 0.11.5
- **Lombok**
- **Maven**

## 📦 Requisitos Previos

- JDK 17 o superior
- Maven 3.6+
- MySQL 8.0+
- Postman o similar (para pruebas de API)

## ⚙️ Configuración

### 1. Base de Datos

Crea una base de datos MySQL:

```sql
CREATE DATABASE securitydb;
```

### 2. Configuración de la Aplicación

Edita `src/main/resources/application.properties`:

```properties
spring.application.name=demo-jwt

# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/securitydb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración JPA / Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# Log de seguridad para depuración
logging.level.org.springframework.security=DEBUG
```

⚠️ **Nota:** `ddl-auto=create-drop` borra la base de datos al reiniciar. Para mantener los datos, usa `update`.

## 🏃‍♂️ Ejecutar la Aplicación

### Opción 1: Con Maven Wrapper

```bash
./mvnw clean spring-boot:run
```

### Opción 2: Con Maven instalado

```bash
mvn clean spring-boot:run
```

La aplicación se ejecutará en `http://localhost:8080`

## 📡 Endpoints de la API

### 🔓 Endpoints Públicos

#### Registro de Usuario
```http
POST /auth/register
Content-Type: application/json

{
    "username": "usuario@example.com",
    "password": "mipassword123",
    "firstname": "Juan",
    "lastname": "Pérez",
    "country": "Argentina"
}
```

**Respuesta:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

#### Inicio de Sesión
```http
POST /auth/login
Content-Type: application/json

{
    "username": "usuario@example.com",
    "password": "mipassword123"
}
```

**Respuesta:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 🔒 Endpoints Protegidos

#### Endpoint de Demostración
```http
POST /api/v1/demo
Authorization: Bearer {tu-token-jwt}
```

**Respuesta:**
```
Welcome to the jungle
```

## 🧪 Pruebas con Postman

### 1. Registrar un nuevo usuario

- **Método:** POST
- **URL:** `http://localhost:8080/auth/register`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
    "username": "test@example.com",
    "password": "password123",
    "firstname": "Test",
    "lastname": "User",
    "country": "Argentina"
}
```

### 2. Iniciar sesión

- **Método:** POST
- **URL:** `http://localhost:8080/auth/login`
- **Headers:** `Content-Type: application/json`
- **Body:**
```json
{
    "username": "test@example.com",
    "password": "password123"
}
```

### 3. Acceder a ruta protegida

- **Método:** POST
- **URL:** `http://localhost:8080/api/v1/demo`
- **Headers:** 
  - `Authorization: Bearer {token-recibido}`
  - `Content-Type: application/json`

## 🏗️ Estructura del Proyecto

```
src/main/java/security_config/demo_jwt/
├── config/
│   ├── ApplicationConfig.java      # Configuración de beans de seguridad
│   └── SecurityConfig.java         # Configuración de Spring Security
├── controller/
│   ├── AuthController.java         # Endpoints de autenticación
│   └── DemoController.java         # Endpoints protegidos de ejemplo
├── dto/auth/
│   ├── AuthResponse.java           # DTO de respuesta con token
│   ├── LoginRequest.java           # DTO de solicitud de login
│   └── RegisterRequest.java        # DTO de solicitud de registro
├── jwt/
│   └── JwtAuthenticationFilter.java # Filtro para validar tokens JWT
├── models/
│   ├── Role.java                   # Enum de roles
│   └── UserEntity.java             # Entidad de usuario
├── repository/
│   └── AuthRepository.java         # Repositorio JPA
├── service/
│   ├── AuthService.java            # Lógica de autenticación
│   ├── JwtService.java             # Generación y validación de JWT
│   └── impl/
│       └── AuthServiceImpl.java    # Interface del servicio
└── DemoJwtApplication.java         # Clase principal
```

## 🔑 Características de Seguridad

- ✅ **Contraseñas encriptadas** con BCrypt
- ✅ **Tokens JWT** con firma HMAC-SHA256
- ✅ **Sesiones stateless** (sin estado en servidor)
- ✅ **Validación de tokens** en cada request
- ✅ **Expiración de tokens** (24 horas por defecto)
- ✅ **Rutas públicas y protegidas**

## 🔧 Configuración Avanzada

### Cambiar el tiempo de expiración del token

Edita `JwtService.java`:

```java
// De 24 horas a 7 días
.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
```

### Cambiar la SECRET_KEY

⚠️ **Importante:** En producción, usa variables de entorno:

```java
@Value("${jwt.secret.key}")
private String SECRET_KEY;
```

Y en `application.properties`:
```properties
jwt.secret.key=${JWT_SECRET_KEY:TuClaveSecretaPorDefecto}
```

### Mantener la base de datos entre reinicios

En `application.properties`:
```properties
spring.jpa.hibernate.ddl-auto=update
```

## 🐛 Solución de Problemas

### Error 403 Forbidden en /auth/login

- Verifica que la ruta esté en `permitAll()`
- Asegúrate de usar método **POST**, no GET
- Verifica el header `Content-Type: application/json`

### Error de conexión a MySQL

- Verifica que MySQL esté corriendo
- Confirma el usuario, password y puerto
- Asegúrate de que la base de datos `securitydb` existe

### Token inválido o expirado

- Genera un nuevo token con `/auth/login`
- Verifica que el token no tenga espacios extra
- El formato debe ser: `Bearer {token}`

## 📝 Notas Importantes

- Los tokens **NO** se almacenan en base de datos (stateless)
- Cada reinicio borra la base de datos con `create-drop`
- Los usuarios tienen rol `USER` por defecto
- La SECRET_KEY debe cambiarse en producción
- Los tokens expiran en 24 horas

## 📄 Licencia

Este proyecto es de código abierto y está disponible para uso educativo.

## 👥 Autor

Desarrollado como proyecto de demostración de Spring Security con JWT.

---

⭐ Si te resultó útil este proyecto, dale una estrella al repositorio!
