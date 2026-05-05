package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.dto.SectorDTO;
import com.example.grupofamiliar_backend.entity.Sector;
import com.example.grupofamiliar_backend.entity.Usuario;
import com.example.grupofamiliar_backend.repository.SectorRepository;
import com.example.grupofamiliar_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;

    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los sectores activos
     */
    public List<SectorDTO> obtenerTodos() {
        return sectorRepository.findByActivo(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un sector por ID
     */
    public SectorDTO obtenerPorId(Long id) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));

        return convertToDTO(sector);
    }

    /**
     * Crea un nuevo sector
     */
    @Transactional
    public SectorDTO crear(SectorDTO sectorDTO) {
        Sector sector = Sector.builder()
                .nombre(sectorDTO.getNombre())
                .codigo(sectorDTO.getCodigo())
                .activo(true)
                .build();

        if (sectorDTO.getSupervisorId() != null) {
            Usuario supervisor = usuarioRepository.findById(sectorDTO.getSupervisorId())
                    .orElseThrow(() -> new RuntimeException("Supervisor no encontrado"));
            sector.setSupervisor(supervisor);
        }

        sector = sectorRepository.save(sector);
        log.info("Sector {} creado exitosamente", sector.getNombre());

        return convertToDTO(sector);
    }

    /**
     * Actualiza un sector existente
     */
    @Transactional
    public SectorDTO actualizar(Long id, SectorDTO sectorDTO) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));

        sector.setNombre(sectorDTO.getNombre());
        if (sectorDTO.getCodigo() != null) {
            sector.setCodigo(sectorDTO.getCodigo());
        }

        if (sectorDTO.getSupervisorId() != null) {
            Usuario supervisor = usuarioRepository.findById(sectorDTO.getSupervisorId())
                    .orElseThrow(() -> new RuntimeException("Supervisor no encontrado"));
            sector.setSupervisor(supervisor);
        }

        sector = sectorRepository.save(sector);
        log.info("Sector {} actualizado", sector.getNombre());

        return convertToDTO(sector);
    }

    /**
     * Desactiva un sector
     */
    @Transactional
    public void desactivar(Long id) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));

        sector.setActivo(false);
        sectorRepository.save(sector);
        log.info("Sector {} desactivado", sector.getNombre());
    }

    private SectorDTO convertToDTO(Sector sector) {
        return SectorDTO.builder()
                .id(sector.getId())
                .nombre(sector.getNombre())
                .codigo(sector.getCodigo())
                .supervisorId(sector.getSupervisor() != null ? sector.getSupervisor().getId() : null)
                .supervisorNombre(sector.getSupervisor() != null ? sector.getSupervisor().getNombreCompleto() : null)
                .activo(sector.getActivo())
                .build();
    }

}
