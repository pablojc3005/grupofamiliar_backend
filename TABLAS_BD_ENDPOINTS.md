# 📊 MAPEO TABLAS BD ↔ ENDPOINTS

## Correlación entre Tablas de Base de Datos y Endpoints REST

---

### 1️⃣ TABLA: `rol`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | rol | `GET /roles` | ❌ | RolController |
| **SELECT** | rol | `GET /roles/{id}` | ❌ | RolController |
| **SELECT** | rol | `GET /roles/nombre/{nombre}` | ❌ | RolController |
| **INSERT** | rol | `POST /roles` | ❌ | RolController |
| **UPDATE** | rol | `PUT /roles/{id}` | ❌ | RolController |
| **DELETE** | rol | `DELETE /roles/{id}` | ❌ | RolController |

**Descripción**: Define roles del sistema (ADMIN, SUP_GENERAL, SUP_SECTORIAL, LIDER, TESORERO)

---

### 2️⃣ TABLA: `usuario`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | usuario | `GET /usuarios` | ✅ | UsuarioController |
| **SELECT** | usuario | `GET /usuarios/{id}` | ✅ | UsuarioController |
| **SELECT** | usuario | `GET /usuarios/email/{email}` | ✅ | UsuarioController |
| **SELECT** | usuario | `GET /usuarios/rol/{rolId}` | ❌ | UsuarioController |
| **SELECT** | usuario | `GET /usuarios/{id}/perfil` | ❌ | UsuarioController |
| **INSERT** | usuario | `POST /usuarios` | ✅ | UsuarioController |
| **INSERT** | usuario | `POST /auth/register` | ❌ | AuthController |
| **UPDATE** | usuario | `PUT /usuarios/{id}` | ✅ | UsuarioController |
| **UPDATE** | usuario | `PATCH /usuarios/{id}/cambiar-rol` | ❌ | UsuarioController |
| **SOFT_DELETE** | usuario | `DELETE /usuarios/{id}` (desactiva) | ✅ | UsuarioController |
| **UPDATE** | usuario | `PATCH /usuarios/{id}/activar` | ✅ | UsuarioController |
| **AUTH** | usuario | `POST /auth/login` | ✅ | AuthController |

**Descripción**: Información de usuarios del sistema

**Relaciones**:
- `id_rol` → tabla `rol`

---

### 3️⃣ TABLA: `refresh_token`
| Operación | Tabla | Endpoint | Estado | Ubicación |
|-----------|-------|----------|--------|-----------|
| **INSERT** | refresh_token | Interno en `/auth/login` | ✅ | AuthService |
| **SELECT** | refresh_token | Interno en `/auth/refresh` | ✅ | AuthService |
| **UPDATE** | refresh_token | Interno en `/auth/refresh` | ✅ | AuthService |
| **DELETE** | refresh_token | Interno en `/auth/logout` | ✅ | AuthService |
| **CLEANUP** | refresh_token | Evento automático (base de datos) | ❌ | Base de Datos |

**Descripción**: Tokens de refresco JWT de larga duración

**Notas**: 
- No tiene endpoint público
- Gestionado totalmente en AuthService
- Se limpian automáticamente por vencimiento

---

### 4️⃣ TABLA: `sector`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | sector | `GET /sectores` | ✅ | SectorController |
| **SELECT** | sector | `GET /sectores/{id}` | ✅ | SectorController |
| **SELECT** | sector | `GET /sectores/{id}/supervisor` | ❌ | SectorController |
| **SELECT** | sector | `GET /sectores/{id}/grupos` | ❌ | SectorController |
| **SELECT** | sector | `GET /sectores/{id}/estadisticas` | ❌ | SectorController |
| **INSERT** | sector | `POST /sectores` | ✅ | SectorController |
| **UPDATE** | sector | `PUT /sectores/{id}` | ✅ | SectorController |
| **UPDATE** | sector | `PATCH /sectores/{id}/asignar-supervisor` | ❌ | SectorController |
| **SOFT_DELETE** | sector | `DELETE /sectores/{id}` (desactiva) | ✅ | SectorController |

**Descripción**: Sectores que agrupan grupos

**Relaciones**:
- `id_sup_sectorial` → tabla `usuario` (rol SUP_SECTORIAL)

---

