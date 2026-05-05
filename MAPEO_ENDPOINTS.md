# 📋 MAPEO COMPLETO DE ENDPOINTS - GRUPO FAMILIAR API

## Resumen Estados
- ✅ **Implementado**: Endpoint existe y funciona
- ⚠️ **Parcial**: Existe pero le faltan funcionalidades
- ❌ **Faltante**: No existe aún

---

## 1. AUTENTICACIÓN 🔐
**Base Path**: `/auth`  
**Controlador**: ✅ `AuthController.java`

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| POST | `/auth/login` | ✅ | Autentificación con email/contraseña → JWT |
| POST | `/auth/refresh` | ✅ | Refresca Access Token con Refresh Token |
| POST | `/auth/logout` | ✅ | Cierra sesión (revoca Refresh Token) |
| POST | `/auth/register` | ❌ | Crear nuevo usuario (registro público) |

---

## 2. USUARIOS 👥
**Base Path**: `/usuarios`  
**Controlador**: ✅ `UsuarioController.java`

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/usuarios` | ✅ | Obtener todos los usuarios activos |
| GET | `/usuarios/{id}` | ✅ | Obtener usuario por ID |
| GET | `/usuarios/email/{email}` | ✅ | Obtener usuario por email |
| POST | `/usuarios` | ✅ | Crear nuevo usuario (ADMIN) |
| PUT | `/usuarios/{id}` | ✅ | Actualizar usuario |
| DELETE | `/usuarios/{id}` | ✅ | Desactivar usuario |
| PATCH | `/usuarios/{id}/activar` | ✅ | Activar usuario desactivado |
| GET | `/usuarios/rol/{rolId}` | ❌ | Obtener usuarios por rol |
| GET | `/usuarios/sector/{sectorId}` | ❌ | Obtener usuarios de un sector |
| PATCH | `/usuarios/{id}/cambiar-rol` | ❌ | Cambiar rol de usuario |
| GET | `/usuarios/{id}/perfil` | ❌ | Obtener perfil completo del usuario |

---

## 3. ROLES 🎯
**Base Path**: `/roles`  
**Controlador**: ❌ **FALTANTE**

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/roles` | ❌ | Obtener todos los roles |
| GET | `/roles/{id}` | ❌ | Obtener rol por ID |
| POST | `/roles` | ❌ | Crear nuevo rol (ADMIN) |
| PUT | `/roles/{id}` | ❌ | Actualizar rol |
| DELETE | `/roles/{id}` | ❌ | Eliminar rol |
| GET | `/roles/nombre/{nombre}` | ❌ | Obtener rol por nombre |

---

## 4. SECTORES 📍
**Base Path**: `/sectores`  
**Controlador**: ✅ `SectorController.java`

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/sectores` | ✅ | Obtener todos los sectores activos |
| GET | `/sectores/{id}` | ✅ | Obtener sector por ID |
| POST | `/sectores` | ✅ | Crear nuevo sector |
| PUT | `/sectores/{id}` | ✅ | Actualizar sector |
| DELETE | `/sectores/{id}` | ✅ | Desactivar sector |
| GET | `/sectores/{id}/supervisor` | ❌ | Obtener datos del supervisor sectorial |
| GET | `/sectores/{id}/grupos` | ❌ | Obtener todos los grupos del sector |
| GET | `/sectores/{id}/estadisticas` | ❌ | Obtener estadísticas consolidadas del sector |
| PATCH | `/sectores/{id}/asignar-supervisor` | ❌ | Asignar supervisor sectorial |

---

## 5. GRUPOS 👨‍👩‍👧‍👦
**Base Path**: `/grupos`  
**Controlador**: ✅ `GrupoController.java`

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/grupos` | ✅ | Obtener todos los grupos activos |
| GET | `/grupos/{id}` | ✅ | Obtener grupo por ID |
| GET | `/grupos/sector/{sectorId}` | ✅ | Obtener grupos por sector |
| POST | `/grupos` | ✅ | Crear nuevo grupo |
| PUT | `/grupos/{id}` | ✅ | Actualizar grupo |
| DELETE | `/grupos/{id}` | ✅ | Desactivar grupo |
| GET | `/grupos/{id}/lider` | ❌ | Obtener datos del líder |
| GET | `/grupos/{id}/miembros` | ❌ | Obtener miembros del grupo |
| GET | `/grupos/{id}/reportes` | ❌ | Obtener reportes del grupo |
| PATCH | `/grupos/{id}/asignar-lider` | ❌ | Asignar o cambiar líder |

