# 🗺️ MAPA RÁPIDO DEL PROYECTO

## Estado de Controladores

| # | Controlador | Path | Endpoints | Cobertura | Estado |
|---|---|---|---|---|---|
| 1 | **AuthController** | `/auth` | 3/4 | 75% | ✅ Activo |
| 2 | **UsuarioController** | `/usuarios` | 8/10 | 80% | ✅ Activo |
| 3 | **SectorController** | `/sectores` | 6/9 | 67% | ✅ Activo |
| 4 | **GrupoController** | `/grupos` | 6/9 | 67% | ✅ Activo |
| 5 | **ReporteController** | `/reportes` | 10/12+ | 80% | ✅ Activo |
| 6 | **RolController** | `/roles` | 0/6 | 0% | ❌ Faltante |
| 7 | **MiembroController** | `/miembros` | 0/9 | 0% | ❌ Faltante |
| 8 | **TipoMiembroController** | `/tipos-miembros` | 0/5 | 0% | ❌ Faltante |
| 9 | **PeriodoController** | `/periodos` | 0/8 | 0% | ❌ Faltante |
| 10 | **FinanzasController** | `/finanzas` | 0/18 | 0% | ❌ Faltante |
| 11 | **NotificacionController** | `/notificaciones` | 0/7 | 0% | ❌ Faltante |
| 12 | **ConsolidadosController** | `/consolidados` | 0/10+ | 0% | ❌ Faltante |

---

## Métodos HTTP Implementados

### ✅ Existentes
```
POST   /auth/login                                  ✅ 200 OK
POST   /auth/refresh                               ✅ 200 OK
POST   /auth/logout                                ✅ 200 OK
GET    /usuarios                                   ✅ 200 OK
GET    /usuarios/{id}                              ✅ 200 OK
GET    /usuarios/email/{email}                     ✅ 200 OK
POST   /usuarios                                   ✅ 201 Created
PUT    /usuarios/{id}                              ✅ 200 OK
DELETE /usuarios/{id}                              ✅ 200 OK
PATCH  /usuarios/{id}/activar                      ✅ 200 OK
GET    /sectores                                   ✅ 200 OK
GET    /sectores/{id}                              ✅ 200 OK
POST   /sectores                                   ✅ 201 Created
PUT    /sectores/{id}                              ✅ 200 OK
DELETE /sectores/{id}                              ✅ 200 OK
GET    /grupos                                     ✅ 200 OK
GET    /grupos/{id}                                ✅ 200 OK
GET    /grupos/sector/{sectorId}                   ✅ 200 OK
POST   /grupos                                     ✅ 201 Created
PUT    /grupos/{id}                                ✅ 200 OK
DELETE /grupos/{id}                                ✅ 200 OK
GET    /reportes                                   ✅ 200 OK
GET    /reportes/{id}                              ✅ 200 OK
GET    /reportes/grupo/{grupoId}                   ✅ 200 OK
GET    /reportes/periodo/{periodoId}               ✅ 200 OK
POST   /reportes                                   ✅ 201 Created
PATCH  /reportes/{id}/enviar                       ✅ 200 OK
PATCH  /reportes/{id}/aprobar                      ✅ 200 OK
PATCH  /reportes/{id}/rechazar                     ✅ 200 OK
```

### ❌ No Existentes
```
POST   /auth/register
GET    /usuarios/rol/{rolId}
GET    /usuarios/{id}/perfil
PATCH  /usuarios/{id}/cambiar-rol}
GET    /roles
GET    /roles/{id}
POST   /roles
PUT    /roles/{id}
DELETE /roles/{id}
GET    /sectores/{id}/supervisor
GET    /sectores/{id}/grupos
GET    /sectores/{id}/estadisticas
PATCH  /sectores/{id}/asignar-supervisor
GET    /grupos/{id}/lider
GET    /grupos/{id}/miembros
GET    /grupos/{id}/reportes
PATCH  /grupos/{id}/asignar-lider
GET    /miembros
GET    /miembros/{id}
GET    /miembros/grupo/{grupoId}
GET    /miembros/tipo/{tipoId}
POST   /miembros
PUT    /miembros/{id}
DELETE /miembros/{id}
PATCH  /miembros/{id}/cambiar-tipo
... y 60+ endpoints más
```

---

## Tablas de BD y Cobertura

```
rol ............................ ░░░░░░░░░░  0% (falta RolController)
usuario ........................ ████████░░ 80% (8/10 endpoints)
refresh_token .................. ████████░░ 80% (internos en Auth)
sector ......................... ██████░░░░ 67% (6/9 endpoints)
grupo .......................... ██████░░░░ 67% (6/9 endpoints)
tipo_miembro ................... ░░░░░░░░░░  0% (falta TipoMiembroController)
miembro ........................ ░░░░░░░░░░  0% (falta MiembroController)
periodo ........................ ░░░░░░░░░░  0% (falta PeriodoController)
reporte ........................ ████████░░ 80% (10/12 endpoints)
reporte_nuevo_integrante ....... ░░░░░░░░░░  0% (integrado en ReporteController)
categoria_financiera ........... ░░░░░░░░░░  0% (falta FinanzasController)
movimiento_financiero .......... ░░░░░░░░░░  0% (falta FinanzasController)
notificacion ................... ░░░░░░░░░░  0% (falta NotificacionController)
Vista: v_consolidado_sectorial . ░░░░░░░░░░  0% (falta ConsolidadosController)
Vista: v_consolidado_general ... ░░░░░░░░░░  0% (falta ConsolidadosController)
Vista: v_resumen_financiero .... ░░░░░░░░░░  0% (falta ConsolidadosController)
```

---

## 🎯 Prioridades Inmediatas

