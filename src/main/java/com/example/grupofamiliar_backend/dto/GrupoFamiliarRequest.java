package com.example.grupofamiliar_backend.dto;

import lombok.Data;

@Data
public class GrupoFamiliarRequest {
    private String nombre;
    private String direccion;
    private Long idLider;
    private Long idSector;
    private Boolean activo;
}
