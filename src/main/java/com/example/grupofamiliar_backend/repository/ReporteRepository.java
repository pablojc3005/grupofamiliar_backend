package com.example.grupofamiliar_backend.repository;

import com.example.grupofamiliar_backend.entity.Reporte;
import com.example.grupofamiliar_backend.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByGrupoFamiliar_Sector(Sector sector);

    List<Reporte> findByGrupoFamiliar(com.example.grupofamiliar_backend.entity.GrupoFamiliar grupoFamiliar);

    List<Reporte> findBySemanaDesdeAndSemanaHasta(LocalDate semanaDesde, LocalDate semanaHasta);

    Optional<Reporte> findByGrupoFamiliarAndSemanaDesdeAndSemanaHasta(com.example.grupofamiliar_backend.entity.GrupoFamiliar grupoFamiliar, LocalDate semanaDesde, LocalDate semanaHasta);

    List<Reporte> findByEstado(String estado);

    List<Reporte> findByGrupoFamiliar_Sector_Id(Long sectorId);

    @Query("SELECT r FROM Reporte r WHERE r.grupoFamiliar.sector.id IN :sectorIds AND r.semanaDesde = :semanaDesde AND r.semanaHasta = :semanaHasta")
    List<Reporte> findBySectorIdInAndSemanaDesdeAndSemanaHasta(@Param("sectorIds") List<Long> sectorIds,
                                              @Param("semanaDesde") LocalDate semanaDesde,
                                              @Param("semanaHasta") LocalDate semanaHasta);

    // Filtrado por rango de fechas (todos los sectores)
    @Query("SELECT r FROM Reporte r WHERE r.semanaDesde >= :desde AND r.semanaHasta <= :hasta")
    List<Reporte> findByRangoDeFechas(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    // Filtrado por rango de fechas y sector especifico
    @Query("SELECT r FROM Reporte r WHERE r.grupoFamiliar.sector.id = :sectorId AND r.semanaDesde >= :desde AND r.semanaHasta <= :hasta")
    List<Reporte> findBySectorIdAndRangoDeFechas(@Param("sectorId") Long sectorId,
                                                  @Param("desde") LocalDate desde,
                                                  @Param("hasta") LocalDate hasta);

    // IDs de grupos que SI tienen reporte en el rango dado para un sector
    @Query("SELECT r.grupoFamiliar.id FROM Reporte r WHERE r.grupoFamiliar.sector.id = :sectorId AND r.semanaDesde >= :desde AND r.semanaHasta <= :hasta")
    List<Long> findGruposConReporteBySectorIdAndRango(@Param("sectorId") Long sectorId,
                                                       @Param("desde") LocalDate desde,
                                                       @Param("hasta") LocalDate hasta);

}
