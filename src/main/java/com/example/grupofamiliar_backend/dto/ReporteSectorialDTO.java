package com.example.grupofamiliar_backend.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteSectorialDTO {

    private Long id;

    private Long supervisorId;

    private String supervisorNombre;

    private Long sectorId;

    private String sectorNombre;

    private LocalDate semanaDesde;

    private LocalDate semanaHasta;

    private String estado;

    // --- Vida Devocional ---
    private Short horasOracion;

    private Short minutosOracion;

    private Boolean lecturaBiblia;

    private Boolean ayuno;

    private Boolean cultoLiderazgo;

    private Boolean diezmo;

    // --- Campos Dinámicos serializados como JSON ---
    private String atencionesJson;

    private String supervisionesJson;

    private String evaluacionesEquipoJson;

    // --- Reunión de Planificación ---
    private String planificacionGrupo;

    private LocalDate planificacionFecha;

    private String planificacionHora;

    private String planificacionPositivos;

    private String planificacionNegativos;

    // --- Firma manuscrita en formato Base64 PNG ---
    private String firma;

    private LocalDateTime enviadoEn;

    private LocalDateTime creadoEn;

    private LocalDateTime actualizadoEn;
}