---

## 6. MIEMBROS 👤
**Base Path**: `/miembros`  
**Controlador**: ❌ **FALTANTE**

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/miembros` | ❌ | Obtener todos los miembros |
| GET | `/miembros/{id}` | ❌ | Obtener miembro por ID |
| GET | `/miembros/grupo/{grupoId}` | ❌ | Obtener miembros de un grupo |
| GET | `/miembros/tipo/{tipoId}` | ❌ | Obtener miembros por tipo |
| POST | `/miembros` | ❌ | Crear nuevo miembro |
| PUT | `/miembros/{id}` | ❌ | Actualizar miembro |
| DELETE | `/miembros/{id}` | ❌ | Desactivar/eliminar miembro |
| PATCH | `/miembros/{id}/cambiar-tipo` | ❌ | Cambiar tipo de miembro |
| GET | `/miembros/grupo/{grupoId}/activos` | ❌ | Obtener miembros activos por grupo |

---

## 7. TIPOS DE MIEMBROS 🏷️
**Base Path**: `/tipos-miembros`  
**Controlador**: ❌ **FALTANTE**

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/tipos-miembros` | ❌ | Obtener todos los tipos |
| GET | `/tipos-miembros/{id}` | ❌ | Obtener tipo por ID |
| POST | `/tipos-miembros` | ❌ | Crear nuevo tipo (ADMIN) |
| PUT | `/tipos-miembros/{id}` | ❌ | Actualizar tipo |
| DELETE | `/tipos-miembros/{id}` | ❌ | Eliminar tipo |

---

## 8. REPORTES 📊
**Base Path**: `/reportes`  
**Controlador**: ✅ `ReporteController.java`

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/reportes` | ✅ | Obtener todos los reportes |
| GET | `/reportes/{id}` | ✅ | Obtener reporte por ID |
| GET | `/reportes/grupo/{grupoId}` | ✅ | Obtener reportes de un grupo |
| GET | `/reportes/periodo/{periodoId}` | ✅ | Obtener reportes de un período |
| POST | `/reportes` | ✅ | Crear/actualizar reporte (guardar en BORRADOR) |
| PATCH | `/reportes/{id}/enviar` | ✅ | Enviar reporte (BORRADOR → ENVIADO) |
| PATCH | `/reportes/{id}/aprobar` | ✅ | Aprobar reporte (ENVIADO → APROBADO) |
| PATCH | `/reportes/{id}/rechazar` | ✅ | Rechazar reporte (ENVIADO → BORRADOR) |
| DELETE | `/reportes/{id}` | ❌ | Eliminar reporte en borrador |
| GET | `/reportes/{id}/nuevos-integrantes` | ❌ | Obtener nuevos integrantes del reporte |
| POST | `/reportes/{id}/nuevos-integrantes` | ❌ | Agregar nuevo integrante al reporte |
| DELETE | `/reportes/{id}/nuevos-integrantes/{integranteId}` | ❌ | Eliminar nuevo integrante del reporte |
| GET | `/reportes/lider/{liderId}` | ❌ | Obtener reportes de un líder |
| GET | `/reportes/estado/{estado}` | ❌ | Obtener reportes por estado |

---

## 9. PERIODOS ⏰
**Base Path**: `/periodos`  
**Controlador**: ❌ **FALTANTE**

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/periodos` | ❌ | Obtener todos los períodos |
| GET | `/periodos/{id}` | ❌ | Obtener período por ID |
| GET | `/periodos/actual` | ❌ | Obtener período actual (activo) |
| POST | `/periodos` | ❌ | Crear nuevo período (ADMIN) |
| PUT | `/periodos/{id}` | ❌ | Actualizar período |
| PATCH | `/periodos/{id}/cerrar` | ❌ | Cerrar un período |
| DELETE | `/periodos/{id}` | ❌ | Eliminar período sin reportes |
| GET | `/periodos/{id}/reportes` | ❌ | Obtener reportes del período |

