package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.dto.LoginRequest;
import com.example.grupofamiliar_backend.dto.LoginResponse;
import com.example.grupofamiliar_backend.dto.RefreshTokenRequest;
import com.example.grupofamiliar_backend.dto.RegisterRequest;
import com.example.grupofamiliar_backend.entity.RefreshToken;
import com.example.grupofamiliar_backend.entity.Rol;
import com.example.grupofamiliar_backend.entity.Sector;
import com.example.grupofamiliar_backend.entity.Usuario;
import com.example.grupofamiliar_backend.repository.RefreshTokenRepository;
import com.example.grupofamiliar_backend.repository.GrupoFamiliarRepository;
import com.example.grupofamiliar_backend.repository.RolRepository;
import com.example.grupofamiliar_backend.repository.SectorRepository;
import com.example.grupofamiliar_backend.repository.UsuarioRepository;
import com.example.grupofamiliar_backend.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UsuarioRepository usuarioRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final SectorRepository sectorRepository;

    private final GrupoFamiliarRepository grupoFamiliarRepository;

    private final RolRepository rolRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    /**
     * Autentica un usuario y retorna tokens JWT (Access y Refresh)
     */

    public LoginResponse login(LoginRequest loginRequest) {
     try {
         // Autenticar usuario (lanza excepción si las credenciales son inválidas)
         authenticationManager.authenticate(
         new UsernamePasswordAuthenticationToken(
         loginRequest.getEmail(),
         loginRequest.getPassword()));

          // Obtener usuario autenticado
          Usuario usuario = usuarioRepository.findFirstByEmail(loginRequest.getEmail())
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

          if (!usuario.getActivo()) {
          throw new RuntimeException("La cuenta de usuario está deshabilitada");
          }

          // Generar tokens
          String accessToken = jwtUtils.generateAccessToken(usuario);
          String refreshToken = jwtUtils.generateRefreshToken(usuario);

          // Guardar refresh token en la base de datos
          saveRefreshToken(usuario, refreshToken);

          // Buscar datos contextuales según rol
          Long sectorId = null;
          String sectorNombre = null;

          Long grupoFamiliarId = null;
          String grupoFamiliarNombre = null;

          String rolNombre = usuario.getRol().getNombre();
          Long superId = null;
          String superNombre = null;

          if ("LIDER".equals(rolNombre))
          {
              var grupoOpt = grupoFamiliarRepository.findByLiderId(usuario.getId());
              if (grupoOpt.isPresent())
              {
                  var grupo = grupoOpt.get();
                  grupoFamiliarId = grupo.getId();
                  grupoFamiliarNombre = grupo.getNombre();
                  sectorId = grupo.getSector().getId();
                  sectorNombre = grupo.getSector().getNombre();
                  if (grupo.getSector().getSupervisor() != null) {
                      superId = grupo.getSector().getSupervisor().getId();
                      superNombre = grupo.getSector().getSupervisor().getNombreCompleto();
                  }
              }
          } else if ("SUP_SECTORIAL".equals(rolNombre))
          {
              // Usar el metodo que retorna List (puede tener múltiples)
              List<Sector> sectores = sectorRepository.findAllBySupervisorId(usuario.getId());

              if (!sectores.isEmpty()) {
                  // Tomar el primer sector
                  Sector sector = sectores.get(0);
                  sectorId = sector.getId();
                  sectorNombre = sector.getNombre();

                  // Log para información
                  if (sectores.size() > 1) {
                      log.warn("El supervisor {} tiene {} sectores asignados. Usando el primero: {} (ID: {})",
                              usuario.getId(), sectores.size(), sector.getNombre(), sector.getId());
                  }
              } else {
                  log.warn("El supervisor ID {} no tiene sectores asignados", usuario.getId());
              }
          }

          log.info("Usuario {} autenticado exitosamente", usuario.getEmail());

          return LoginResponse.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .tipo("Bearer")
          .usuarioId(usuario.getId())
          .email(usuario.getEmail())
          .nombreCompleto(usuario.getNombreCompleto())
          .rol(rolNombre)
          .sectorId(sectorId)
          .sectorNombre(sectorNombre)
          .grupoFamiliarId(grupoFamiliarId)
          .grupoFamiliarNombre(grupoFamiliarNombre)
          .supervisorId(superId)
          .supervisorNombre(superNombre)
          .build();

          } catch (Exception ex) {
          log.error("Error en autenticación: {}", ex.getMessage());
          throw new RuntimeException("Credenciales inválidas");
          }
     }

