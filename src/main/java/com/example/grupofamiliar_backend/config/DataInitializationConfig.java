package com.example.grupofamiliar_backend.config;

import com.example.grupofamiliar_backend.entity.Rol;
import com.example.grupofamiliar_backend.entity.Usuario;
import com.example.grupofamiliar_backend.repository.RolRepository;
import com.example.grupofamiliar_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializationConfig {

    @Bean
    public CommandLineRunner initializeData(RolRepository rolRepository, 
                                           UsuarioRepository usuarioRepository,
                                           PasswordEncoder passwordEncoder) {
        return args -> {
            // Crear roles si no existen
            if (rolRepository.findByNombre("ADMIN").isEmpty()) {
                log.info("Creando roles estándar...");
                
                Rol adminRole = Rol.builder()
                        .nombre("ADMIN")
                        .descripcion("Administrador del sistema")
                        .build();
                
                Rol supGeneralRole = Rol.builder()
                        .nombre("SUP_GENERAL")
                        .descripcion("Supervisor General")
                        .build();
                
                Rol supSectorialRole = Rol.builder()
                        .nombre("SUP_SECTORIAL")
                        .descripcion("Supervisor Sectorial")
                        .build();
                
                Rol liderRole = Rol.builder()
                        .nombre("LIDER")
                        .descripcion("Líder de grupo")
                        .build();
                
                Rol tesoreroRole = Rol.builder()
                        .nombre("TESORERO")
                        .descripcion("Tesorero")
                        .build();
                
                rolRepository.save(adminRole);
                rolRepository.save(supGeneralRole);
                rolRepository.save(supSectorialRole);
                rolRepository.save(liderRole);
                rolRepository.save(tesoreroRole);
                
                log.info("Roles creados exitosamente");
            }

            // Crear usuario de prueba admin si no existe
            if (usuarioRepository.findFirstByEmail("admin@grupofamiliar.com").isEmpty()) {
                log.info("Creando usuario administrador de prueba...");
                
                Rol adminRole = rolRepository.findByNombre("ADMIN")
                        .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));
                
                Usuario adminUser = Usuario.builder()
                        .nombres("Luis")
                        .apellidos("Administrador")
                        .email("admin@grupofamiliar.com")
                        .telefono("1234567890")
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .rol(adminRole)
                        .activo(true)
                        .build();
                
                usuarioRepository.save(adminUser);
                log.info("Usuario administrador creado: admin@grupofamiliar.com / admin123");
            }

            // Crear usuario líder de prueba
            if (usuarioRepository.findFirstByEmail("lider@grupofamiliar.com").isEmpty()) {
                log.info("Creando usuario líder de prueba...");
                
                Rol liderRole = rolRepository.findByNombre("LIDER")
                        .orElseThrow(() -> new RuntimeException("Rol LIDER no encontrado"));
                
                Usuario liderUser = Usuario.builder()
                        .nombres("Juan")
                        .apellidos("Líder")
                        .email("lider@grupofamiliar.com")
                        .telefono("9876543210")
                        .passwordHash(passwordEncoder.encode("lider123"))
                        .rol(liderRole)
                        .activo(true)
                        .build();
                
                usuarioRepository.save(liderUser);
                log.info("Usuario líder creado: lider@grupofamiliar.com / lider123");
            }
        };
    }

}
