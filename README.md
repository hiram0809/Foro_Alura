# Foro Alura - API REST

Este proyecto es una API REST para un foro de discusión, desarrollada como parte del challenge de Alura Latam. Permite a los usuarios registrarse, autenticarse, crear tópicos (preguntas, discusiones), listarlos, actualizarlos y eliminarlos.

## 🔧 Tecnologías Utilizadas

- **Java 24**
- **Spring Boot 3.5.4**
- **Spring Security**
- **JWT (JSON Web Tokens)** para autenticación
- **Spring Data JPA**
- **PostgreSQL** (base de datos)
- **Insomnia** (para pruebas de API)
- **Maven** (gestión de dependencias)

## ⚙️ Configuración Inicial

### Requisitos Previos
- JDK 24
- PostgreSQL instalado y corriendo
- Insomnia (o Postman) para probar los endpoints
- IntelliJ IDEA (o cualquier IDE Java)

### Configuración de Base de Datos
1. Crear una base de datos en PostgreSQL:
```sql
CREATE DATABASE foro_alura;

Configurar application.properties:
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/foro_alura
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=TuClaveSecretaBase64De32Caracteres  # Generar con: openssl rand -base64 32
jwt.expiration=86400000  # 24 horas en milisegundos

# Server
server.port=8080

📂 Estructura del Proyecto
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── alura/
│   │           └── forokev/
│   │               ├── config/
│   │               │   ├── SecurityConfig.java
│   │               │   └── WebConfig.java
│   │               ├── controller/
│   │               │   ├── AuthController.java
│   │               │   └── TopicoController.java
│   │               ├── dto/
│   │               │   ├── AuthResponse.java
│   │               │   ├── LoginRequest.java
│   │               │   └── TopicoRequest.java
│   │               ├── model/
│   │               │   ├── Topico.java
│   │               │   └── Usuario.java
│   │               ├── repository/
│   │               │   ├── TopicoRepository.java
│   │               │   └── UsuarioRepository.java
│   │               ├── security/
│   │               │   ├── JwtAuthenticationFilter.java
│   │               │   └── JwtService.java
│   │               ├── service/
│   │               │   └── UserDetailsServiceImpl.java
│   │               └── ForoApplication.java
│   └── resources/
│       └── application.properties
├── test/
└── pom.xml

🔐 Autenticación JWT
Registro de Usuario
POST /auth/register
Content-Type: application/json

{
  "email": "usuario@ejemplo.com",
  "password": "P@ssw0rdSegura123"
}

Login
POST /auth/login
Content-Type: application/json

{
  "email": "usuario@ejemplo.com",
  "password": "P@ssw0rdSegura123"
}

Respuesta:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

📝 Endpoints de Tópicos
Crear Tópico
POST /topicos
Authorization: Bearer <token>
Content-Type: application/json

{
  "titulo": "Mi problema con Spring Security",
  "mensaje": "No puedo configurar JWT correctamente"
}

Listar Tópicos
GET /topicos
Authorization: Bearer <token>

Actualizar Tópico
PUT /topicos/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "titulo": "[SOLUCIONADO] Mi problema con Spring Security",
  "mensaje": "Ya encontré la solución en la documentación"
}

Eliminar Tópico
DELETE /topicos/{id}
Authorization: Bearer <token>

🧪 Pruebas con Insomnia
Configuración Recomendada
Crea un entorno con variables:

{
  "base_url": "http://localhost:8080",
  "token_kevin": "eyJhbGci...",
  "token_maria": "eyJqd3Qi..."
}

graph TD
  A[Registrar Usuario] --> B[Login]
  B --> C[Crear Tópico]
  C --> D[Listar Tópicos]
  D --> E[Actualizar Tópico]
  E --> F[Eliminar Tópico]

EJEMPLOS EJEMPLOS EJEMPLOS EJEMPLOS ---------------------------------------------------------

Ejemplos de Requests
Registro exitoso (200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

Creación de tópico (201 Created):
{
  "id": 1,
  "titulo": "Problema con JWT",
  "mensaje": "No puedo hacer login",
  "fechaCreacion": "2025-08-05T10:30:00",
  "autor": {
    "id": 1,
    "email": "kevin@email.com"
  }
}

Error 403 Forbidden:
Causas comunes:
Token no incluido en headers
Token expirado (24h)
Intento de modificar tópico de otro usuario