---

## 10. NUEVOS INTEGRANTES 🆕
**Base Path**: `/nuevos-integrantes` o bajo `/reportes/{id}/nuevos-integrantes`  
**Controlador**: ❌ **FALTANTE** (se podría integrar en ReporteController)

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/reportes/{reporteId}/nuevos-integrantes` | ❌ | Listar nuevos integrantes del reporte |
| POST | `/reportes/{reporteId}/nuevos-integrantes` | ❌ | Agregar nuevo integrante |
| PUT | `/nuevos-integrantes/{id}` | ❌ | Actualizar nuevo integrante |
| DELETE | `/reportes/{reporteId}/nuevos-integrantes/{integranteId}` | ❌ | Eliminar nuevo integrante |
| PATCH | `/nuevos-integrantes/{id}/formalizar` | ❌ | Formalizar como miembro permanente |

---

## 11. FINANZAS 💰
**Base Path**: `/finanzas`  
**Controlador**: ❌ **FALTANTE** (se necesita un FinanzasController)

### 11.1 Movimientos Financieros

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/finanzas/movimientos` | ❌ | Obtener todos los movimientos |
| GET | `/finanzas/movimientos/{id}` | ❌ | Obtener movimiento por ID |
| GET | `/finanzas/movimientos/periodo/{periodoId}` | ❌ | Obtener movimientos del período |
| GET | `/finanzas/movimientos/sector/{sectorId}` | ❌ | Obtener movimientos del sector |
| GET | `/finanzas/movimientos/grupo/{grupoId}` | ❌ | Obtener movimientos del grupo |
| GET | `/finanzas/movimientos/categoria/{categoriaId}` | ❌ | Obtener movimientos por categoría |
| POST | `/finanzas/movimientos` | ❌ | Registrar nuevo movimiento (TESORERO) |
| PUT | `/finanzas/movimientos/{id}` | ❌ | Actualizar movimiento (TESORERO) |
| DELETE | `/finanzas/movimientos/{id}` | ❌ | Eliminar movimiento (TESORERO) |
| POST | `/finanzas/movimientos/ingresos-reportes/{periodoId}` | ❌ | Procesar ingresos de reportes del período |

### 11.2 Categorías Financieras

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/finanzas/categorias` | ❌ | Obtener todas las categorías |
| GET | `/finanzas/categorias/{id}` | ❌ | Obtener categoría por ID |
| GET | `/finanzas/categorias/tipo/{tipo}` | ❌ | Obtener categorías por tipo (INGRESO/EGRESO) |
| POST | `/finanzas/categorias` | ❌ | Crear nueva categoría (ADMIN) |
| PUT | `/finanzas/categorias/{id}` | ❌ | Actualizar categoría |
| DELETE | `/finanzas/categorias/{id}` | ❌ | Desactivar categoría |

### 11.3 Reportes y Resúmenes Financieros

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/finanzas/resumen/periodo/{periodoId}` | ❌ | Resumen financiero del período |
| GET | `/finanzas/resumen/sector/{sectorId}` | ❌ | Resumen financiero del sector |
| GET | `/finanzas/balance/{periodoId}` | ❌ | Balance INGRESOS - EGRESOS del período |
| GET | `/finanzas/estadisticas/periodo/{periodoId}` | ❌ | Estadísticas financieras del período |
| GET | `/finanzas/ofrenda-total/periodo/{periodoId}` | ❌ | Total de ofrendas registradas |

---

