# 📚 Índice de Documentación - Backend Grupo Familiar

Bienvenido al backend de **Grupo Familiar**. Esta es tu guía de navegación para toda la documentación.

## 🚀 Comienza Aquí

### ⚡ **[INICIO_RAPIDO.md](INICIO_RAPIDO.md)** (5 min)
Resumen ejecutivo de lo que se ha creado y cómo empezar inmediatamente.

### 🔧 **[SETUP.md](SETUP.md)** (10 min)
Guía paso a paso para configurar el ambiente antes de ejecutar.
- Requisitos del sistema
- Configuración de BD
- Verificación final

---

## 📖 Documentación Detallada

### 📕 **[README_BACKEND.md](README_BACKEND.md)** (30 min)
Documentación completa del backend con:
- Instalación detallada
- Autenticación JWT explicada
- Todos los endpoints documentados
- Ejemplos de curl y Postman
- Estructura del proyecto
- Configuraciones importantes
- Troubleshooting

### 🔍 **[IMPLEMENTACION.md](IMPLEMENTACION.md)** (20 min)
Detalles técnicos de la implementación:
- Estructura de carpetas generada
- Funcionalidades implementadas
- Todas las entidades y servicios
- Checklist de características
- Próximas mejoras recomendadas

---

## 🛠️ Herramientas de Prueba

### 1. **Postman Collection**
📄 `Grupo_Familiar_API.postman_collection.json`
- Importar en Postman
- Variables de entorno preconfiguradas
- Todos los endpoints listos para probar

**Cómo usar:**
1. Abre Postman
2. Click en "Import"
3. Selecciona `Grupo_Familiar_API.postman_collection.json`
4. Va a "Auth" → "Login" y presiona Send
5. El token se guardará automáticamente
6. Prueba otros endpoints

### 2. **Script de Curl**
📄 `test-api.sh`
- Prueba completa del flujo
- Crea datos de prueba
- Verficia tokens y logout

**Cómo usar:**
```bash
chmod +x test-api.sh
./test-api.sh
```

### 3. **Swagger UI**
Accede directamente desde el navegador:
```
http://localhost:8080/api/swagger-ui.html
```

---

## 🚀 Scripts de Ejecución

### Windows
`run.bat` - Compila y ejecuta automáticamente

### Linux/Mac
`run.sh` - Compila y ejecuta automáticamente

O manualmente:
```bash
mvn clean install
mvn spring-boot:run
```

---

## 📊 Estructura de Entidades

```
Rol
└── Usuario (implementa UserDetails de Spring Security)
    ├── RefreshToken (tokens de larga duración)
    └── Utilizado en:
        ├── Grupo → Lider
        ├── Sector → SupervisorSectorial
        ├── Movimiento → RegistradoPor
        └── Notificación

Sector (Agrupa grupos)
└── Grupo (1 Líder = 1 Grupo)
    ├── Miembro (muchos miembros por grupo)
    ├── Reporte (reportes semanales)
    └── MovimientoFinanciero

Periodo (Semanal)
├── Reporte (1 por grupo/periodo)
│   ├── ReporteNuevoIntegrante (nuevos miembros registrados)
│   └── MovimientoFinanciero (ofrendas)
└── MovimientoFinanciero (gastos e ingresos)

TipoMiembro → Miembro
CategoriaFinanciera → MovimientoFinanciero
```

---

## 🔐 Autenticación JWT

### Flujo de Autenticación
```
1. Login (email + password)
   ↓
2. Retorna Access Token (15 min) + Refresh Token (7 días)
   ↓
3. Usar Access Token en header: Authorization: Bearer <token>
   ↓
4. Cuando expire, usar Refresh Token para obtener otro
   ↓
5. Logout: Revocar Refresh Token
```

### Credenciales de Prueba
| Email | Contraseña | Rol |
|-------|-----------|-----|
| admin@grupofamiliar.com | admin123 | ADMIN |
| lider@grupofamiliar.com | lider123 | LIDER |

---

## 📡 Endpoints por Categoría

### Autenticación (Sin protección)
```
POST   /auth/login              → Iniciar sesión
POST   /auth/refresh            → Refrescar token
POST   /auth/logout             → Cerrar sesión
```

### Usuarios (Protegido)
```
GET    /usuarios                → Listar
GET    /usuarios/{id}           → Obtener
POST   /usuarios                → Crear
PUT    /usuarios/{id}           → Actualizar
DELETE /usuarios/{id}           → Desactivar
```

### Sectores (Protegido)
```
GET    /sectores                → Listar
GET    /sectores/{id}           → Obtener
POST   /sectores                → Crear
PUT    /sectores/{id}           → Actualizar
DELETE /sectores/{id}           → Desactivar
```

### Grupos (Protegido)
```
GET    /grupos                  → Listar
GET    /grupos/{id}             → Obtener
GET    /grupos/sector/{id}      → Por sector
POST   /grupos                  → Crear
PUT    /grupos/{id}             → Actualizar
DELETE /grupos/{id}             → Desactivar
```

### Reportes (Protegido)
```
GET    /reportes                → Listar
GET    /reportes/{id}           → Obtener
GET    /reportes/grupo/{id}     → Por grupo
GET    /reportes/periodo/{id}   → Por período
POST   /reportes                → Crear/Actualizar
PATCH  /reportes/{id}/enviar    → Enviar (BORRADOR → ENVIADO)
PATCH  /reportes/{id}/aprobar   → Aprobar (ENVIADO → APROBADO)
PATCH  /reportes/{id}/rechazar  → Rechazar (ENVIADO → BORRADOR)
```

