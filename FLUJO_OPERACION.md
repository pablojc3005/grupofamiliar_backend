# 🎬 Flujo de Operación - Backend Grupo Familiar

## 📊 Diagrama de Flujo General

```
┌─────────────────────────────────────────────────────────────────┐
│                      CLIENTE (Frontend)                         │
│              (React, Angular, Vue, etc.)                        │
└────────────────────┬────────────────────────────────────────────┘
                     │
                     │ HTTP Request
                     │ + Headers (Authorization: Bearer <token>)
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                    SPRING BOOT API                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  1. SECURITY LAYER                                              │
│     ├─ JwtAuthenticationFilter                                  │
│     │  └─ Extrae token del header Authorization                │
│     ├─ JwtUtils                                                 │
│     │  └─ Valida firma y expiración                             │
│     └─ CustomUserDetailsService                                 │
│        └─ Carga detalles del usuario de BD                      │
│                                                                 │
│  2. ROUTING LAYER                                               │
│     ├─ AuthController        → /auth/**                         │
│     ├─ UsuarioController     → /usuarios/**                     │
│     ├─ GrupoController       → /grupos/**                       │
│     ├─ SectorController      → /sectores/**                     │
│     └─ ReporteController     → /reportes/**                     │
│                                                                 │
│  3. SERVICE LAYER                                               │
│     ├─ AuthService           (Login, Refresh, Logout)           │
│     ├─ UsuarioService        (CRUD usuarios)                    │
│     ├─ GrupoService          (CRUD grupos)                      │
│     ├─ SectorService         (CRUD sectores)                    │
│     └─ ReporteService        (CRUD reportes)                    │
│                                                                 │
│  4. DATA LAYER                                                  │
│     ├─ UsuarioRepository      (JPA)                             │
│     ├─ GrupoRepository        (JPA)                             │
│     ├─ SectorRepository       (JPA)                             │
│     ├─ ReporteRepository      (JPA)                             │
│     └─ RefreshTokenRepository (JPA)                             │
│                                                                 │
└────────────────┬───────────────────────────────────────────────┘
                 │
                 │ SQL Queries
                 ▼
┌─────────────────────────────────────────────────────────────────┐
│                     DATABASE (MySQL)                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Tablas:                                                        │
│  ├─ rol                      (5 registros)                      │
│  ├─ usuario                  (Encriptado BCrypt)                │
│  ├─ refresh_token            (Tokens persistidos)               │
│  ├─ sector                                                      │
│  ├─ grupo                                                       │
│  ├─ tipo_miembro                                                │
│  ├─ miembro                                                     │
│  ├─ periodo                                                     │
│  ├─ reporte                                                     │
│  ├─ reporte_nuevo_integrante                                    │
│  ├─ categoria_financiera                                        │
│  ├─ movimiento_financiero                                       │
│  └─ notificacion                                                │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔐 Flujo de Autenticación

```
CLIENTE                           SERVIDOR
   │                                 │
   │ 1. POST /auth/login            │
   │    (email, password)            │
   │────────────────────────────────>│
   │                                 │ 2. Buscar usuario en BD
   │                                 │    por email
   │                                 │
   │                                 │ 3. Validar contraseña
   │                                 │    (BCrypt compare)
   │                                 │
   │                                 │ 4. Generar tokens:
   │                                 │    - AccessToken (15 min)
   │                                 │    - RefreshToken (7 días)
   │                                 │
   │                                 │ 5. Guardar RefreshToken
   │                                 │    en BD
   │                                 │
   │ 6. Respuesta con tokens        │
   │<────────────────────────────────┤
   │ {                               │
   │   accessToken: "eyJhbGc...",   │
   │   refreshToken: "eyJhbGc...",  │
   │   usuarioId: 1,                 │
   │   rol: "ADMIN"                  │
   │ }                               │
   │
   │ Guarda tokens en memoria        
   │ o localStorage
