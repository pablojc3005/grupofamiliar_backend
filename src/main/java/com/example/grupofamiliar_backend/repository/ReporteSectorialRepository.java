package com.example.grupofamiliar_backend.repository;

import com.example.grupofamiliar_backend.entity.ReporteSectorial;
import com.example.grupofamiliar_backend.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteSectorialRepository extends JpaRepository<ReporteSectorial, Long> {

    List<ReporteSectorial> findBySupervisorId(Long supervisorId);

    List<ReporteSectorial> findBySectorId(Long sectorId);

    Optional<ReporteSectorial> findBySectorAndSemanaDesdeAndSemanaHasta(Sector sector, LocalDate semanaDesde, LocalDate semanaHasta);

    @Query("SELECT r FROM ReporteSectorial r WHERE r.sector.id = :sectorId AND r.semanaDesde >= :desde AND r.semanaDesde <= :hasta")
    List<ReporteSectorial> findBySectorIdAndRangoDeFechas(@Param("sectorId") Long sectorId, @Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    @Query("SELECT r FROM ReporteSectorial r WHERE r.semanaDesde >= :desde AND r.semanaDesde <= :hasta")
    List<ReporteSectorial> findByRangoDeFechas(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
}
