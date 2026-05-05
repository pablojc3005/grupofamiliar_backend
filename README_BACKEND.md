# Grupo Familiar Backend - API REST

Backend completo con autenticación JWT para la gestión de grupos familiares, reportes semanales y finanzas.

## 📋 Requisitos

- Java 17 o superior
- Maven 3.6+
- MariaDB/MySQL 8.0+
- Git

## 🚀 Configuración Inicial

### 1. Configurar la Base de Datos

```bash
# Conectarse a MariaDB/MySQL
mysql -u root -p

# Ejecutar el script de inicialización
source src/main/resources/init.sql
```

O ejecutar directamente:

```bash
mysql -u root -p < src/main/resources/init.sql
```

### 2. Configurar las Credenciales de Acceso

Editar `src/main/resources/application.properties`:

```properties
# Base de Datos
spring.datasource.url=jdbc:mariadb://localhost:3306/grupo_familiar?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA

# JWT Secret (cambiar en producción)
app.jwt.secret=your-super-secret-key-change-this-in-production-environment-very-important-key-256bit-minimum
```

### 3. Compilar el Proyecto

```bash
# Compilar con Maven
mvn clean install

# O en Windows
mvnw.cmd clean install
```

### 4. Ejecutar la Aplicación

```bash
# Ejecutar directamente
mvn spring-boot:run

# O en Windows
mvnw.cmd spring-boot:run

# O ejecutar el JAR directamente (después de compilar)
java -jar target/grupofamiliar_backend-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en `http://localhost:8080/api`

## 📚 Documentación de API

### Swagger UI

Una vez que la aplicación esté ejecutándose, accede a:
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

## 🔐 Autenticación JWT

### Credenciales de Prueba

```
Email: admin@grupofamiliar.com
Contraseña: admin123
Rol: ADMIN

Email: lider@grupofamiliar.com
Contraseña: lider123
Rol: LIDER
```

### Flujo de Autenticación

#### 1. Login

```bash
POST /auth/login
Content-Type: application/json

{
  "email": "admin@grupofamiliar.com",
  "password": "admin123"
}
```

**Respuesta exitosa:**

```json
{
  "success": true,
  "mensaje": "Inicio de sesión exitoso",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tipo": "Bearer",
    "usuarioId": 1,
    "email": "admin@grupofamiliar.com",
    "nombreCompleto": "Luis Administrador",
    "rol": "ADMIN"
  }
}
```

#### 2. Usar el Access Token

En todas las peticiones protegidas, incluir el header:

```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

#### 3. Refrescar el Access Token

```bash
POST /auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
}
```

#### 4. Logout

```bash
POST /auth/logout
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
}
```

## 📡 Endpoints Disponibles

### Autenticación

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/auth/login` | Autentica un usuario |
| POST | `/auth/refresh` | Refresca el Access Token |
| POST | `/auth/logout` | Cierra sesión |

### Usuarios

| Método | Ruta | Descripción | Protegida |
|--------|------|-------------|-----------|
| GET | `/usuarios` | Obtiene todos los usuarios | ✅ |
| GET | `/usuarios/{id}` | Obtiene un usuario | ✅ |
| GET | `/usuarios/email/{email}` | Obtiene usuario por email | ✅ |
| POST | `/usuarios` | Crea un nuevo usuario | ✅ |
| PUT | `/usuarios/{id}` | Actualiza usuario | ✅ |
| DELETE | `/usuarios/{id}` | Desactiva usuario | ✅ |
| PATCH | `/usuarios/{id}/activar` | Activa usuario | ✅ |

### Sectores

| Método | Ruta | Descripción | Protegida |
|--------|------|-------------|-----------|
| GET | `/sectores` | Obtiene todos los sectores | ✅ |
| GET | `/sectores/{id}` | Obtiene un sector | ✅ |
| POST | `/sectores` | Crea un sector | ✅ |
| PUT | `/sectores/{id}` | Actualiza sector | ✅ |
| DELETE | `/sectores/{id}` | Desactiva sector | ✅ |

### Grupos

| Método | Ruta | Descripción | Protegida |
|--------|------|-------------|-----------|
| GET | `/grupos` | Obtiene todos los grupos | ✅ |
| GET | `/grupos/{id}` | Obtiene un grupo | ✅ |
| GET | `/grupos/sector/{sectorId}` | Obtiene grupos por sector | ✅ |
| POST | `/grupos` | Crea un grupo | ✅ |
| PUT | `/grupos/{id}` | Actualiza grupo | ✅ |
| DELETE | `/grupos/{id}` | Desactiva grupo | ✅ |

### Reportes