```

---

## 🔒 Flujo de Petición Protegida

```
CLIENTE                           SERVIDOR
   │                                 │
   │ 1. GET /usuarios               │
   │    + Header:                    │
   │    Authorization: Bearer <tk>   │
   │────────────────────────────────>│
   │                                 │ 2. JwtAuthenticationFilter
   │                                 │    intercepta request
   │                                 │
   │                                 │ 3. Extrae token del header
   │                                 │
   │                                 │ 4. JwtUtils.validateToken()
   │                                 │    ├─ Verifica firma
   │                                 │    ├─ Verifica expiración
   │                                 │    └─ Extrae subject (email)
   │                                 │
   │                                 │ 5. Busca usuario por email
   │                                 │    en BD
   │                                 │
   │                                 │ 6. Verifica roles y permisos
   │                                 │
   │                                 │ 7. Si es válido:
   │                                 │    - Ejecuta controlador
   │                                 │    - Llama servicio
   │                                 │    - Consulta BD
   │                                 │    - Prepara respuesta
   │                                 │
   │ 8. Retorna datos                │
   │<────────────────────────────────┤
   │ {                               │
   │   success: true,                 │
   │   data: [...]                   │
   │ }                               │
```

---

## 🔄 Flujo de Refresh Token

```
Después de 15 minutos (AccessToken expirado):

CLIENTE                           SERVIDOR
   │                                 │
   │ 1. POST /auth/refresh          │
   │    {                            │
   │      refreshToken: "..."        │
   │    }                            │
   │────────────────────────────────>│
   │                                 │ 2. Buscar RefreshToken
   │                                 │    en BD
   │                                 │
   │                                 │ 3. Validar:
   │                                 │    ├─ Existe
   │                                 │    ├─ No expirado
   │                                 │    └─ No revocado
   │                                 │
   │                                 │ 4. Generar nuevo
   │                                 │    AccessToken (15 min)
   │                                 │
   │ 5. Nuevo token                 │
   │<────────────────────────────────┤
   │ {                               │
   │   accessToken: "eyJhbGc..."    │
   │ }                               │
   │
   │ Actualiza token en memoria
```

---

## 📝 Flujo de Creación de Reporte

```
CLIENTE                          SERVIDOR
   │                                │
   │ 1. POST /reportes             │
   │    + Authorization: Bearer    │
   │    {                          │
   │      grupoId: 1,              │
   │      periodoId: 1,            │
   │      cantHermanos: 15,        │
   │      ...                      │
   │    }                          │
   │──────────────────────────────>│
   │                               │ 2. Validar token JWT
   │                               │
   │                               │ 3. UsuarioController
   │                               │    recibe petición
   │                               │
   │                               │ 4. ReporteService.
   │                               │    crearOActualizar()
   │                               │
   │                               │ 5. Buscar grupo en BD
   │                               │
   │                               │ 6. Buscar período en BD
   │                               │
   │                               │ 7. ¿Ya existe reporte
   │                               │    grupo/período?
   │                               │    ├─ Si: Actualizar
   │                               │    └─ No: Crear nuevo
   │                               │
   │                               │ 8. Guardar en BD con
   │                               │    estado: BORRADOR
   │                               │
   │ 9. Reporte creado            │
   │<──────────────────────────────┤
   │ {                             │
   │   id: 1,                      │
   │   estado: "BORRADOR",         │
   │   ...                         │
   │ }                             │
│
│ 10. ENVIAR: PATCH /reportes/1/enviar
│
│─────────────────────────────────>│ Estado: ENVIADO
│<──────────────────────────────────│ 
│
│ 11. APROBAR: PATCH /reportes/1/aprobar
│
│─────────────────────────────────>│ Estado: APROBADO
│<──────────────────────────────────│
```

---

## 🗺️ Relación entre Entidades

```
ROL (5 registros)
├─ ADMIN
├─ SUP_GENERAL
├─ SUP_SECTORIAL
├─ LIDER
└─ TESORERO

