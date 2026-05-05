package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.dto.CreateUsuarioRequest;
import com.example.grupofamiliar_backend.dto.UsuarioDTO;
import com.example.grupofamiliar_backend.entity.Rol;
import com.example.grupofamiliar_backend.entity.Sector;
import com.example.grupofamiliar_backend.entity.Usuario;
import com.example.grupofamiliar_backend.repository.RolRepository;
import com.example.grupofamiliar_backend.repository.SectorRepository;
import com.example.grupofamiliar_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final RolRepository rolRepository;

    private final SectorRepository sectorRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene todos los usuarios activos
     */
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findByActivo(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por ID
     */
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertToDTO(usuario);
    }

    /**
     * Obtiene un usuario por email
     */
    public UsuarioDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findFirstByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertToDTO(usuario);
    }

    /**
     * Crea un nuevo usuario
     */
    @Transactional
    public UsuarioDTO crear(CreateUsuarioRequest request) {
        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Obtener el rol
        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Crear usuario
        Usuario usuario = Usuario.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .rol(rol)
                .activo(true)
                .build();



        usuario = usuarioRepository.save(usuario);
        log.info("Usuario {} creado exitosamente", usuario.getEmail());

        return convertToDTO(usuario);
    }

    /**
     * Actualiza un usuario existente
     */
    @Transactional
    public UsuarioDTO actualizar(Long id, CreateUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setTelefono(request.getTelefono());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }



        usuario = usuarioRepository.save(usuario);
        log.info("Usuario {} actualizado exitosamente", usuario.getEmail());

        return convertToDTO(usuario);
    }

    /**
     * Desactiva un usuario
     */
    @Transactional
    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        log.info("Usuario {} desactivado", usuario.getEmail());
    }

    /**
     * Activa un usuario
     */
    @Transactional
    public void activar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(true);
        usuarioRepository.save(usuario);
        log.info("Usuario {} activado", usuario.getEmail());
    }

    /**
     * Convierte una entidad Usuario a DTO
     */
    private UsuarioDTO convertToDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .rol(usuario.getRol().getNombre())
                .activo(usuario.getActivo())
                .sectorId(null)
                .sectorNombre(null)
                .supervisorId(null)
                .supervisorNombre(null)
                .build();
    }

}
