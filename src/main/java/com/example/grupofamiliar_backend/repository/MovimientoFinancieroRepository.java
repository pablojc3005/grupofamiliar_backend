package com.example.grupofamiliar_backend.repository;

import com.example.grupofamiliar_backend.entity.CategoriaFinanciera;
import com.example.grupofamiliar_backend.entity.MovimientoFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoFinancieroRepository extends JpaRepository<MovimientoFinanciero, Long> {

    List<MovimientoFinanciero> findByFechaBetween(LocalDate start, LocalDate end);

    List<MovimientoFinanciero> findBySectorId(Long sectorId);

    List<MovimientoFinanciero> findByCategoria(CategoriaFinanciera categoria);

    List<MovimientoFinanciero> findByCategoriaId(Long categoriaId);

}
