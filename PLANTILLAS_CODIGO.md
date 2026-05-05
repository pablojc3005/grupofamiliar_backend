# 🛠️ PLANTILLAS DE IMPLEMENTACIÓN - CONTROLADORES FALTANTES

## Índice Rápido
1. [RolController](#rolcontroller)
2. [TipoMiembroController](#tiomembrocontroller)
3. [PeriodoController](#periodocontroller)
4. [MiembroController](#miembrocontroller)
5. [FinanzasController](#finanzascontroller)
6. [NotificacionController](#notificacioncontroller)
7. [ConsolidadosController](#consolidadoscontroller)

---

## RolController

**Ubicación**: `src/main/java/com/example/grupofamiliar_backend/controller/RolController.java`

**Endpoints Faltantes**:
```
✅ GET    /roles
✅ GET    /roles/{id}
✅ GET    /roles/nombre/{nombre}
✅ POST   /roles
✅ PUT    /roles/{id}
✅ DELETE /roles/{id}
```

**Plantilla**:
```java
package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.entity.Rol;
import com.example.grupofamiliar_backend.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestión de roles del sistema")
@SecurityRequirement(name = "Bearer Authentication")
public class RolController {

    private final RolService rolService;

    @GetMapping
    @Operation(summary = "Obtiene todos los roles")
    public ResponseEntity<ApiResponse<List<Rol>>> obtenerTodos() {
        List<Rol> roles = rolService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Roles obtenidos", roles));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un rol por ID")
    public ResponseEntity<ApiResponse<Rol>> obtenerPorId(@PathVariable Byte id) {
        try {
            Rol rol = rolService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Rol obtenido", rol));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtiene un rol por nombre")
    public ResponseEntity<ApiResponse<Rol>> obtenerPorNombre(@PathVariable String nombre) {
        try {
            Rol rol = rolService.obtenerPorNombre(nombre);
            return ResponseEntity.ok(new ApiResponse<>(true, "Rol obtenido", rol));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo rol")
    public ResponseEntity<ApiResponse<Rol>> crear(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.crear(rol);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Rol creado exitosamente", nuevoRol));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un rol existente")
    public ResponseEntity<ApiResponse<Rol>> actualizar(@PathVariable Byte id, 
                                                        @RequestBody Rol rol) {
        try {
            Rol rolActualizado = rolService.actualizar(id, rol);
            return ResponseEntity.ok(new ApiResponse<>(true, "Rol actualizado", rolActualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un rol")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Byte id) {
        try {
            rolService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Rol eliminado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
```

---

## TipoMiembroController

**Ubicación**: `src/main/java/com/example/grupofamiliar_backend/controller/TipoMiembroController.java`

**Endpoints Faltantes**:
```
✅ GET    /tipos-miembros
✅ GET    /tipos-miembros/{id}
✅ POST   /tipos-miembros
✅ PUT    /tipos-miembros/{id}
✅ DELETE /tipos-miembros/{id}
```

**Plantilla**:

```java
package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-miembros")
@RequiredArgsConstructor
@Tag(name = "Tipos de Miembros", description = "Catálogo de tipos de miembros")
@SecurityRequirement(name = "Bearer Authentication")
public class TipoMiembroController {

    private final TipoMiembroService tipoMiembroService;

    @GetMapping
    @Operation(summary = "Obtiene todos los tipos de miembros")
    public ResponseEntity<ApiResponse<List<TipoMiembro>>> obtenerTodos() {
        List<TipoMiembro> tipos = tipoMiembroService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Tipos de miembros obtenidos", tipos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un tipo de miembro por ID")
    public ResponseEntity<ApiResponse<TipoMiembro>> obtenerPorId(@PathVariable Byte id) {
        try {
            TipoMiembro tipo = tipoMiembroService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tipo obtenido", tipo));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo tipo de miembro")
    public ResponseEntity<ApiResponse<TipoMiembro>> crear(@RequestBody TipoMiembro tipo) {
        try {
            TipoMiembro nuevoTipo = tipoMiembroService.crear(tipo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Tipo de miembro creado exitosamente", nuevoTipo));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un tipo de miembro")
    public ResponseEntity<ApiResponse<TipoMiembro>> actualizar(@PathVariable Byte id,
                                                               @RequestBody TipoMiembro tipo) {
        try {
            TipoMiembro tipoActualizado = tipoMiembroService.actualizar(id, tipo);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tipo actualizado", tipoActualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un tipo de miembro")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Byte id) {
        try {
            tipoMiembroService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tipo de miembro eliminado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
```

---

## PeriodoController

**Ubicación**: `src/main/java/com/example/grupofamiliar_backend/controller/PeriodoController.java`

**Endpoints Faltantes**:
```
✅ GET    /periodos
✅ GET    /periodos/{id}
✅ GET    /periodos/actual
✅ POST   /periodos
✅ PUT    /periodos/{id}
✅ PATCH  /periodos/{id}/cerrar
✅ DELETE /periodos/{id}
✅ GET    /periodos/{id}/reportes
```

**Plantilla**:
```java
package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.entity.Periodo;
import com.example.grupofamiliar_backend.service.PeriodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/periodos")
@RequiredArgsConstructor
@Tag(name = "Períodos", description = "Gestión de períodos semanales")
@SecurityRequirement(name = "Bearer Authentication")
public class PeriodoController {

    private final PeriodoService periodoService;

    @GetMapping
    @Operation(summary = "Obtiene todos los períodos")
    public ResponseEntity<ApiResponse<List<Periodo>>> obtenerTodos() {
        List<Periodo> periodos = periodoService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Períodos obtenidos", periodos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un período por ID")
    public ResponseEntity<ApiResponse<Periodo>> obtenerPorId(@PathVariable Long id) {
        try {
            Periodo periodo = periodoService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Período obtenido", periodo));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/actual")
    @Operation(summary = "Obtiene el período actual (activo)")
    public ResponseEntity<ApiResponse<Periodo>> obtenerActual() {
        try {
            Periodo periodo = periodoService.obtenerActual();
            return ResponseEntity.ok(new ApiResponse<>(true, "Período actual obtenido", periodo));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/{id}/reportes")
    @Operation(summary = "Obtiene todos los reportes de un período")
    public ResponseEntity<ApiResponse<?>> obtenerReportes(@PathVariable Long id) {
        try {
            var reportes = periodoService.obtenerReportesPeriodo(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reportes del período obtenidos", reportes));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo período")
    public ResponseEntity<ApiResponse<Periodo>> crear(@RequestBody Periodo periodo) {
        try {
            Periodo nuevoPeriodo = periodoService.crear(periodo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Período creado exitosamente", nuevoPeriodo));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un período existente")
    public ResponseEntity<ApiResponse<Periodo>> actualizar(@PathVariable Long id, 
                                                           @RequestBody Periodo periodo) {
        try {
            Periodo periodoActualizado = periodoService.actualizar(id, periodo);
            return ResponseEntity.ok(new ApiResponse<>(true, "Período actualizado", periodoActualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/cerrar")
    @Operation(summary = "Cierra un período (ya no se pueden crear reportes)")
    public ResponseEntity<ApiResponse<Periodo>> cerrar(@PathVariable Long id) {
        try {
            Periodo periodoCerrado = periodoService.cerrar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Período cerrado", periodoCerrado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un período (solo si no tiene reportes)")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            periodoService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Período eliminado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
```

---

## MiembroController

**Ubicación**: `src/main/java/com/example/grupofamiliar_backend/controller/MiembroController.java`

**Endpoints Faltantes**:
```
✅ GET    /miembros
✅ GET    /miembros/{id}
✅ GET    /miembros/grupo/{grupoId}
✅ GET    /miembros/grupo/{grupoId}/activos
✅ GET    /miembros/tipo/{tipoId}
✅ POST   /miembros
✅ PUT    /miembros/{id}
✅ DELETE /miembros/{id}
✅ PATCH  /miembros/{id}/cambiar-tipo
```

**Plantilla** (resumida, es similar a las anteriores):

```java
package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/miembros")
@RequiredArgsConstructor
@Tag(name = "Miembros", description = "Gestión de miembros de grupos")
@SecurityRequirement(name = "Bearer Authentication")
public class MiembroController {

    private final MiembroService miembroService;

    @GetMapping
    @Operation(summary = "Obtiene todos los miembros")
    public ResponseEntity<ApiResponse<List<Miembro>>> obtenerTodos() {
        List<Miembro> miembros = miembroService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Miembros obtenidos", miembros));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un miembro por ID")
    public ResponseEntity<ApiResponse<Miembro>> obtenerPorId(@PathVariable Long id) {
        try {
            Miembro miembro = miembroService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Miembro obtenido", miembro));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/grupo/{grupoId}")
    @Operation(summary = "Obtiene miembros de un grupo (incluye inactivos)")
    public ResponseEntity<ApiResponse<List<Miembro>>> obtenerPorGrupo(@PathVariable Long grupoId) {
        try {
            List<Miembro> miembros = miembroService.obtenerPorGrupo(grupoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Miembros obtenidos", miembros));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/grupo/{grupoId}/activos")
    @Operation(summary = "Obtiene miembros activos de un grupo")
    public ResponseEntity<ApiResponse<List<Miembro>>> obtenerActivosPorGrupo(@PathVariable Long grupoId) {
        try {
            List<Miembro> miembros = miembroService.obtenerActivosPorGrupo(grupoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Miembros activos obtenidos", miembros));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/tipo/{tipoId}")
    @Operation(summary = "Obtiene miembros por tipo")
    public ResponseEntity<ApiResponse<List<Miembro>>> obtenerPorTipo(@PathVariable Byte tipoId) {
        try {
            List<Miembro> miembros = miembroService.obtenerPorTipo(tipoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Miembros obtenidos", miembros));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo miembro")
    public ResponseEntity<ApiResponse<Miembro>> crear(@RequestBody Miembro miembro) {
        try {
            Miembro nuevoMiembro = miembroService.crear(miembro);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Miembro creado exitosamente", nuevoMiembro));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un miembro")
    public ResponseEntity<ApiResponse<Miembro>> actualizar(@PathVariable Long id,
                                                           @RequestBody Miembro miembro) {
        try {
            Miembro miembroActualizado = miembroService.actualizar(id, miembro);
            return ResponseEntity.ok(new ApiResponse<>(true, "Miembro actualizado", miembroActualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/cambiar-tipo")
    @Operation(summary = "Cambia el tipo de miembro")
    public ResponseEntity<ApiResponse<Miembro>> cambiarTipo(@PathVariable Long id,
                                                            @RequestParam Byte nuevoTipoId) {
        try {
            Miembro miembroActualizado = miembroService.cambiarTipo(id, nuevoTipoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tipo de miembro actualizado", miembroActualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactiva un miembro")
    public ResponseEntity<ApiResponse<Void>> desactivar(@PathVariable Long id) {
        try {
            miembroService.desactivar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Miembro desactivado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
```

---

## FinanzasController

**Ubicación**: `src/main/java/com/example/grupofamiliar_backend/controller/FinanzasController.java`

**Endpoints Faltantes**:
```
✅ GET    /finanzas/movimientos
✅ GET    /finanzas/movimientos/{id}
✅ GET    /finanzas/movimientos/periodo/{periodoId}
✅ GET    /finanzas/movimientos/sector/{sectorId}
✅ GET    /finanzas/movimientos/grupo/{grupoId}
✅ GET    /finanzas/movimientos/categoria/{categoriaId}
✅ POST   /finanzas/movimientos
✅ PUT    /finanzas/movimientos/{id}
✅ DELETE /finanzas/movimientos/{id}
✅ GET    /finanzas/categorias
✅ GET    /finanzas/categorias/{id}
✅ GET    /finanzas/categorias/tipo/{tipo}
✅ POST   /finanzas/categorias
✅ PUT    /finanzas/categorias/{id}
✅ DELETE /finanzas/categorias/{id}
✅ GET    /finanzas/resumen/periodo/{periodoId}
✅ GET    /finanzas/balance/{periodoId}
✅ GET    /finanzas/ofrenda-total/periodo/{periodoId}
```

**Plantilla**:
```java
package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.entity.CategoriaFinanciera;
import com.example.grupofamiliar_backend.entity.MovimientoFinanciero;
import com.example.grupofamiliar_backend.service.FinanzasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/finanzas")
@RequiredArgsConstructor
@Tag(name = "Finanzas", description = "Gestión de movimientos financieros y reportes")
@SecurityRequirement(name = "Bearer Authentication")
public class FinanzasController {

    private final FinanzasService finanzasService;

    // ────────────────────────────────────
    // MOVIMIENTOS FINANCIEROS
    // ────────────────────────────────────

    @GetMapping("/movimientos")
    @Operation(summary = "Obtiene todos los movimientos financieros")
    public ResponseEntity<ApiResponse<List<MovimientoFinanciero>>> obtenerMovimientos() {
        List<MovimientoFinanciero> movimientos = finanzasService.obtenerMovimientos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Movimientos obtenidos", movimientos));
    }

    @GetMapping("/movimientos/{id}")
    @Operation(summary = "Obtiene un movimiento por ID")
    public ResponseEntity<ApiResponse<MovimientoFinanciero>> obtenerMovimientoPorId(@PathVariable Long id) {
        try {
            MovimientoFinanciero movimiento = finanzasService.obtenerMovimiento(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimiento obtenido", movimiento));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/movimientos/periodo/{periodoId}")
    @Operation(summary = "Obtiene movimientos de un período")
    public ResponseEntity<ApiResponse<List<MovimientoFinanciero>>> obtenerMovimientosPorPeriodo(@PathVariable Long periodoId) {
        try {
            List<MovimientoFinanciero> movimientos = finanzasService.obtenerMovimientosPorPeriodo(periodoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimientos obtenidos", movimientos));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/movimientos/sector/{sectorId}")
    @Operation(summary = "Obtiene movimientos de un sector")
    public ResponseEntity<ApiResponse<List<MovimientoFinanciero>>> obtenerMovimientosPorSector(@PathVariable Long sectorId) {
        try {
            List<MovimientoFinanciero> movimientos = finanzasService.obtenerMovimientosPorSector(sectorId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimientos obtenidos", movimientos));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/movimientos/grupo/{grupoId}")
    @Operation(summary = "Obtiene movimientos de un grupo")
    public ResponseEntity<ApiResponse<List<MovimientoFinanciero>>> obtenerMovimientosPorGrupo(@PathVariable Long grupoId) {
        try {
            List<MovimientoFinanciero> movimientos = finanzasService.obtenerMovimientosPorGrupo(grupoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimientos obtenidos", movimientos));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/movimientos/categoria/{categoriaId}")
    @Operation(summary = "Obtiene movimientos por categoría")
    public ResponseEntity<ApiResponse<List<MovimientoFinanciero>>> obtenerMovimientosPorCategoria(@PathVariable Byte categoriaId) {
        try {
            List<MovimientoFinanciero> movimientos = finanzasService.obtenerMovimientosPorCategoria(categoriaId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimientos obtenidos", movimientos));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping("/movimientos")
    @Operation(summary = "Registra un nuevo movimiento financiero (TESORERO)")
    public ResponseEntity<ApiResponse<MovimientoFinanciero>> crearMovimiento(@RequestBody MovimientoFinanciero movimiento) {
        try {
            MovimientoFinanciero nuevoMovimiento = finanzasService.crearMovimiento(movimiento);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Movimiento registrado", nuevoMovimiento));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/movimientos/{id}")
    @Operation(summary = "Actualiza un movimiento financiero")
    public ResponseEntity<ApiResponse<MovimientoFinanciero>> actualizarMovimiento(@PathVariable Long id, 
                                                                                   @RequestBody MovimientoFinanciero movimiento) {
        try {
            MovimientoFinanciero actualizado = finanzasService.actualizarMovimiento(id, movimiento);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimiento actualizado", actualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/movimientos/{id}")
    @Operation(summary = "Elimina un movimiento financiero")
    public ResponseEntity<ApiResponse<Void>> eliminarMovimiento(@PathVariable Long id) {
        try {
            finanzasService.eliminarMovimiento(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Movimiento eliminado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    // ────────────────────────────────────
    // CATEGORÍAS FINANCIERAS
    // ────────────────────────────────────

    @GetMapping("/categorias")
    @Operation(summary = "Obtiene todas las categorías financieras")
    public ResponseEntity<ApiResponse<List<CategoriaFinanciera>>> obtenerCategorias() {
        List<CategoriaFinanciera> categorias = finanzasService.obtenerCategorias();
        return ResponseEntity.ok(new ApiResponse<>(true, "Categorías obtenidas", categorias));
    }

    @GetMapping("/categorias/{id}")
    @Operation(summary = "Obtiene una categoría por ID")
    public ResponseEntity<ApiResponse<CategoriaFinanciera>> obtenerCategoriaPorId(@PathVariable Byte id) {
        try {
            CategoriaFinanciera categoria = finanzasService.obtenerCategoria(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Categoría obtenida", categoria));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/categorias/tipo/{tipo}")
    @Operation(summary = "Obtiene categorías por tipo (INGRESO/EGRESO)")
    public ResponseEntity<ApiResponse<List<CategoriaFinanciera>>> obtenerCategoriasPorTipo(@PathVariable String tipo) {
        try {
            List<CategoriaFinanciera> categorias = finanzasService.obtenerCategoriasPorTipo(tipo);
            return ResponseEntity.ok(new ApiResponse<>(true, "Categorías obtenidas", categorias));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping("/categorias")
    @Operation(summary = "Crea una nueva categoría (ADMIN)")
    public ResponseEntity<ApiResponse<CategoriaFinanciera>> crearCategoria(@RequestBody CategoriaFinanciera categoria) {
        try {
            CategoriaFinanciera nuevaCategoria = finanzasService.crearCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Categoría creada", nuevaCategoria));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/categorias/{id}")
    @Operation(summary = "Actualiza una categoría")
    public ResponseEntity<ApiResponse<CategoriaFinanciera>> actualizarCategoria(@PathVariable Byte id, 
                                                                                 @RequestBody CategoriaFinanciera categoria) {
        try {
            CategoriaFinanciera actualizada = finanzasService.actualizarCategoria(id, categoria);
            return ResponseEntity.ok(new ApiResponse<>(true, "Categoría actualizada", actualizada));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/categorias/{id}")
    @Operation(summary = "Desactiva una categoría")
    public ResponseEntity<ApiResponse<Void>> eliminarCategoria(@PathVariable Byte id) {
        try {
            finanzasService.eliminarCategoria(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Categoría desactivada"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    // ────────────────────────────────────
    // REPORTES Y RESÚMENES FINANCIEROS
    // ────────────────────────────────────

    @GetMapping("/resumen/periodo/{periodoId}")
    @Operation(summary = "Obtiene resumen financiero del período (v_resumen_financiero)")
    public ResponseEntity<ApiResponse<?>> obtenerResumenPeriodo(@PathVariable Long periodoId) {
        try {
            var resumen = finanzasService.obtenerResumenFinanciero(periodoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Resumen obtenido", resumen));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/balance/{periodoId}")
    @Operation(summary = "Obtiene balance INGRESOS - EGRESOS del período")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerBalance(@PathVariable Long periodoId) {
        try {
            var balance = finanzasService.obtenerBalance(periodoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Balance obtenido", balance));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/ofrenda-total/periodo/{periodoId}")
    @Operation(summary = "Obtiene total de ofrendas registradas en el período")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerOfrendaTotal(@PathVariable Long periodoId) {
        try {
            var ofrenda = finanzasService.obtenerOfrendaTotal(periodoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Total de ofrendas obtenido", ofrenda));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
```

---

## NotificacionController

**Ubicación**: `src/main/java/com/example/grupofamiliar_backend/controller/NotificacionController.java`

**Plantilla** (similar a las anteriores, con GET para el usuario actual):

```java
package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "Sistema de notificaciones para usuarios")
@SecurityRequirement(name = "Bearer Authentication")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    @Operation(summary = "Obtiene las notificaciones del usuario actual")
    public ResponseEntity<ApiResponse<List<Notificacion>>> obtenerMis(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<Notificacion> notificaciones = notificacionService.obtenerDelUsuario(email);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificaciones obtenidas", notificaciones));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una notificación por ID")
    public ResponseEntity<ApiResponse<Notificacion>> obtenerPorId(@PathVariable Long id) {
        try {
            Notificacion notificacion = notificacionService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificación obtenida", notificacion));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/no-leidas")
    @Operation(summary = "Obtiene notificaciones no leídas del usuario actual")
    public ResponseEntity<ApiResponse<List<Notificacion>>> obtenerNoLeidas(Authentication authentication) {
        try {
            String email = authentication.getName();
            List<Notificacion> notificaciones = notificacionService.obtenerNoLeidas(email);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificaciones no leídas obtenidas", notificaciones));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/marcar-leida")
    @Operation(summary = "Marca una notificación como leída")
    public ResponseEntity<ApiResponse<Notificacion>> marcarLeida(@PathVariable Long id) {
        try {
            Notificacion notificacion = notificacionService.marcarLeida(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificación marcada como leída", notificacion));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/marcar-todas-leidas")
    @Operation(summary = "Marca todas las notificaciones como leídas")
    public ResponseEntity<ApiResponse<Void>> marcarTodasLeidas(Authentication authentication) {
        try {
            String email = authentication.getName();
            notificacionService.marcarTodasLeidas(email);
            return ResponseEntity.ok(new ApiResponse<>(true, "Todas las notificaciones marcadas como leídas"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una notificación")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            notificacionService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Notificación eliminada"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping("/enviar")
    @Operation(summary = "Envía una notificación a un usuario (ADMIN)")
    public ResponseEntity<ApiResponse<Void>> enviar(@RequestParam Long idUsuario,
                                                    @RequestParam String titulo,
                                                    @RequestParam String cuerpo,
                                                    @RequestParam(required = false) String tipo) {
        try {
            notificacionService.enviar(idUsuario, titulo, cuerpo, tipo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Notificación enviada"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
```

---

## ConsolidadosController

**Ubicación**: `src/main/java/com/example/grupofamiliar_backend/controller/ConsolidadosController.java`

**Plantilla** (especial: conecte con vistas SQL):
```java
package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.service.ConsolidadosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consolidados")
@RequiredArgsConstructor
@Tag(name = "Consolidados", description = "Reportes consolidados y estadísticas")
@SecurityRequirement(name = "Bearer Authentication")
public class ConsolidadosController {

    private final ConsolidadosService consolidadosService;

    @GetMapping("/sectorial/{sectorId}/periodo/{periodoId}")
    @Operation(summary = "Consolidado sectorial (v_consolidado_sectorial)")
    public ResponseEntity<ApiResponse<?>> obtenerConsolidadoSectorial(@PathVariable Long sectorId, 
                                                                       @PathVariable Long periodoId) {
        try {
            var consolidado = consolidadosService.obtenerConsolidadoSectorial(sectorId, periodoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Consolidado sectorial obtenido", consolidado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/general/periodo/{periodoId}")
    @Operation(summary = "Consolidado general (v_consolidado_general)")
    public ResponseEntity<ApiResponse<?>> obtenerConsolidadoGeneral(@PathVariable Long periodoId) {
        try {
            var consolidado = consolidadosService.obtenerConsolidadoGeneral(periodoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Consolidado general obtenido", consolidado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/sector/{sectorId}")
    @Operation(summary = "Consolidado actual del sector")
    public ResponseEntity<ApiResponse<?>> obtenerConsolidadoActualSector(@PathVariable Long sectorId) {
        try {
            var consolidado = consolidadosService.obtenerConsolidadoActualSector(sectorId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Consolidado actual obtenido", consolidado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/general")
    @Operation(summary = "Consolidado general actual")
    public ResponseEntity<ApiResponse<?>> obtenerConsolidadoActualGeneral() {
        try {
            var consolidado = consolidadosService.obtenerConsolidadoActualGeneral();
            return ResponseEntity.ok(new ApiResponse<>(true, "Consolidado actual obtenido", consolidado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/historico/sector/{sectorId}")
    @Operation(summary = "Histórico de consolidados del sector")
    public ResponseEntity<ApiResponse<List<?>>> obtenerHistoricoSector(@PathVariable Long sectorId) {
        try {
            var historico = consolidadosService.obtenerHistoricoSector(sectorId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Histórico obtenido", historico));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/historico/general")
    @Operation(summary = "Histórico de consolidados generales")
    public ResponseEntity<ApiResponse<List<?>>> obtenerHistoricoGeneral() {
        try {
            var historico = consolidadosService.obtenerHistoricoGeneral();
            return ResponseEntity.ok(new ApiResponse<>(true, "Histórico obtenido", historico));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
```

---

## 📝 Resumen de Archivos a Crear

```
1. RolController.java
2. TipoMiembroController.java
3. PeriodoController.java
4. MiembroController.java
5. FinanzasController.java
6. NotificacionController.java
7. ConsolidadosController.java

+ 7 Service classes (RolService, TipoMiembroService, etc.)
+ DTOs adicionales si es necesario
```

---

**Todas las plantillas de código están listas para copiar y pegar.**

Para las clases Service, siga el patrón existente en `UsuarioService`, `GrupoService`, etc.