USUARIO (rol_id → ROL.id)
├─ id_rol ──> ROL
├─ Como LIDER ──> GRUPO como líder (1-1)
├─ Como SUP_SECTORIAL ──> SECTOR como supervisor
└─ RefreshTokens (1-N)

SECTOR (supervisor_id → USUARIO.id)
├─ Tiene N GRUPOS
└─ Tiene N MOVIMIENTOS_FINANCIEROS

GRUPO (sector_id → SECTOR.id, lider_id → USUARIO.id)
├─ Pertenece a 1 SECTOR
├─ Tiene 1 LIDER
├─ Tiene N MIEMBROS
├─ Tiene N REPORTES
└─ Tiene N MOVIMIENTOS_FINANCIEROS

TIPO_MIEMBRO (5 registros)
├─ HERMANO
├─ AMIGO
├─ ADOLESCENTE
├─ NIÑO_CRISTIANO
└─ NIÑO_AMIGO

MIEMBRO (grupo_id → GRUPO.id, tipo_miembro_id → TIPO_MIEMBRO.id)
├─ Pertenece a 1 GRUPO
├─ Tiene 1 TIPO_MIEMBRO
└─ Puede estar en REPORTE_NUEVO_INTEGRANTE

PERIODO (Generados cada semana)
├─ Tiene N REPORTES
└─ Tiene N MOVIMIENTOS_FINANCIEROS

REPORTE (grupo_id → GRUPO.id, periodo_id → PERIODO.id, lider_id → USUARIO.id)
├─ De 1 GRUPO
├─ De 1 PERIODO (única combinación)
├─ Del 1 LIDER
├─ Estados: BORRADOR → ENVIADO → APROBADO
├─ Registra: Asistencia, conversiones, visitas, ofrendas
└─ Tiene N REPORTE_NUEVO_INTEGRANTE

REPORTE_NUEVO_INTEGRANTE (reporte_id → REPORTE.id, miembro_id → MIEMBRO.id)
├─ Registra nuevos integrantes de la semana
└─ Pueden formalizarse como MIEMBRO

CATEGORIA_FINANCIERA (8 registros)
├─ INGRESO
│  ├─ Ofrenda Sábado
│  ├─ Ofrenda Miércoles
│  ├─ Ofrenda Niños
│  └─ Otras Ofrendas
└─ EGRESO
   ├─ Gasto Operativo
   ├─ Gasto Materiales
   ├─ Transporte
   └─ Alimentos

MOVIMIENTO_FINANCIERO (categoria_id → CATEGORIA_FINANCIERA.id, registrado_por → USUARIO.id)
├─ De 1 CATEGORIA
├─ De 1 PERIODO
├─ Del 1 USUARIO (quien registra)
├─ Opcional: SECTOR
├─ Opcional: GRUPO
├─ Opcional: REPORTE
└─ Registra: Monto, descripción, fecha

NOTIFICACION (usuario_id → USUARIO.id)
├─ Para 1 USUARIO
├─ Tipos: REPORTE_PENDIENTE, REPORTE_APROBADO, AVISO
└─ Puede estar leída o no
```

---

## 🔄 Estados del Reporte

```
┌──────────┐
│ BORRADOR │  (Estado inicial)
│(Editable)│
└────┬─────┘
     │ Líder guarda los datos y envía
     ▼
┌──────────┐
│ ENVIADO  │  (En espera de aprobación)
│(Revisable)
└────┬─────┘
     │ 
     ├─ Supervisor Sectorial/General aprueba
     │  ▼
     │ ┌──────────┐
     │ │ APROBADO │  (Estado final)
     │ │ (Cerrado) │
     │ └──────────┘
     │
     └─ O no está correcto, se rechaza
        ▼
     ┌──────────┐
     │ BORRADOR │  (Vuelve a editar)
     │(Editable)│
     └──────────┘
```

---

## 💾 Ciclo de Vida de una Petición

```
1. LLEGADA
   - Cliente envía HTTP Request
   - Spring recibe en DispatcherServlet

