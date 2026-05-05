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
    public ResponseEntity<ApiResponse<Rol>> obtenerPorId(@PathVariable Long id) {
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
    public ResponseEntity<ApiResponse<Rol>> actualizar(@PathVariable Long id, 
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
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            rolService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Rol eliminado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
