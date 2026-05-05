package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.dto.ReporteDTO;
import com.example.grupofamiliar_backend.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "CRUD de reportes semanales")
@SecurityRequirement(name = "Bearer Authentication")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    @Operation(summary = "Obtiene todos los reportes")
    public ResponseEntity<ApiResponse<List<ReporteDTO>>> obtenerTodos() {
        List<ReporteDTO> reportes = reporteService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Reportes obtenidos", reportes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un reporte por ID")
    public ResponseEntity<ApiResponse<ReporteDTO>> obtenerPorId(@PathVariable Long id) {
        try {
            ReporteDTO reporte = reporteService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte obtenido", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/sector/{sectorId}")
    @Operation(summary = "Obtiene reportes de un sector específico")
    public ResponseEntity<ApiResponse<List<ReporteDTO>>> obtenerPorSector(@PathVariable Long sectorId) {
        try {
            List<ReporteDTO> reportes = reporteService.obtenerPorSector(sectorId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reportes obtenidos", reportes));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/grupo/{grupoId}")
    @Operation(summary = "Obtiene reportes de un grupo familiar específico")
    public ResponseEntity<ApiResponse<List<ReporteDTO>>> obtenerPorGrupoFamiliar(@PathVariable Long grupoId) {
        try {
            List<ReporteDTO> reportes = reporteService.obtenerPorGrupoFamiliar(grupoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reportes obtenidos", reportes));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/semana")
    @Operation(summary = "Obtiene reportes de una semana específica")
    public ResponseEntity<ApiResponse<List<ReporteDTO>>> obtenerPorSemana(
            @RequestParam java.time.LocalDate semanaDesde, 
            @RequestParam java.time.LocalDate semanaHasta) {
        try {
            List<ReporteDTO> reportes = reporteService.obtenerPorSemana(semanaDesde, semanaHasta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reportes obtenidos", reportes));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/todos/fechas")
    @Operation(summary = "Obtiene todos los reportes en un rango de fechas (ADMIN)")
    public ResponseEntity<ApiResponse<List<ReporteDTO>>> obtenerTodosPorRango(
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {
        try {
            List<ReporteDTO> reportes = reporteService.obtenerTodosPorRango(desde, hasta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reportes obtenidos", reportes));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/sector/{sectorId}/fechas")
    @Operation(summary = "Obtiene reportes de un sector en un rango de fechas")
    public ResponseEntity<ApiResponse<List<ReporteDTO>>> obtenerPorSectorYRango(
            @PathVariable Long sectorId,
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {
        try {
            List<ReporteDTO> reportes = reporteService.obtenerPorSectorYRango(sectorId, desde, hasta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reportes obtenidos", reportes));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/alertas/sector/{sectorId}")
    @Operation(summary = "Obtiene grupos familiares sin reporte en el rango dado (para SUP_SECTORIAL y ADMIN)")
    public ResponseEntity<ApiResponse<?>> obtenerLideresSinReporte(
            @PathVariable Long sectorId,
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {
        try {
            var alertas = reporteService.obtenerLideresSinReporte(sectorId, desde, hasta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Alertas obtenidas", alertas));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea o actualiza un reporte")
    public ResponseEntity<ApiResponse<ReporteDTO>> crearOActualizar(@RequestBody ReporteDTO reporteDTO) {
        try {
            ReporteDTO reporte = reporteService.crearOActualizar(reporteDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Reporte guardado en borrador", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/enviar")
    @Operation(summary = "Envía un reporte (BORRADOR → ENVIADO)")
    public ResponseEntity<ApiResponse<ReporteDTO>> enviar(@PathVariable Long id) {
        try {
            ReporteDTO reporte = reporteService.enviar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte enviado", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/aprobar")
    @Operation(summary = "Aprueba un reporte (ENVIADO → APROBADO)")
    public ResponseEntity<ApiResponse<ReporteDTO>> aprobar(@PathVariable Long id) {
        try {
            ReporteDTO reporte = reporteService.aprobar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte aprobado", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/rechazar")
    @Operation(summary = "Rechaza un reporte (ENVIADO → BORRADOR)")
    public ResponseEntity<ApiResponse<ReporteDTO>> rechazar(@PathVariable Long id) {
        try {
            ReporteDTO reporte = reporteService.rechazar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte rechazado", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un reporte en estado BORRADOR")
    public ResponseEntity<ApiResponse<ReporteDTO>> actualizar(@PathVariable Long id,
                                                              @RequestBody ReporteDTO reporteDTO) {
        try {
            ReporteDTO reporte = reporteService.actualizar(id, reporteDTO);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte actualizado", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un reporte en estado BORRADOR")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            reporteService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte eliminado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

}