## 12. CONSOLIDADOS Y ESTADÍSTICAS 📈
**Base Path**: `/consolidados`  
**Controlador**: ❌ **FALTANTE** (se necesita un ConsolidadosController)

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/consolidados/sectorial/{sectorId}/periodo/{periodoId}` | ❌ | Consolidado sectorial (v_consolidado_sectorial) |
| GET | `/consolidados/general/periodo/{periodoId}` | ❌ | Consolidado general (v_consolidado_general) |
| GET | `/consolidados/sector/{sectorId}` | ❌ | Consolidado actual del sector |
| GET | `/consolidados/general` | ❌ | Consolidado general actual |
| GET | `/consolidados/historico/sector/{sectorId}` | ❌ | Historico de consolidados del sector |
| GET | `/consolidados/historico/general` | ❌ | Histórico de consolidados generales |
| POST | `/consolidados/exportar/pdf` | ❌ | Exportar consolidado a PDF |
| POST | `/consolidados/exportar/excel` | ❌ | Exportar consolidado a Excel |

---

## 13. NOTIFICACIONES 🔔
**Base Path**: `/notificaciones`  
**Controlador**: ❌ **FALTANTE**

| Método | Endpoint | Estado | Descripción |
|--------|----------|--------|-------------|
| GET | `/notificaciones` | ❌ | Obtener notificaciones del usuario actual |
| GET | `/notificaciones/{id}` | ❌ | Obtener notificación por ID |
| GET | `/notificaciones/no-leidas` | ❌ | Obtener notificaciones no leídas |
| PATCH | `/notificaciones/{id}/marcar-leida` | ❌ | Marcar notificación como leída |
| PATCH | `/notificaciones/marcar-todas-leidas` | ❌ | Marcar todas como leídas |
| DELETE | `/notificaciones/{id}` | ❌ | Eliminar notificación |
| POST | `/notificaciones/enviar` | ❌ | Enviar notificación a usuario (ADMIN) |

---

## RESUMEN DE IMPLEMENTACIÓN

### ✅ Controladores Implementados (3 de 8)
1. **AuthController** - Autenticación JWT completa
2. **UsuarioController** - CRUD de usuarios
3. **ReporteController** - Reportes semanales con estados
4. **SectorController** - CRUD de sectores
5. **GrupoController** - CRUD de grupos

### ❌ Controladores Faltantes (5)
1. **RolController** - Gestión de roles
2. **MiembroController** - CRUD de miembros
3. **TipoMiembroController** - Tipos de miembros
4. **PeriodoController** - Gestión de períodos
5. **FinanzasController** - Movimientos financieros, categorías y reportes
6. **NotificacionController** - Sistema de notificaciones
7. **ConsolidadosController** - Vistas consolidadas y estadísticas

### ⚠️ Funcionalidades Incompletas
- Endpoints adicionales en controladores existentes (filtros, búsquedas avanzadas)
- Integración de vistas SQL en endpoints de consolidados
- Sistema de archivos para exportar PDF/Excel
- Eventos WebSocket para notificaciones en tiempo real

---

## RECOMENDACIONES PRIORIDAD

### 🔴 CRÍTICA (Se usan inmediatamente)
1. `MiembroController` - Fundamental para el sistema
2. `PeriodoController` - Base para los reportes
3. `FinanzasController` - Módulo de tesorería esencial

### 🟠 ALTA (Necesario para cobertura completa)
1. `RolController` - Gestión de accesos
2. `ConsolidadosController` - Dashboard/reportes ejecutivos
3. `NotificacionController` - Sistema de alertas

### 🟡 MEDIA (Mejoras y complementos)
1. Endpoints adicionales en controladores existentes
2. Exportación a PDF/Excel
3. WebSocket para notificaciones en tiempo real
4. Búsquedas avanzadas y filtros

---

## PRÓXIMOS PASOS

1. ✅ Crear **MiembroController**
2. ✅ Crear **PeriodoController**
3. ✅ Crear **FinanzasController** (Movimientos y Categorías)
4. ✅ Crear **RolController**
5. ✅ Crear **ConsolidadosController** (conectar vistas SQL)
6. ✅ Crear **NotificacionController**
7. ⚠️ Completar endpoints adicionales en controladores existentes
