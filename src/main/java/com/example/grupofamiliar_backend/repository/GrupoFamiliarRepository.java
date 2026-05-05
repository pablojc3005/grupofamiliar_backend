package com.example.grupofamiliar_backend.repository;

import com.example.grupofamiliar_backend.entity.GrupoFamiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoFamiliarRepository extends JpaRepository<GrupoFamiliar, Long> {

    Optional<GrupoFamiliar> findByLiderId(Long liderId);
    List<GrupoFamiliar> findBySectorId(Long sectorId);
    
}
