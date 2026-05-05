# 📖 Tabla de Contenidos - Documentación Backend

Bienvenido a la documentación completa del backend Grupo Familiar. Aquí encontrarás todos los recursos necesarios para entender, configurar, ejecutar y probar la aplicación.

---

## 🚀 INICIO RÁPIDO

Si es tu primera vez, comienza aquí:

### 1. **[INICIO_RAPIDO.md](./INICIO_RAPIDO.md)** ⏱️ (5 minutos)
   - Requisitos del sistema
   - Pasos básicos para ejecutar
   - Primer test del API
   - Troubleshooting rápido

### 2. **[SETUP.md](./SETUP.md)** ⚙️ (10 minutos)
   - Checklist de instalación
   - Configuración de database
   - Variables de entorno
   - Proceso step-by-step

---

## 📚 DOCUMENTACIÓN PRINCIPAL

### 3. **[README_BACKEND.md](./README_BACKEND.md)** 📖 (Referencia completa)
   - Descripción general del proyecto
   - Arquitectura y tecnologías
   - Autenticación JWT explicada
   - Documentación de endpoints
   - Flujo de autenticación
   - Integración con base de datos

### 4. **[IMPLEMENTACION.md](./IMPLEMENTACION.md)** 🏗️ (Detalles técnicos)
   - Estructura de carpetas
   - Componentes principales:
     - Entidades (13 tablas)
     - Repositorios (13 interfaces)
     - Servicios (5 clases)
     - Controladores (5 clases)
     - DTOs (10 clases)
     - Seguridad (4 clases)
   - Features implementados
   - Mejoras futuras

---

## 🔍 FLUJOS Y DIAGRAMAS

### 5. **[FLUJO_OPERACION.md](./FLUJO_OPERACION.md)** 📊 (Diagramas visuales)
   - Flujo general de la aplicación
   - Autenticación paso a paso
   - Peticiones protegidas
   - Refresh token
   - Estado de reportes
   - Ciclo de vida de peticiones
   - Mapeo de URLs

---

## 🧪 PRUEBAS Y TESTING

### 6. **Colección Postman** 📮
   - Archivo: `Grupo_Familiar_API.postman_collection.json`
   - Uso: Importar en Postman o Insomnia
   - Contiene: 40+ requests organizados por endpoint
   - Variables: Automáticamente configuradas

### 7. **Script de Prueba** 🔧
   - Archivo: `test-api.sh` (Linux/Mac)
   - Uso: Ejecutar para suite de tests automatizados
   - Requiere: curl instalado
   - Valida: Todos los endpoints principales

### 8. **Scripts de Ejecución** ▶️
   - Windows: `run.bat`
   - Linux/Mac: `run.sh`
   - Función: Compilar y ejecutar aplicación

---

## 🗄️ BASE DE DATOS

### 9. **[init.sql](./src/main/resources/init.sql)** 💾
   - Crea estructura de BD completa (13 tablas)
   - Inserta datos iniciales:
     - 5 Roles
     - 3 Usuarios de prueba
     - 8 Categorías financieras
   - Usa: `mysql -u root -p < init.sql`

---

## 📋 MATRIZ DE REFERENCIA RÁPIDA

| Documento | Tiempo | Propósito | Para Quién |
|-----------|--------|---------|-----------|
| INICIO_RAPIDO.md | 5 min | Ejecutar rápido | Developers nuevos |
| SETUP.md | 10 min | Configurar | DevOps / Admins |
| README_BACKEND.md | 30 min | Entender API | Frontend devs |
| IMPLEMENTACION.md | 45 min | Arquitectura código | Backend devs |
| FLUJO_OPERACION.md | 20 min | Diagramas | Diseñadores / PMs |

---

## 🔑 CREDENCIALES DE PRUEBA

```
Rol: ADMIN
├─ Email: admin@grupofamiliar.com
└─ Password: admin123

Rol: LIDER
├─ Email: lider@grupofamiliar.com
└─ Password: lider123

Rol: SUPERVISOR
├─ Email: supervisor@grupofamiliar.com
└─ Password: supervisor123
```

---

## 🌐 URLs PRINCIPALES

```
API Base URL:
└─ http://localhost:8080/api

Documentación Interactiva:
└─ http://localhost:8080/api/swagger-ui.html

OpenAPI JSON:
└─ http://localhost:8080/api/v3/api-docs

Endpoints principales:
├─ POST   /auth/login      - Autenticación
├─ GET    /usuarios        - Listar usuarios
├─ GET    /grupos          - Listar grupos
├─ GET    /reportes        - Listar reportes
├─ GET    /sectores        - Listar sectores
└─ PATCH  /reportes/{id}/enviar - Cambiar estado reporte
```

