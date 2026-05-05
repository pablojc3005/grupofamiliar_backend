package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.ApiResponse;
import com.example.grupofamiliar_backend.dto.CreateUsuarioRequest;
import com.example.grupofamiliar_backend.dto.UsuarioDTO;
import com.example.grupofamiliar_backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "CRUD de usuarios")
@SecurityRequirement(name = "Bearer Authentication")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Obtiene todos los usuarios activos")
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> obtenerTodos() {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios obtenidos", usuarios));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un usuario por ID")
    public ResponseEntity<ApiResponse<UsuarioDTO>> obtenerPorId(@PathVariable Long id) {
        try {
            UsuarioDTO usuario = usuarioService.obtenerPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario obtenido", usuario));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Obtiene un usuario por email")
    public ResponseEntity<ApiResponse<UsuarioDTO>> obtenerPorEmail(@PathVariable String email) {
        try {
            UsuarioDTO usuario = usuarioService.obtenerPorEmail(email);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario obtenido", usuario));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo usuario")
    public ResponseEntity<ApiResponse<UsuarioDTO>> crear(@RequestBody CreateUsuarioRequest request) {
        try {
            UsuarioDTO usuario = usuarioService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Usuario creado exitosamente", usuario));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un usuario existente")
    public ResponseEntity<ApiResponse<UsuarioDTO>> actualizar(@PathVariable Long id, 
                                                               @RequestBody CreateUsuarioRequest request) {
        try {
            UsuarioDTO usuario = usuarioService.actualizar(id, request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado", usuario));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactiva un usuario")
    public ResponseEntity<ApiResponse<Void>> desactivar(@PathVariable Long id) {
        try {
            usuarioService.desactivar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario desactivado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activa un usuario")
    public ResponseEntity<ApiResponse<Void>> activar(@PathVariable Long id) {
        try {
            usuarioService.activar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario activado"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

}
