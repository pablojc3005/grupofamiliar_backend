package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.dto.ReporteSectorialDTO;
import com.example.grupofamiliar_backend.service.ReporteSectorialService;
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
@RequestMapping("/reportes-sectoriales")
@RequiredArgsConstructor
@Tag(name = "Reportes Sectoriales", description = "Endpoints para la gestión de reportes de supervisores sectoriales")
@SecurityRequirement(name = "Bearer Authentication")
public class ReporteSectorialController {

    private final ReporteSectorialService reporteSectorialService;

    @GetMapping
    @Operation(summary = "Obtiene todos los reportes sectoriales")
    public ResponseEntity<ApiResponse<List<ReporteSectorialDTO>>> obtenerTodos() {
        List<ReporteSectorialDTO> reportes = reporteSectorialService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Reportes sectoriales obtenidos", reportes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un reporte sectorial por ID")
    public ResponseEntity<ApiResponse<ReporteSectorialDTO>> obtenerPorId(@PathVariable Long id) {
        try {
            ReporteSectorialDTO reporte = reporteSectorialService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte sectorial obtenido", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/supervisor/{supervisorId}")
    @Operation(summary = "Obtiene reportes sectoriales de un supervisor")
    public ResponseEntity<ApiResponse<List<ReporteSectorialDTO>>> obtenerPorSupervisor(@PathVariable Long supervisorId) {
        List<ReporteSectorialDTO> reportes = reporteSectorialService.obtenerPorSupervisor(supervisorId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reportes del supervisor obtenidos", reportes));
    }

    @GetMapping("/sector/{sectorId}")
    @Operation(summary = "Obtiene reportes sectoriales de un sector")
    public ResponseEntity<ApiResponse<List<ReporteSectorialDTO>>> obtenerPorSector(@PathVariable Long sectorId) {
        List<ReporteSectorialDTO> reportes = reporteSectorialService.obtenerPorSector(sectorId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reportes del sector obtenidos", reportes));
    }

    @GetMapping("/sector/{sectorId}/fechas")
    @Operation(summary = "Obtiene reportes sectoriales de un sector en un rango de fechas")
    public ResponseEntity<ApiResponse<List<ReporteSectorialDTO>>> obtenerPorSectorYRango(
            @PathVariable Long sectorId,
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {
        try {
            List<ReporteSectorialDTO> reportes = reporteSectorialService.obtenerPorSectorYRango(sectorId, desde, hasta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reportes sectoriales obtenidos", reportes));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/todos/fechas")
    @Operation(summary = "Obtiene todos los reportes sectoriales en un rango de fechas")
    public ResponseEntity<ApiResponse<List<ReporteSectorialDTO>>> obtenerTodosPorRango(
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {
        try {
            List<ReporteSectorialDTO> reportes = reporteSectorialService.obtenerTodosPorRango(desde, hasta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reportes sectoriales obtenidos", reportes));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea o actualiza un reporte sectorial (se guarda en BORRADOR)")
    public ResponseEntity<ApiResponse<ReporteSectorialDTO>> crearOActualizar(@RequestBody ReporteSectorialDTO dto) {
        try {
            ReporteSectorialDTO reporte = reporteSectorialService.crearOActualizar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Reporte sectorial guardado en borrador", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/enviar")
    @Operation(summary = "Envía un reporte sectorial (BORRADOR → ENVIADO)")
    public ResponseEntity<ApiResponse<ReporteSectorialDTO>> enviar(@PathVariable Long id) {
        try {
            ReporteSectorialDTO reporte = reporteSectorialService.enviar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte sectorial enviado exitosamente", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/aprobar")
    @Operation(summary = "Aprueba un reporte sectorial (ENVIADO → APROBADO)")
    public ResponseEntity<ApiResponse<ReporteSectorialDTO>> aprobar(@PathVariable Long id) {
        try {
            ReporteSectorialDTO reporte = reporteSectorialService.aprobar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte sectorial aprobado", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/rechazar")
    @Operation(summary = "Rechaza un reporte sectorial (ENVIADO → BORRADOR)")
    public ResponseEntity<ApiResponse<ReporteSectorialDTO>> rechazar(@PathVariable Long id) {
        try {
            ReporteSectorialDTO reporte = reporteSectorialService.rechazar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte sectorial rechazado y devuelto a borrador", reporte));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un reporte sectorial en estado BORRADOR")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            reporteSectorialService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Reporte sectorial eliminado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
