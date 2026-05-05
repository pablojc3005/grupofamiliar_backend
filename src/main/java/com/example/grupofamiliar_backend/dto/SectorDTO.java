package com.example.grupofamiliar_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectorDTO {

    private Long id;

    private String nombre;

    private String codigo;

    private Long supervisorId;

    private String supervisorNombre;

    private Boolean activo;

}