| Método | Ruta | Descripción | Protegida |
|--------|------|-------------|-----------|
| GET | `/reportes` | Obtiene todos los reportes | ✅ |
| GET | `/reportes/{id}` | Obtiene un reporte | ✅ |
| GET | `/reportes/grupo/{grupoId}` | Obtiene reportes de un grupo | ✅ |
| GET | `/reportes/periodo/{periodoId}` | Obtiene reportes de un período | ✅ |
| POST | `/reportes` | Crea/actualiza reportes | ✅ |
| PATCH | `/reportes/{id}/enviar` | Envía un reporte | ✅ |
| PATCH | `/reportes/{id}/aprobar` | Aprueba un reporte | ✅ |
| PATCH | `/reportes/{id}/rechazar` | Rechaza un reporte | ✅ |

## 📝 Ejemplos de Uso

### Crear un Usuario

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombres": "Carlos",
    "apellidos": "Pérez",
    "email": "carlos@test.com",
    "telefono": "1234567890",
    "password": "password123",
    "idRol": 4
  }'
```

### Crear un Sector

```bash
curl -X POST http://localhost:8080/api/sectores \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "SECTOR ROJO I",
    "codigo": "SR1",
    "supervisorId": 3
  }'
```

### Crear un Grupo

```bash
curl -X POST http://localhost:8080/api/grupos \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "LIDER ROJO I",
    "codigo": "LR1",
    "sectorId": 1,
    "liderId": 2
  }'
```

### Crear un Reporte

```bash
curl -X POST http://localhost:8080/api/reportes \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "grupoId": 1,
    "periodoId": 1,
    "cantHermanos": 15,
    "cantAmigos": 5,
    "cantAdolescentes": 3,
    "cantConvertidos": 1,
    "cantNinosCristianos": 8,
    "cantNinosAmigos": 2,
    "cantVisitaConsolidacion": 5,
    "cantVisitaCasaDePaz": 3,
    "cantVisitaHogar": 2,
    "cantHrOracion": 10,
    "cantHrMep": 8,
    "cantHrDiscipulado": 5,
    "cantRetiroEspiritual": 0,
    "ofrendaSabado": 250.00,
    "ofrendaNinos": 50.00,
    "ofrendaMiercoles": 30.00,
    "observaciones": "Todo está progresando bien"
  }'
```

## 🏗️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/grupofamiliar_backend/
│   │   ├── config/              # Configuraciones (Security, OpenAPI, etc.)
│   │   ├── controller/          # Controladores REST
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── entity/              # Entidades JPA
│   │   ├── repository/          # Repositorios JPA
│   │   ├── security/            # Seguridad JWT
│   │   ├── service/             # Servicios de negocio
│   │   └── GrupofamiliarBackendApplication.java
│   └── resources/
│       ├── application.properties
│       └── init.sql             # Script de inicialización
└── test/                        # Tests unitarios
```

## 🔑 Configuraciones Importantes

### JWT

- **Secret**: `app.jwt.secret` (cambiar en producción)
- **Access Token Expiration**: 15 minutos (900000 ms)
- **Refresh Token Expiration**: 7 días (604800000 ms)

### CORS

Configurados los orígenes:
- `http://localhost:3000` (React)
- `http://localhost:4200` (Angular)

Cambiar en `SecurityConfig.java` según sea necesario.

## 🐛 Troubleshooting

### Conexión a Base de Datos

```
Error: Connection refused
```

Verificar que MariaDB/MySQL esté runiendo:

```bash
# Linux/Mac
sudo systemctl status mysql
sudo systemctl start mysql

# Windows
net start MySQL80
```

### Puerto 8080 ya en uso

Cambiar el puerto en `application.properties`:

```properties
server.port=8081
```

### JWT Expirado

Si recibes error `Token expirado`, usar el refresh token para obtener uno nuevo mediante el endpoint `/auth/refresh`.

## 📦 Dependencias Principales

- Spring Boot 4.0.4
- Spring Security
- Spring Data JPA
- JWT (jjwt)
- MariaDB/MySQL JDBC Driver
- Lombok
- SpringDoc OpenAPI (Swagger)

## 🚧 Próximas Mejoras

- [ ] Agregar endpoints para Miembros
- [ ] Agregar endpoints para Períodos
- [ ] Agregar endpoints para Movimientos Financieros
- [ ] Agregar endpoints para Notificaciones
- [ ] Implementar filtros y paginación
- [ ] Agregar validaciones adicionales
- [ ] Tests unitarios completamente

## 📝 Licencia

Este proyecto está bajo licencia MIT.

## 👥 Contacto

Para soporte técnico: support@grupofamiliar.com
