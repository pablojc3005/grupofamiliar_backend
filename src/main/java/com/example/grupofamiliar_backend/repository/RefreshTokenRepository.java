package com.example.grupofamiliar_backend.repository;

import com.example.grupofamiliar_backend.entity.RefreshToken;
import com.example.grupofamiliar_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUsuario(Usuario usuario);

    List<RefreshToken> findByRevocado(Boolean revocado);

    @Query("DELETE FROM RefreshToken rt WHERE rt.expiraEn < ?1 OR rt.revocado = true")
    void deleteExpiredAndRevoked(LocalDateTime now);

    /*
     * Optional<RefreshToken> findByToken(String token);
     * List<RefreshToken> findByUsuario(Usuario usuario); // ← Agrega este método
     * void deleteByUsuario(Usuario usuario); // ← Agrega este método opcional
     */

}