/*
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            log.info("=== INICIO LOGIN MANUAL ===");
            log.info("Email: {}", loginRequest.getEmail());
            log.info("Password recibida: {}", loginRequest.getPassword());

            // 1. Buscar usuario directamente (sin usar AuthenticationManager)
            Usuario usuario = usuarioRepository.findFirstByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> {
                        log.error("Usuario no encontrado: {}", loginRequest.getEmail());
                        return new RuntimeException("Credenciales inválidas");
                    });

            log.info("Usuario encontrado: ID={}, Email={}", usuario.getId(), usuario.getEmail());
            log.info("Password hash en BD: {}", usuario.getPasswordHash());

            // 2. Verificar si está activo
            if (!usuario.getActivo()) {
                log.warn("Usuario inactivo: {}", usuario.getEmail());
                throw new RuntimeException("La cuenta de usuario está deshabilitada");
            }

            // 3. Verificar contraseña manualmente
            boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), usuario.getPasswordHash());
            log.info("¿La contraseña coincide?: {}", passwordMatches);

            if (!passwordMatches) {
                log.warn("Contraseña incorrecta para usuario: {}", usuario.getEmail());
                throw new RuntimeException("Credenciales inválidas");
            }

            log.info("Usuario autenticado exitosamente: {}", usuario.getEmail());

            // 4. Generar tokens
            String accessToken = jwtUtils.generateAccessToken(usuario);
            String refreshToken = jwtUtils.generateRefreshToken(usuario);

            log.info("Tokens generados - Access: {}, Refresh: {}",
                    accessToken.substring(0, Math.min(20, accessToken.length())) + "...",
                    refreshToken.substring(0, Math.min(20, refreshToken.length())) + "...");

            // 5. Guardar refresh token (limpiar anteriores)
            // Limpiar refresh tokens viejos
            List<RefreshToken> oldTokens = refreshTokenRepository.findByUsuario(usuario);
            oldTokens.forEach(token -> {
                log.info("Eliminando refresh token antiguo: {}", token.getId());
                refreshTokenRepository.delete(token);
            });

            saveRefreshToken(usuario, refreshToken);

            // 6. Buscar datos contextuales según rol
            Long sectorId = null;
            String sectorNombre = null;
            String rolNombre = usuario.getRol().getNombre();

            if ("LIDER".equals(rolNombre)) {
                if (usuario.getSector() != null) {
                    sectorId = usuario.getSector().getId();
                    sectorNombre = usuario.getSector().getNombre();
                }
            } else if ("SUP_SECTORIAL".equals(rolNombre)) {
                var sectorOpt = sectorRepository.findBySupervisorSectorialId(usuario.getId());
                if (sectorOpt.isPresent()) {
                    var sector = sectorOpt.get();
                    sectorId = sector.getId();
                    sectorNombre = sector.getNombre();
                }
            }

            Long superId = usuario.getSupervisor() != null ? usuario.getSupervisor().getId() : null;
            String superNombre = usuario.getSupervisor() != null ? usuario.getSupervisor().getNombreCompleto() : null;

            log.info("=== LOGIN EXITOSO ===");

            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tipo("Bearer")
                    .usuarioId(usuario.getId())
                    .email(usuario.getEmail())
                    .nombreCompleto(usuario.getNombreCompleto())
                    .rol(rolNombre)
                    .sectorId(sectorId)
                    .sectorNombre(sectorNombre)
                    .supervisorId(superId)
                    .supervisorNombre(superNombre)
                    .build();

        } catch (Exception ex) {
            log.error("Error en autenticación: {}", ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Credenciales inválidas");
        }
    }
*/
    /**
     * 
     * Refresca el Access Token utilizando el Refresh Token
     */
    public LoginResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        // Buscar y validar el refresh token
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token no válido"));

        if (!storedToken.isValid()) {
            throw new RuntimeException("Refresh token expirado o revocado");
        }

        // Obtener usuario
        Usuario usuario = storedToken.getUsuario();

        if (!usuario.getActivo()) {
            throw new RuntimeException("La cuenta de usuario está deshabilitada");
        }

        // Generar nuevo access token
        String newAccessToken = jwtUtils.generateAccessToken(usuario);

        log.info("Access token refrescado para usuario {}", usuario.getEmail());

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tipo("Bearer")
                .usuarioId(usuario.getId())
                .email(usuario.getEmail())
                .nombreCompleto(usuario.getNombreCompleto())
                .rol(usuario.getRol().getNombre())
                .build();
    }

    /**
     * Cierra sesión revocando el refresh token
     */
    public void logout(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token no encontrado"));

        token.setRevocado(true);
        refreshTokenRepository.save(token);

        log.info("Usuario {} cerró sesión exitosamente", token.getUsuario().getEmail());
    }

    /**
     * Guarda un nuevo refresh token en la base de datos
     */
    private void saveRefreshToken(Usuario usuario, String tokenString) {
        RefreshToken token = RefreshToken.builder()
                .usuario(usuario)
                .token(tokenString)
                .expiraEn(LocalDateTime.now().plusDays(7)) // 7 días de validez
                .revocado(false)
                .ipOrigen("0.0.0.0") // TODO: obtener IP real de la request
                .userAgent("") // TODO: obtener user agent real de la request
                .build();

        refreshTokenRepository.save(token);
    }

    /**
     * Registra un nuevo usuario y le envía la contraseña por correo
     */
    public void registerUser(RegisterRequest request) {
        if (usuarioRepository.findFirstByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        // Asignamos el rol LIDER por defecto según el plan (podría cambiarse a otro
        // según necesidad)
        Rol rol = rolRepository.findByNombre("LIDER")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado en el sistema"));

        // Generar contraseña aleatoria (8 caracteres)
        String rawPassword = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Crear y guardar el usuario
        Usuario nuevoUsuario = Usuario.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .passwordHash(encodedPassword)
                .rol(rol)
                .activo(true)
                .build();

        usuarioRepository.save(nuevoUsuario);
        log.info("Nuevo usuario registrado: {}", request.getEmail());

        // Enviar contraseña por correo
        emailService.enviarRecuperacionContrasena(request.getEmail(), rawPassword);
    }

    /**
     * Restablece la contraseña de un usuario existente
     */
    public void resetPassword(String email) {
        Usuario usuario = usuarioRepository.findFirstByEmail(email)
                .orElseThrow(() -> new RuntimeException("No existe un usuario registrado con este correo"));

        if (!usuario.getActivo()) {
            throw new RuntimeException("La cuenta de usuario está deshabilitada");
        }

        // Generar nueva contraseña aleatoria (8 caracteres)
        String newRawPassword = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = passwordEncoder.encode(newRawPassword);

        // Actualizar el usuario
        usuario.setPasswordHash(encodedPassword);
        usuarioRepository.save(usuario);
        log.info("Contraseña restablecida para el usuario: {}", email);

        // Enviar la nueva contraseña por correo
        emailService.enviarReinicioContrasena(email, newRawPassword);
    }

}
