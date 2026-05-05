package com.example.grupofamiliar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUsuarioRequest {

    private String nombres;

    private String apellidos;

    private String email;

    private String telefono;

    private String password;

    private Long idRol;

    private Long idSupervisor;

    private Long idSector;

}
