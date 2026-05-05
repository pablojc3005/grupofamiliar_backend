package com.example.grupofamiliar_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categoria_financiera")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaFinanciera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    @Column(nullable = false)
    private Boolean activo = true;

    public enum TipoMovimiento {
        INGRESO, EGRESO
    }

}
