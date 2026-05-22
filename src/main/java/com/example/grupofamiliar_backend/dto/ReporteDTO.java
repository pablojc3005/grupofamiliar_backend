package com.example.grupofamiliar_backend.dto;

import lombok.*;

import java.math.BigDecimal;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteDTO {

    private Long id;

    private Long sectorId;

    private String sectorNombre;

    private Long grupoFamiliarId;

    private String grupoFamiliarNombre;

    private LocalDate semanaDesde;

    private LocalDate semanaHasta;

    private Long liderId;

    private String liderNombre;

    private String estado;

    // --- Información general del líder ---
    private Boolean diezmo;

    private Boolean lecturaBiblia;

    private Boolean visito;

    private Short horasOracion;

    private Short minutosOracion;

    private Boolean ayuno;

    // --- Asistencia ---
    private Short cantHermanos;

    private Short cantAmigos;

    private Short cantAdolescentes;

    private Short cantConvertidos;

    private Short cantNinosCristianos;

    private Short cantNinosAmigos;

    // --- Visitas ---
    private Short cantVisitaConsolidacion;

    private Short cantVisitaCasaDePaz;

    private Short cantVisitaHogar;

    // --- Actividades espirituales ---
    private Short cultoHoracion;

    private Short cantHrMep;

    private Short cantHrDiscipulado;

    private Short cantRetiroEspiritual;

    private Short cantCultoCentral;

    // --- Ofrendas ---
    private BigDecimal ofrendaSabado;

    private BigDecimal ofrendaNinos;

    private BigDecimal ofrendaMiercoles;

    private String observaciones;

}
