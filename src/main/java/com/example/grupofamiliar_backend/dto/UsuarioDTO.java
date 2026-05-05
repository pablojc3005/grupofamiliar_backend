package com.example.grupofamiliar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;

    private String nombres;

    private String apellidos;

    private String email;

    private String telefono;

    private String rol;

    private Boolean activo;

    private Long sectorId;
    
    private String sectorNombre;

    private Long supervisorId;
    
    private String supervisorNombre;

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

}