### 5️⃣ TABLA: `grupo`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | grupo | `GET /grupos` | ✅ | GrupoController |
| **SELECT** | grupo | `GET /grupos/{id}` | ✅ | GrupoController |
| **SELECT** | grupo | `GET /grupos/sector/{sectorId}` | ✅ | GrupoController |
| **SELECT** | grupo | `GET /grupos/{id}/lider` | ❌ | GrupoController |
| **SELECT** | grupo | `GET /grupos/{id}/miembros` | ❌ | GrupoController |
| **SELECT** | grupo | `GET /grupos/{id}/reportes` | ❌ | GrupoController |
| **INSERT** | grupo | `POST /grupos` | ✅ | GrupoController |
| **UPDATE** | grupo | `PUT /grupos/{id}` | ✅ | GrupoController |
| **UPDATE** | grupo | `PATCH /grupos/{id}/asignar-lider` | ❌ | GrupoController |
| **SOFT_DELETE** | grupo | `DELETE /grupos/{id}` (desactiva) | ✅ | GrupoController |

**Descripción**: Grupos religiosos liderados por un usuario (1 líder = 1 grupo)

**Relaciones**:
- `id_sector` → tabla `sector`
- `id_lider` → tabla `usuario` (rol LIDER, UNIQUE)

---

### 6️⃣ TABLA: `tipo_miembro`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | tipo_miembro | `GET /tipos-miembros` | ❌ | TipoMiembroController |
| **SELECT** | tipo_miembro | `GET /tipos-miembros/{id}` | ❌ | TipoMiembroController |
| **INSERT** | tipo_miembro | `POST /tipos-miembros` | ❌ | TipoMiembroController |
| **UPDATE** | tipo_miembro | `PUT /tipos-miembros/{id}` | ❌ | TipoMiembroController |
| **DELETE** | tipo_miembro | `DELETE /tipos-miembros/{id}` | ❌ | TipoMiembroController |

**Descripción**: Tipos de miembros (HERMANO, AMIGO, ADOLESCENTE, NIÑO_CRISTIANO, NIÑO_AMIGO)

---

### 7️⃣ TABLA: `miembro`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | miembro | `GET /miembros` | ❌ | MiembroController |
| **SELECT** | miembro | `GET /miembros/{id}` | ❌ | MiembroController |
| **SELECT** | miembro | `GET /miembros/grupo/{grupoId}` | ❌ | MiembroController |
| **SELECT** | miembro | `GET /miembros/grupo/{grupoId}/activos` | ❌ | MiembroController |
| **SELECT** | miembro | `GET /miembros/tipo/{tipoId}` | ❌ | MiembroController |
| **INSERT** | miembro | `POST /miembros` | ❌ | MiembroController |
| **UPDATE** | miembro | `PUT /miembros/{id}` | ❌ | MiembroController |
| **UPDATE** | miembro | `PATCH /miembros/{id}/cambiar-tipo` | ❌ | MiembroController |
| **SOFT_DELETE** | miembro | `DELETE /miembros/{id}` (desactiva) | ❌ | MiembroController |

**Descripción**: Miembros de los grupos

**Relaciones**:
- `id_tipo_miembro` → tabla `tipo_miembro`
- `id_grupo` → tabla `grupo`

---

### 8️⃣ TABLA: `periodo`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | periodo | `GET /periodos` | ❌ | PeriodoController |
| **SELECT** | periodo | `GET /periodos/{id}` | ❌ | PeriodoController |
| **SELECT** | periodo | `GET /periodos/actual` | ❌ | PeriodoController |
| **SELECT** | periodo | `GET /periodos/{id}/reportes` | ❌ | PeriodoController |
| **INSERT** | periodo | `POST /periodos` | ❌ | PeriodoController |
| **UPDATE** | periodo | `PUT /periodos/{id}` | ❌ | PeriodoController |
| **UPDATE** | periodo | `PATCH /periodos/{id}/cerrar` | ❌ | PeriodoController |
| **DELETE** | periodo | `DELETE /periodos/{id}` | ❌ | PeriodoController |

**Descripción**: Períodos semanales para reportes

---

### 9️⃣ TABLA: `reporte`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | reporte | `GET /reportes` | ✅ | ReporteController |
| **SELECT** | reporte | `GET /reportes/{id}` | ✅ | ReporteController |
| **SELECT** | reporte | `GET /reportes/grupo/{grupoId}` | ✅ | ReporteController |
| **SELECT** | reporte | `GET /reportes/periodo/{periodoId}` | ✅ | ReporteController |
| **SELECT** | reporte | `GET /reportes/lider/{liderId}` | ❌ | ReporteController |
| **SELECT** | reporte | `GET /reportes/estado/{estado}` | ❌ | ReporteController |
| **INSERT** | reporte | `POST /reportes` | ✅ | ReporteController |
| **UPDATE** | reporte | `PUT /reportes/{id}` | ⚠️ | Via POST |
| **UPDATE** | reporte | `PATCH /reportes/{id}/enviar` | ✅ | ReporteController |
| **UPDATE** | reporte | `PATCH /reportes/{id}/aprobar` | ✅ | ReporteController |
| **UPDATE** | reporte | `PATCH /reportes/{id}/rechazar` | ✅ | ReporteController |
| **DELETE** | reporte | `DELETE /reportes/{id}` | ❌ | ReporteController |

