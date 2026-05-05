package com.example.grupofamiliar_backend.repository;

import com.example.grupofamiliar_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuario WHERE email = :email ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Optional<Usuario> findFirstByEmail(@Param("email") String email);

    List<Usuario> findByActivo(Boolean activo);

    boolean existsByEmail(String email);

}
