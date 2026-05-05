package com.example.grupofamiliar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    @Builder.Default
    private String tipo = "Bearer";

    private Long usuarioId;

    private String email;

    private String nombreCompleto;

    private String rol;

    // Datos contextuales según rol (pueden ser null si no aplica)
    private Long grupoFamiliarId;

    private String grupoFamiliarNombre;
    private Long sectorId;

    private String sectorNombre;

    private Long supervisorId;

    private String supervisorNombre;

}
