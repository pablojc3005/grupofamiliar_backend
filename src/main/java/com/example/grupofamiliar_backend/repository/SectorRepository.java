package com.example.grupofamiliar_backend.repository;

import com.example.grupofamiliar_backend.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    List<Sector> findByActivo(Boolean activo);

    Optional<Sector> findByCodigo(String codigo);

    Optional<Sector> findBySupervisorId(Long supervisorId);

    // NUEVO METODO: Retorna List (puede tener múltiples resultados)
    List<Sector> findAllBySupervisorId(Long supervisorId);

    // OPCIONAL: Metodo para obtener el primero si hay varios (usando LIMIT)
    @Query(value = "SELECT * FROM sector WHERE id_supervisor = :supervisorId LIMIT 1", nativeQuery = true)
    Optional<Sector> findFirstBySupervisorId(@Param("supervisorId") Long supervisorId);

}
