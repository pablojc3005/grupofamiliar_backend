package com.example.grupofamiliar_backend.dto;

import com.example.grupofamiliar_backend.entity.GrupoFamiliar;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GrupoFamiliarDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private Long liderId;
    private String liderNombre;
    private Long sectorId;
    private String sectorNombre;
    private Boolean activo;
    private LocalDateTime creadoEn;

    public static GrupoFamiliarDTO fromEntity(GrupoFamiliar grupo) {
        return GrupoFamiliarDTO.builder()
                .id(grupo.getId())
                .nombre(grupo.getNombre())
                .direccion(grupo.getDireccion())
                .liderId(grupo.getLider() != null ? grupo.getLider().getId() : null)
                .liderNombre(grupo.getLider() != null ? grupo.getLider().getNombreCompleto() : null)
                .sectorId(grupo.getSector() != null ? grupo.getSector().getId() : null)
                .sectorNombre(grupo.getSector() != null ? grupo.getSector().getNombre() : null)
                .activo(grupo.getActivo())
                .creadoEn(grupo.getCreadoEn())
                .build();
    }
}
