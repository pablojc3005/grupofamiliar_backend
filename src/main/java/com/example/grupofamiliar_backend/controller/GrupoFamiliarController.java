package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.dto.GrupoFamiliarDTO;
import com.example.grupofamiliar_backend.dto.GrupoFamiliarRequest;
import com.example.grupofamiliar_backend.entity.GrupoFamiliar;
import com.example.grupofamiliar_backend.service.GrupoFamiliarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grupos-familiares")
@RequiredArgsConstructor
@Tag(name = "Grupos Familiares", description = "Gestión de grupos familiares")
@SecurityRequirement(name = "Bearer Authentication")
public class GrupoFamiliarController {

    private final GrupoFamiliarService grupoFamiliarService;

    @GetMapping
    @Operation(summary = "Obtiene todos los grupos familiares")
    public ResponseEntity<ApiResponse<List<GrupoFamiliarDTO>>> obtenerTodos() {
        List<GrupoFamiliarDTO> grupos = grupoFamiliarService.obtenerTodos().stream()
                .map(GrupoFamiliarDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse<>(true, "Grupos familiares obtenidos", grupos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un grupo familiar por ID")
    public ResponseEntity<ApiResponse<GrupoFamiliarDTO>> obtenerPorId(@PathVariable Long id) {
        try {
            GrupoFamiliar grupo = grupoFamiliarService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Grupo familiar obtenido", GrupoFamiliarDTO.fromEntity(grupo)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo grupo familiar")
    public ResponseEntity<ApiResponse<GrupoFamiliarDTO>> crear(@RequestBody GrupoFamiliarRequest request) {
        try {
            GrupoFamiliar nuevoGrupo = grupoFamiliarService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Grupo familiar creado exitosamente", GrupoFamiliarDTO.fromEntity(nuevoGrupo)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un grupo familiar existente")
    public ResponseEntity<ApiResponse<GrupoFamiliarDTO>> actualizar(@PathVariable Long id,
                                                                    @RequestBody GrupoFamiliarRequest request) {
        try {
            GrupoFamiliar grupoActualizado = grupoFamiliarService.actualizar(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Grupo familiar actualizado", GrupoFamiliarDTO.fromEntity(grupoActualizado)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un grupo familiar (Físico)")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            grupoFamiliarService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Grupo familiar eliminado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Cambia el estado activo/inactivo de un grupo familiar")
    public ResponseEntity<ApiResponse<Void>> cambiarEstado(@PathVariable Long id) {
        try {
            grupoFamiliarService.cambiarEstado(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Estado de grupo familiar modificado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