2. SEGURIDAD
   - JwtAuthenticationFilter intercepta
   - Valida token JWT
   - Crea Authentication en SecurityContext

3. ROUTING
   - DispatcherServlet mapea a controlador
   - Valida método HTTP y path

4. CONTROLADOR
   - Recibe datos (body, params, headers)
   - Valida formato (DTOs)
   - Llama servicio

5. SERVICIO
   - Lógica de negocio
   - Comprobaciones (existe, activo, etc.)
   - Llama repositorio

6. REPOSITORIO
   - Construye query JPA/Hibernate
   - Traduce a SQL
   - Ejecuta en BD
   - Regresa resultados

7. TRANSFORMACIÓN
   - Entity → DTO
   - Construye respuesta ApiResponse

8. RESPUESTA
   - Serializa JSON
   - Retorna HTTP 200/201/400/404/500
   - Headers (Content-Type, etc.)
   - Cliente recibe response
```

---

## 📈 Rendimiento y Escalabilidad

```
Optimizaciones implementadas:
├─ LazyLoading en relaciones
├─ Índices en bases de datos
├─ Caché de tokens validados
├─ Transacciones JDBC
├─ Queries eficientes en JPA
└─ Cifrado BCrypt solo en login

Consideraciones futuras:
├─ Redis para caché de tokens
├─ Paginación en listados largos
├─ Índices adicionales en BD
├─ Connection pooling
├─ Rate limiting
└─ Compresión de respuestas
```

---

## 🎯 Mapeo de URLs

```
┌─────────────────────────────────────────────────────────────┐
│  http://localhost:8080/api/swagger-ui.html                 │
│  ├─ Documentación interactiva                              │
│  ├─ Probar endpoints                                       │
│  └─ Ver esquema de requests/responses                      │
│                                                            │
├─────────────────────────────────────────────────────────────┤
│  http://localhost:8080/api/auth/**                         │
│  ├─ POST /login     - Autenticarse                         │
│  ├─ POST /refresh   - Refrescar token                      │
│  └─ POST /logout    - Cerrar sesión                        │
│                                                            │
├─────────────────────────────────────────────────────────────┤
│  http://localhost:8080/api/usuarios/**                     │
│  ├─ GET     - Listar todos                                 │
│  ├─ GET /{id} - Obtener uno                                │
│  ├─ POST    - Crear                                        │
│  ├─ PUT /{id} - Actualizar                                 │
│  └─ DELETE /{id} - Desactivar                              │
│                                                            │
├─────────────────────────────────────────────────────────────┤
│  http://localhost:8080/api/grupos/**                       │
│  ├─ GET           - Listar                                 │
│  ├─ GET /{id}     - Obtener                                │
│  ├─ GET /sector/.. - Por sector                            │
│  ├─ POST          - Crear                                  │
│  ├─ PUT /{id}     - Editar                                 │
│  └─ DELETE /{id}  - Desactivar                             │
│                                                            │
├─────────────────────────────────────────────────────────────┤
│  http://localhost:8080/api/sectores/**                     │
│  ├─ GET    - Listar                                        │
│  ├─ POST   - Crear                                         │
│  ├─ PUT    - Editar                                        │
│  └─ DELETE - Desactivar                                    │
│                                                            │
├─────────────────────────────────────────────────────────────┤
│  http://localhost:8080/api/reportes/**                     │
│  ├─ GET                  - Listar todos                    │
│  ├─ GET /{id}            - Obtener uno                     │
│  ├─ GET /grupo/..        - Del grupo                       │
│  ├─ GET /periodo/..      - Del período                     │
│  ├─ POST                 - Crear/editar                    │
│  ├─ PATCH /{id}/enviar   - Enviar                          │
│  ├─ PATCH /{id}/aprobar  - Aprobar                         │
│  └─ PATCH /{id}/rechazar - Rechazar                        │
└─────────────────────────────────────────────────────────────┘
```

---

Este documento es una referencia visual del flujo completo de la aplicación.
Para más detalles, revisa la documentación específica de cada componente.
