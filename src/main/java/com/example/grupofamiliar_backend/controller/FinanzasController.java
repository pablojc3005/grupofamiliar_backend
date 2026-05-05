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

    @GetMapping("/movimientos/fechas")
    @Operation(summary = "Obtiene movimientos por rango de fechas")
    public ResponseEntity<ApiResponse<List<MovimientoFinanciero>>> obtenerMovimientosPorFecha(
            @RequestParam java.time.LocalDate start, 
            @RequestParam java.time.LocalDate end) {
        try {
            List<MovimientoFinanciero> movimientos = finanzasService.obtenerMovimientosPorFecha(start, end);
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


    @GetMapping("/movimientos/categoria/{categoriaId}")
    @Operation(summary = "Obtiene movimientos por categoría")
    public ResponseEntity<ApiResponse<List<MovimientoFinanciero>>> obtenerMovimientosPorCategoria(@PathVariable Long categoriaId) {
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

    @GetMapping("/resumen/fechas")
    @Operation(summary = "Obtiene resumen financiero por rango de fechas")
    public ResponseEntity<ApiResponse<?>> obtenerResumenPeriodo(
            @RequestParam java.time.LocalDate start, 
            @RequestParam java.time.LocalDate end) {
        try {
            var resumen = finanzasService.obtenerResumenFinanciero(start, end);
            return ResponseEntity.ok(new ApiResponse<>(true, "Resumen obtenido", resumen));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/balance/fechas")
    @Operation(summary = "Obtiene balance INGRESOS - EGRESOS por rango de fechas")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerBalance(
            @RequestParam java.time.LocalDate start, 
            @RequestParam java.time.LocalDate end) {
        try {
            var balance = finanzasService.obtenerBalance(start, end);
            return ResponseEntity.ok(new ApiResponse<>(true, "Balance obtenido", balance));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/ofrenda-total/fechas")
    @Operation(summary = "Obtiene total de ofrendas registradas por rango de fechas")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerOfrendaTotal(
            @RequestParam java.time.LocalDate start, 
            @RequestParam java.time.LocalDate end) {
        try {
            var ofrenda = finanzasService.obtenerOfrendaTotal(start, end);
            return ResponseEntity.ok(new ApiResponse<>(true, "Total de ofrendas obtenido", ofrenda));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/ofrendas/grupo")
    @Operation(summary = "Obtiene ofrendas agrupadas por grupo familiar desde los reportes (TESORERO/ADMIN)")
    public ResponseEntity<ApiResponse<?>> obtenerOfrendasPorGrupo(
            @RequestParam java.time.LocalDate desde,
            @RequestParam java.time.LocalDate hasta,
            @RequestParam(required = false) Long sectorId) {
        try {
            var data = finanzasService.obtenerOfrendasPorGrupo(desde, hasta, sectorId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Ofrendas por grupo obtenidas", data));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/ofrendas/sector")
    @Operation(summary = "Obtiene ofrendas agrupadas por sector desde los reportes (TESORERO/ADMIN)")
    public ResponseEntity<ApiResponse<?>> obtenerOfrendasPorSector(
            @RequestParam java.time.LocalDate desde,
            @RequestParam java.time.LocalDate hasta) {
        try {
            var data = finanzasService.obtenerOfrendasPorSector(desde, hasta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Ofrendas por sector obtenidas", data));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
