package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.dto.SectorDTO;
import com.example.grupofamiliar_backend.service.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sectores")
@RequiredArgsConstructor
@Tag(name = "Sectores", description = "CRUD de sectores")
@SecurityRequirement(name = "Bearer Authentication")
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    @Operation(summary = "Obtiene todos los sectores activos")
    public ResponseEntity<ApiResponse<List<SectorDTO>>> obtenerTodos() {
        List<SectorDTO> sectores = sectorService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Sectores obtenidos", sectores));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un sector por ID")
    public ResponseEntity<ApiResponse<SectorDTO>> obtenerPorId(@PathVariable Long id) {
        try {
            SectorDTO sector = sectorService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Sector obtenido", sector));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo sector")
    public ResponseEntity<ApiResponse<SectorDTO>> crear(@RequestBody SectorDTO sectorDTO) {
        try {
            SectorDTO sector = sectorService.crear(sectorDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Sector creado exitosamente", sector));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un sector existente")
    public ResponseEntity<ApiResponse<SectorDTO>> actualizar(@PathVariable Long id, 
                                                              @RequestBody SectorDTO sectorDTO) {
        try {
            SectorDTO sector = sectorService.actualizar(id, sectorDTO);
            return ResponseEntity.ok(new ApiResponse<>(true, "Sector actualizado", sector));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactiva un sector")
    public ResponseEntity<ApiResponse<Void>> desactivar(@PathVariable Long id) {
        try {
            sectorService.desactivar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Sector desactivado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

}
