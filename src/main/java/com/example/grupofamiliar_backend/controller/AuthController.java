package com.example.grupofamiliar_backend.controller;

import com.example.grupofamiliar_backend.dto.*;
import com.example.grupofamiliar_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para autenticación y gestión de JWT")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Inicia sesión con email y contraseña")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Inicio de sesión exitoso", response));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresca el Access Token usando el Refresh Token")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(@RequestBody RefreshTokenRequest request) {
        try {
            LoginResponse response = authService.refresh(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Token refrescado exitosamente", response));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Cierra sesión revocando el Refresh Token")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody RefreshTokenRequest request) {
        try {
            authService.logout(request.getRefreshToken());
            return ResponseEntity.ok(new ApiResponse<>(true, "Sesión cerrada exitosamente"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registra un nuevo usuario y envía contraseña al correo")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest request) {
        try {
            authService.registerUser(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Registro exitoso. Revisa tu correo electrónico para obtener la contraseña."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Genera una nueva contraseña y la envía por correo")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            authService.resetPassword(request.getEmail());
            return ResponseEntity.ok(new ApiResponse<>(true, "Se ha enviado una nueva contraseña a tu correo."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, ex.getMessage()));
        }
    }
}