---

## 📦 ESTRUCTURA DE ARCHIVOS

```
src/main/java/com/example/grupofamiliar_backend/
├── entity/                    (13 clases JPA)
│   ├── Rol, Usuario, RefreshToken
│   ├── Sector, Grupo, TipoMiembro, Miembro
│   ├── Periodo, Reporte, ReporteNuevoIntegrante
│   ├── CategoriaFinanciera, MovimientoFinanciero
│   └── Notificacion
├── repository/               (13 interfaces JPA)
├── dto/                     (10 clases DTOs)
├── service/                 (5 servicios)
├── controller/              (5 controladores REST)
├── security/                (4 clases JWT)
├── config/                  (3 configuraciones)
└── GrupofamiliarBackendApplication.java

src/main/resources/
├── application.properties    (Configuración)
├── init.sql                 (SQL inicial)
├── static/                  (Archivos estáticos)
└── templates/               (Plantillas)

src/test/
└── GrupofamiliarBackendApplicationTests.java
```

---

## ✅ CHECKLIST DE PRIMEROS PASOS

- [ ] Leer INICIO_RAPIDO.md (5 min)
- [ ] Instalar Java 17+
- [ ] Instalar Maven
- [ ] Instalar MySQL/MariaDB
- [ ] Clonar/descargar proyecto
- [ ] Ejecutar init.sql para crear BD
- [ ] Actualizar application.properties (credenciales DB)
- [ ] Ejecutar `mvn clean install`
- [ ] Ejecutar `mvn spring-boot:run`
- [ ] Abrir http://localhost:8080/api/swagger-ui.html
- [ ] Probar login con admin@grupofamiliar.com / admin123
- [ ] Responder a primera petición protegida (GET /usuarios)
- [ ] Ver FLUJO_OPERACION.md para entender cómo funciona

---

## 🆘 AYUDA Y TROUBLESHOOTING

### Problemas Comunes:

**¿No puedo conectar a la BD?**
→ Ver SETUP.md → Sección "SQL Connection Issues"

**¿El token JWT no funciona?**
→ Ver README_BACKEND.md → Sección "JWT Token"

**¿Puerto 8080 ocupado?**
→ Ver application.properties → `server.port`

**¿Error de compilación Maven?**
→ Verificar Java 17+ → `java -version`

**¿No reconoce los endpoints?**
→ Ver FLUJO_OPERACION.md → Sección "Mapeo de URLs"

---

## 🔐 SEGURIDAD

**JWT Tokens:**
- Access Token: 15 minutos de validez
- Refresh Token: 7 días de validez
- Algoritmo: HMAC-SHA512
- Secret: Configurable en application.properties

**Contraseñas:**
- Cifrada con BCrypt
- Única contraseña en BD: `passwordHash`
- Nunca se devuelve en responses

**CORS:**
- Configurado para localhost:3000 (Angular)
- Configurado para localhost:4200 (React)
- Health para agregar más orígenes

---

## 📞 CONTACTO Y SOPORTE

Para problemas o preguntas:
1. Revisar documentación relevante (arriba)
2. Buscar en Postman Collection las pruebas
3. Revisar logs de aplicación
4. Validar configuración en application.properties

---

## 📝 HISTORIAL DE CAMBIOS

| Versión | Cambios | Fecha |
|---------|---------|-------|
| 1.0 | Backend completo generado | Hoy |
| - | 13 Entidades + JWT | - |
| - | 31+ Endpoints | - |
| - | Documentación completa | - |

---

## 🎯 PRÓXIMOS PASOS (Después de ejecutar)

1. **Fase 1: Validación** (Hoy)
   - Ejecutar aplicación
   - Verificar endpoints en Swagger
   - Probar autenticación

2. **Fase 2: Integración Frontend** (Próximo)
   - Conectar React/Angular
   - Implementar login UI
   - Consumir endpoints

3. **Fase 3: Mejoras** (Futuro)
   - Agregar más entidades
   - Paginación
   - Filtros avanzados
   - Tests unitarios

---

**¡Bienvenido a Grupo Familiar Backend! 🎉**

Comienza con [INICIO_RAPIDO.md](./INICIO_RAPIDO.md) →