### 🔴 P0 - CRÍTICAS (Bloquean desarrollo)
1. **PeriodoController** → Necesario para crear reportes
2. **MiembroController** → Base del sistema de seguimiento
3. **FinanzasController** → Core para rol TESORERO

### 🟠 P1 - ALTAS
1. **RolController** → Gestión de permisos
2. **ConsolidadosController** → Dashboard ejecutivo
3. **NotificacionController** → Alertas del sistema

### 🟡 P2 - MEDIAS
1. **TipoMiembroController** → Catálogo
2. Completar endpoints faltantes en controladores existentes

---

## 📂 Estructura de Carpetas

```
src/main/java/com/example/grupofamiliar_backend/
├── controller/
│   ├── AuthController.java .......................... ✅
│   ├── UsuarioController.java ....................... ✅
│   ├── SectorController.java ........................ ✅
│   ├── GrupoController.java ......................... ✅
│   ├── ReporteController.java ....................... ✅
│   ├── RolController.java ........................... ❌
│   ├── MiembroController.java ....................... ❌
│   ├── TipoMiembroController.java ................... ❌
│   ├── PeriodoController.java ....................... ❌
│   ├── FinanzasController.java ...................... ❌
│   ├── NotificacionController.java ................. ❌
│   └── ConsolidadosController.java ................. ❌
├── service/
│   ├── AuthService.java ............................. ✅
│   ├── UsuarioService.java .......................... ✅
│   ├── SectorService.java ........................... ✅
│   ├── GrupoService.java ............................ ✅
│   ├── ReporteService.java .......................... ✅
│   ├── RolService.java ............................. ❌
│   ├── MiembroService.java .......................... ❌
│   ├── TipoMiembroService.java ....................... ❌
│   ├── PeriodoService.java .......................... ❌
│   ├── FinanzasService.java ......................... ❌
│   ├── NotificacionService.java ..................... ❌
│   └── ConsolidadosService.java ..................... ❌
├── repository/
│   ├── UsuarioRepository.java ....................... ✅
│   ├── SectorRepository.java ........................ ✅
│   ├── GrupoRepository.java ......................... ✅
│   ├── ReporteRepository.java ....................... ✅
│   ├── RolRepository.java ........................... ✅ (existe)
│   ├── MiembroRepository.java ....................... ✅ (existe)
│   ├── TipoMiembroRepository.java ................... ✅ (existe)
│   ├── PeriodoRepository.java ....................... ✅ (existe)
│   ├── CategoriaFinancieraRepository.java .......... ✅ (existe)
│   ├── MovimientoFinancieroRepository.java ......... ✅ (existe)
│   ├── NotificacionRepository.java ................. ✅ (existe)
│   └── RefreshTokenRepository.java ................. ✅
└── entity/
    ├── Usuario.java .................................. ✅
    ├── Rol.java ...................................... ✅
    ├── Sector.java ................................... ✅
    ├── Grupo.java .................................... ✅
    ├── Miembro.java .................................. ✅
    ├── TipoMiembro.java .............................. ✅
    ├── Periodo.java .................................. ✅
    ├── Reporte.java .................................. ✅
    ├── ReporteNuevoIntegrante.java .................. ✅
    ├── CategoriaFinanciera.java ..................... ✅
    ├── MovimientoFinanciero.java .................... ✅
    ├── Notificacion.java ............................. ✅
    └── RefreshToken.java ............................. ✅
```

**Nota**: ✅ en repository y entity significa que YA EXISTEN, pero faltan los controladores y servicios

---

## 📋 Checklist de Implementación

### Fase 1: Fundación (1-2 horas)
- [ ] PeriodoController + PeriodoService
- [ ] RolController + RolService
- [ ] TipoMiembroController + TipoMiembroService

### Fase 2: Core Data (2-3 horas)
- [ ] MiembroController + MiembroService
- [ ] Agregar endpoints de relación en GrupoController

### Fase 3: Finanzas (2-3 horas)
- [ ] FinanzasController + FinanzasService
- [ ] Integrar vistas SQL de resumen financiero

### Fase 4: Dashboard (1-2 horas)
- [ ] ConsolidadosController + ConsolidadosService
- [ ] Integrar vistas SQL consolidadas

### Fase 5: Notificaciones (1 hora)
- [ ] NotificacionController + NotificacionService
- [ ] Disparadores automáticos en otros servicios

### Fase 6: Pulido (2-3 horas)
- [ ] Completar endpoints adicionales
- [ ] Testing y validación
- [ ] Ajustes de seguridad y validaciones

---

## 🔗 Relaciones de Dependencia

```
AuthController (✅)
    ↓
UsuarioController (✅) ← Requiere RolService (❌)
    ↓
Sectores (✅) ← Requiere MiembroController (❌)
    ↓
Grupos (✅) ← Requiere MiembroController (❌)
    ↓
Reportes (✅) ← Requiere PeriodoController (❌)
    ↓
Consolidados (❌) ← Requiere Reportes (✅)
    ↓
Finanzas (❌) ← Requiere Categorías (❌)
    ↓
Notificaciones (❌) ← Requiere otros módulos
```

---

## 📊 Resumen Estadístico

| Métrica | Cantidad |
|---------|----------|
| Tablas implementadas | 13/13 ✅ |
| Vistas SQL creadas | 3/3 ✅ |
| Repositories creados | 13/13 ✅ |
| Entities creadas | 13/13 ✅ |
| Controladores implementados | 5/12 (42%) |
| Servicios implementados | 5/12 (42%) |
| Endpoints totales requeridos | 150+ |
| Endpoints implementados | ~45 (30%) |
| Horas estimadas para completar | 13-15h |

---

**Última actualización**: 27 de marzo de 2026  
**Generado por**: Mapeo Automático de Endpoints