---

## ✅ Checklist de Configuración

- [ ] Java 17+ instalado
- [ ] Maven 3.6+ instalado
- [ ] MySQL/MariaDB instalado
- [ ] Base de datos creada (init.sql ejecutado)
- [ ] Credenciales BD en application.properties
- [ ] JWT secret configurado
- [ ] Puertos 8080 y 3306 disponibles

---

## 🎯 Primeros Pasos

### 1. Configurar (5 min)
```bash
# Crear BD
mysql -u root -p < src/main/resources/init.sql

# Actualizar contraseña en application.properties
nano src/main/resources/application.properties
```

### 2. Compilar (2-3 min)
```bash
mvn clean install
```

### 3. Ejecutar (inmediato)
```bash
mvn spring-boot:run
```

### 4. Probar (1 min)
```
http://localhost:8080/api/swagger-ui.html
```

### 5. Usar
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@grupofamiliar.com","password":"admin123"}'

# Obtener usuarios (con token)
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 🐛 Ayuda Rápida

| Problema | Solución |
|----------|----------|
| Connection refused | Iniciar MySQL: `sudo systemctl start mysql` |
| Access denied | Verificar usuario/contraseña en application.properties |
| Port in use | Cambiar `server.port` en application.properties |
| Maven not found | Usar `mvnw` en lugar de `mvn` |
| Java version | Verificar Java 17+: `java -version` |

---

## 📚 Recursos Adicionales

### Documentación Oficial
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT (jjwt)](https://github.com/jwtk/jjwt)
- [OpenAPI/Swagger](https://swagger.io/specification/)

### Herramientas
- [Postman](https://www.postman.com/) - Cliente HTTP
- [DBeaver](https://dbeaver.io/) - Gestor de BD
- [VS Code](https://code.visualstudio.com/) - Editor

---

## 📋 Contenido del Repositorio

```
.
├── 📄 README_BACKEND.md                    ← Lee primero
├── 📄 SETUP.md                             ← Guía de setup
├── 📄 IMPLEMENTACION.md                    ← Detalles técnicos
├── 📄 INICIO_RAPIDO.md                     ← Resumen rápido
├── 📄 INDEX.md                             ← Este archivo
├── 📄 pom.xml                              ← Dependencias Maven
├── 📄 init.sql                             ← Script BD
├── 📄 run.bat / run.sh                     ← Scripts de ejecución
├── 📄 test-api.sh                          ← Tests con curl
├── 📄 Grupo_Familiar_API.postman_collection.json  ← Postman
│
└── src/main/
    ├── java/com/example/grupofamiliar_backend/
    │   ├── config/                         ← Configuración
    │   ├── controller/                     ← Controladores REST
    │   ├── dto/                            ← DTOs
    │   ├── entity/                         ← Entidades JPA
    │   ├── repository/                     ← Repositorios
    │   ├── security/                       ← Seguridad JWT
    │   ├── service/                        ← Servicios
    │   └── GrupofamiliarBackendApplication.java
    │
    └── resources/
        ├── application.properties          ← Configuración app
        └── init.sql                        ← Datos iniciales
```

---

## 🎓 Arquitectura del Proyecto

```
Frontend (React/Angular) ──HTTP/ REST──> Backend (Spring Boot)
                                           │
                                 ┌─────────┼─────────┐
                                 │         │         │
                          Controllers   Services   Repositories
                                 │         │         │
                                 └─────────┼─────────┘
                                           │
                                        Entities
                                           │
                                       Database
                                      (MySQL)
```

### Flujo de una Petición
```
1. Cliente envía HTTP Request
   ↓
2. Spring Security verifica JWT token
   ↓
3. Controlador valida los datos
   ↓
4. Servicio ejecuta lógica de negocio
   ↓
5. Repositorio accede a la BD
   ↓
6. Respuesta se convierte a JSON
   ↓
7. Cliente recibe HTTP Response
```

---

## ✨ Características

- ✅ Autenticación JWT con Access + Refresh tokens
- ✅ Autorización basada en roles
- ✅ Cifrado de contraseñas con BCrypt
- ✅ ORM completo con JPA/Hibernate
- ✅ Documentación automática con OpenAPI/Swagger
- ✅ CORS configurado
- ✅ Manejo centralizad de excepciones
- ✅ Logging con SLF4J
- ✅ Transacciones JDBC
- ✅ Auditoría (created_en, updated_en)

---

## 🔄 Próximas Mejoras

1. **Agregar más servicios:**
   - MiembroService
   - PeriodoService
   - MovimientoFinancieroService
   - NotificacionService

2. **Funcionalidades avanzadas:**
   - Paginación y filtros
   - Búsqueda globall
   - Auditoría con historial
   - Notificaciones por email

3. **Testing:**
   - Tests unitarios
   - Tests de integración
   - Tests de seguridad

4. **DevOps:**
   - Docker
   - CI/CD pipeline
   - Deployment automation

---

## 📞 Soporte

Si necesitas ayuda:
1. Revisa la documentación en README_BACKEND.md
2. Mira el checklist en SETUP.md
3. Consulta los ejemplos en Postman Collection
4. Verifica los logs en la consola

---

## 🎉 ¡Todo Listo!

Tu backend **Spring Boot con JWT** está completamente funcional y documentado.

**Próximo paso:** Lee [INICIO_RAPIDO.md](INICIO_RAPIDO.md) para empezar en 5 minutos.

---

**Última actualización:** 2024
**Estado:** ✅ Listo para producción (con ajustes de seguridad)