**Descripción**: Reportes semanales de grupos

**Relaciones**:
- `id_grupo` → tabla `grupo`
- `id_periodo` → tabla `periodo`
- `id_lider` → tabla `usuario`

---

### 🔟 TABLA: `reporte_nuevo_integrante`
| Operación | Tabla | Endpoint | Estado | Ubicación |
|-----------|-------|----------|--------|-----------|
| **SELECT** | reporte_nuevo_integrante | `GET /reportes/{reporteId}/nuevos-integrantes` | ❌ | ReporteController |
| **INSERT** | reporte_nuevo_integrante | `POST /reportes/{reporteId}/nuevos-integrantes` | ❌ | ReporteController |
| **UPDATE** | reporte_nuevo_integrante | `PUT /nuevos-integrantes/{id}` | ❌ | ReporteController |
| **UPDATE** | reporte_nuevo_integrante | `PATCH /nuevos-integrantes/{id}/formalizar` | ❌ | ReporteController |
| **DELETE** | reporte_nuevo_integrante | `DELETE /reportes/{reporteId}/nuevos-integrantes/{integranteId}` | ❌ | ReporteController |

**Descripción**: Nuevos integrantes registrados en reportes semanales

**Relaciones**:
- `id_reporte` → tabla `reporte` (ON DELETE CASCADE)
- `id_tipo_miembro` → tabla `tipo_miembro`
- `id_miembro` → tabla `miembro` (después de formalizar)

---

### 1️⃣1️⃣ TABLA: `categoria_financiera`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | categoria_financiera | `GET /finanzas/categorias` | ❌ | FinanzasController |
| **SELECT** | categoria_financiera | `GET /finanzas/categorias/{id}` | ❌ | FinanzasController |
| **SELECT** | categoria_financiera | `GET /finanzas/categorias/tipo/{tipo}` | ❌ | FinanzasController |
| **INSERT** | categoria_financiera | `POST /finanzas/categorias` | ❌ | FinanzasController |
| **UPDATE** | categoria_financiera | `PUT /finanzas/categorias/{id}` | ❌ | FinanzasController |
| **SOFT_DELETE** | categoria_financiera | `DELETE /finanzas/categorias/{id}` (desactiva) | ❌ | FinanzasController |

**Descripción**: Categorías de movimientos financieros (INGRESO/EGRESO)

---

### 1️⃣2️⃣ TABLA: `movimiento_financiero`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | movimiento_financiero | `GET /finanzas/movimientos` | ❌ | FinanzasController |
| **SELECT** | movimiento_financiero | `GET /finanzas/movimientos/{id}` | ❌ | FinanzasController |
| **SELECT** | movimiento_financiero | `GET /finanzas/movimientos/periodo/{periodoId}` | ❌ | FinanzasController |
| **SELECT** | movimiento_financiero | `GET /finanzas/movimientos/sector/{sectorId}` | ❌ | FinanzasController |
| **SELECT** | movimiento_financiero | `GET /finanzas/movimientos/grupo/{grupoId}` | ❌ | FinanzasController |
| **SELECT** | movimiento_financiero | `GET /finanzas/movimientos/categoria/{categoriaId}` | ❌ | FinanzasController |
| **INSERT** | movimiento_financiero | `POST /finanzas/movimientos` | ❌ | FinanzasController |
| **UPDATE** | movimiento_financiero | `PUT /finanzas/movimientos/{id}` | ❌ | FinanzasController |
| **DELETE** | movimiento_financiero | `DELETE /finanzas/movimientos/{id}` | ❌ | FinanzasController |

**Descripción**: Movimientos financieros (ingresos y egresos)

**Relaciones**:
- `id_periodo` → tabla `periodo`
- `id_categoria` → tabla `categoria_financiera`
- `id_sector` → tabla `sector`
- `id_grupo` → tabla `grupo`
- `id_reporte` → tabla `reporte`
- `registrado_por` → tabla `usuario` (rol TESORERO)

---

