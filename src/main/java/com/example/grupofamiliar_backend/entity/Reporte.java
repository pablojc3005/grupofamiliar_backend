package com.example.grupofamiliar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reporte", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_grupo_familiar", "semana_desde", "semana_hasta"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_grupo_familiar", nullable = false)
    private GrupoFamiliar grupoFamiliar;

    @Column(name = "semana_desde", nullable = false)
    private LocalDate semanaDesde;

    @Column(name = "semana_hasta", nullable = false)
    private LocalDate semanaHasta;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoReporte estado = EstadoReporte.BORRADOR;

    @Column(nullable = false)
    private Integer tiempoOracion = 0;

    @Column(nullable = false)
    private Boolean ayuno = false;

    // --- Asistencia ---
    @Column(nullable = false)
    private Short cantHermanos = 0;

    @Column(nullable = false)
    private Short cantAmigos = 0;

    @Column(nullable = false)
    private Short cantAdolescentes = 0;

    // --- Conversiones y niños ---
    @Column(nullable = false)
    private Short cantConvertidos = 0;

    @Column(nullable = false)
    private Short cantNinosCristianos = 0;

    @Column(nullable = false)
    private Short cantNinosAmigos = 0;

    // --- Visitas ---
    @Column(nullable = false)
    private Short cantVisitaConsolidacion = 0;

    @Column(nullable = false)
    private Short cantVisitaCasaDePaz = 0;

    @Column(nullable = false)
    private Short cantVisitaHogar = 0;

    // --- Actividades espirituales ---
    @Column(nullable = false)
    private Short cantHrOracion = 0;

    @Column(nullable = false)
    private Short cantHrMep = 0;

    @Column(nullable = false)
    private Short cantHrDiscipulado = 0;

    @Column(nullable = false)
    private Short cantRetiroEspiritual = 0;

    // --- Ofrendas ---
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ofrendaSabado = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ofrendaNinos = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ofrendaMiercoles = BigDecimal.ZERO;

    // --- Observaciones ---
    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column
    private LocalDateTime enviadoEn;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(nullable = false)
    private LocalDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = LocalDateTime.now();
    }

    public BigDecimal getTotalOfrendas() {
        return ofrendaSabado.add(ofrendaNinos).add(ofrendaMiercoles);
    }

    public Integer getTotalAsistencia() {
        return (cantHermanos != null ? cantHermanos : 0) +
               (cantAmigos != null ? cantAmigos : 0) +
               (cantAdolescentes != null ? cantAdolescentes : 0);
    }

    public enum EstadoReporte {
        BORRADOR, ENVIADO, APROBADO
    }

}
