package com.example.grupofamiliar_backend.repository;

import com.example.grupofamiliar_backend.entity.CategoriaFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaFinancieraRepository extends JpaRepository<CategoriaFinanciera, Byte> {

    Optional<CategoriaFinanciera> findByNombre(String nombre);

    List<CategoriaFinanciera> findByActivo(Boolean activo);

    List<CategoriaFinanciera> findByTipo(String tipo);

}