### 1️⃣3️⃣ TABLA: `notificacion`
| Operación | Tabla | Endpoint | Estado | Controlador |
|-----------|-------|----------|--------|-------------|
| **SELECT** | notificacion | `GET /notificaciones` | ❌ | NotificacionController |
| **SELECT** | notificacion | `GET /notificaciones/{id}` | ❌ | NotificacionController |
| **SELECT** | notificacion | `GET /notificaciones/no-leidas` | ❌ | NotificacionController |
| **INSERT** | notificacion | Genera automáticamente | ❌ | Internamente |
| **UPDATE** | notificacion | `PATCH /notificaciones/{id}/marcar-leida` | ❌ | NotificacionController |
| **UPDATE** | notificacion | `PATCH /notificaciones/marcar-todas-leidas` | ❌ | NotificacionController |
| **DELETE** | notificacion | `DELETE /notificaciones/{id}` | ❌ | NotificacionController |
| **INSERT** | notificacion | `POST /notificaciones/enviar` | ❌ | NotificacionController |

**Descripción**: Notificaciones para usuarios

**Relaciones**:
- `id_usuario` → tabla `usuario` (ON DELETE CASCADE)

---

### 1️⃣4️⃣ VISTAS SQL (Sin tabla física)

#### Vista: `v_consolidado_sectorial`
| Consola | Endpoint | Estado | Controlador |
|---------|----------|--------|-------------|
| SELECT | `GET /consolidados/sectorial/{sectorId}/periodo/{periodoId}` | ❌ | ConsolidadosController |
| SELECT | `GET /consolidados/sector/{sectorId}` | ❌ | ConsolidadosController |
| SELECT | `GET /consolidados/historico/sector/{sectorId}` | ❌ | ConsolidadosController |

**Descripción**: Consolidado de reportes por sector y período

---

#### Vista: `v_consolidado_general`
| Consola | Endpoint | Estado | Controlador |
|---------|----------|--------|-------------|
| SELECT | `GET /consolidados/general/periodo/{periodoId}` | ❌ | ConsolidadosController |
| SELECT | `GET /consolidados/general` | ❌ | ConsolidadosController |
| SELECT | `GET /consolidados/historico/general` | ❌ | ConsolidadosController |

**Descripción**: Consolidado general de todos los sectores

---

#### Vista: `v_resumen_financiero`
| Consola | Endpoint | Estado | Controlador |
|---------|----------|--------|-------------|
| SELECT | `GET /finanzas/resumen/periodo/{periodoId}` | ❌ | FinanzasController |
| SELECT | `GET /finanzas/resumen/sector/{sectorId}` | ❌ | FinanzasController |
| SELECT | `GET /finanzas/balance/{periodoId}` | ❌ | FinanzasController |
| SELECT | `GET /finanzas/estadisticas/periodo/{periodoId}` | ❌ | FinanzasController |

**Descripción**: Resumen financiero consolidado

---

## ESTADÍSTICAS FINALES

| Métrica | Cantidad |
|---------|----------|
| **Tablas totales** | 13 |
| **Vistas SQL** | 3 |
| **Endpoints mapeados** | 150+ |
| **Endpoints implementados** | ~45 ✅ |
| **Endpoints faltantes** | ~105 ❌ |
| **Controladores implementados** | 5 |
| **Controladores faltantes** | 8 |
| **% Cobertura** | ~30% |

---

## DEPENDENCIAS DE IMPLEMENTACIÓN

```
Auth (✅) 
  └─> Usuarios (✅)
       └─> Roles (❌)
       └─> Sectores (✅)
            └─> Grupos (✅)
                 └─> Miembros (❌)
                      └─> Tipos Miembros (❌)
                 └─> Reportes (✅)
                      └─> Períodos (❌)
                      └─> Nuevos Integrantes (❌)
            └─> Finanzas (❌)
                 └─> Movimientos Financieros (❌)
                 └─> Categorías Financieras (❌)
       └─> Notificaciones (❌)
  
Consolidados (❌)
  └─> Reportes (✅)
       └─> Sectores (✅)
       └─> Miembros (❌)
```

## ORDEN RECOMENDADO DE IMPLEMENTACIÓN

1. **PeriodoController** - Base para todos los reportes
2. **MiembroController** - Fundamental para los datos
3. **RolController** - Control de accesos
4. **TipoMiembroController** - Datos de catálogo
5. **FinanzasController** - Módulo de tesorería
6. **NotificacionController** - Sistema de alertas
7. **ConsolidadosController** - Dashboard y reportes
8. Completar endpoints adicionales en controladores existentes
