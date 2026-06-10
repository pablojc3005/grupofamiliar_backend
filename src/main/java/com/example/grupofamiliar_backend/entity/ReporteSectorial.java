package com.example.grupofamiliar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reporte_sectorial", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_sector", "semana_desde", "semana_hasta"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteSectorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_supervisor", nullable = false)
    private Usuario supervisor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sector", nullable = false)
    private Sector sector;

    @Column(name = "semana_desde", nullable = false)
    private LocalDate semanaDesde;

    @Column(name = "semana_hasta", nullable = false)
    private LocalDate semanaHasta;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoReporte estado = EstadoReporte.BORRADOR;

    // --- Vida Devocional ---
    @Column(name = "horas_oracion", nullable = false)
    private Short horasOracion = 0;

    @Column(name = "minutos_oracion", nullable = false)
    private Short minutosOracion = 0;

    @Column(name = "lectura_biblia", nullable = false)
    private Boolean lecturaBiblia = false;

    @Column(nullable = false)
    private Boolean ayuno = false;

    @Column(name = "culto_liderazgo", nullable = false)
    private Boolean cultoLiderazgo = false;

    @Column(nullable = false)
    private Boolean diezmo = false;

    // --- Campos Dinámicos serializados como JSON ---
    @Column(name = "atenciones_json", columnDefinition = "LONGTEXT")
    private String atencionesJson;

    @Column(name = "supervisiones_json", columnDefinition = "LONGTEXT")
    private String supervisionesJson;

    @Column(name = "evaluaciones_equipo_json", columnDefinition = "LONGTEXT")
    private String evaluacionesEquipoJson;

    // --- Reunión de Planificación ---
    @Column(name = "planificacion_grupo", length = 150)
    private String planificacionGrupo;

    @Column(name = "planificacion_fecha")
    private LocalDate planificacionFecha;

    @Column(name = "planificacion_hora", length = 20)
    private String planificacionHora;

    @Column(name = "planificacion_positivos", columnDefinition = "TEXT")
    private String planificacionPositivos;

    @Column(name = "planificacion_negativos", columnDefinition = "TEXT")
    private String planificacionNegativos;

    // --- Firma manuscrita en formato Base64 PNG ---
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String firma;

    // --- Auditoría ---
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

    public enum EstadoReporte {
        BORRADOR, ENVIADO, APROBADO
    }
}
