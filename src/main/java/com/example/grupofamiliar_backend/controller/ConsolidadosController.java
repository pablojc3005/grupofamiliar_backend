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

    @GetMapping("/sectorial/{sectorId}/fechas")
    @Operation(summary = "Consolidado sectorial (v_consolidado_sectorial)")
    public ResponseEntity<ApiResponse<?>> obtenerConsolidadoSectorial(
            @PathVariable Long sectorId, 
            @RequestParam java.time.LocalDate semanaDesde,
            @RequestParam java.time.LocalDate semanaHasta) {
        try {
            var consolidado = consolidadosService.obtenerConsolidadoSectorial(sectorId, semanaDesde, semanaHasta);
            return ResponseEntity.ok(new ApiResponse<>(true, "Consolidado sectorial obtenido", consolidado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/general/fechas")
    @Operation(summary = "Consolidado general (v_consolidado_general)")
    public ResponseEntity<ApiResponse<?>> obtenerConsolidadoGeneral(
            @RequestParam java.time.LocalDate semanaDesde,
            @RequestParam java.time.LocalDate semanaHasta) {
        try {
            var consolidado = consolidadosService.obtenerConsolidadoGeneral(semanaDesde, semanaHasta);
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
