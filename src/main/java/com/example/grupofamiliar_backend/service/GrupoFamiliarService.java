package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.dto.GrupoFamiliarRequest;
import com.example.grupofamiliar_backend.entity.GrupoFamiliar;
import com.example.grupofamiliar_backend.entity.Sector;
import com.example.grupofamiliar_backend.entity.Usuario;
import com.example.grupofamiliar_backend.repository.GrupoFamiliarRepository;
import com.example.grupofamiliar_backend.repository.SectorRepository;
import com.example.grupofamiliar_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoFamiliarService {

    private final GrupoFamiliarRepository grupoFamiliarRepository;
    private final UsuarioRepository usuarioRepository;
    private final SectorRepository sectorRepository;

    public List<GrupoFamiliar> obtenerTodos() {
        return grupoFamiliarRepository.findAll();
    }

    public GrupoFamiliar obtenerPorId(Long id) {
        return grupoFamiliarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo Familiar no encontrado con ID: " + id));
    }

    public GrupoFamiliar crear(GrupoFamiliarRequest request) {
        if (request.getIdLider() == null) {
            throw new RuntimeException("Debe asignar un líder al grupo familiar");
        }
        if (request.getIdSector() == null) {
            throw new RuntimeException("Debe asignar un sector al grupo familiar");
        }

        Usuario lider = usuarioRepository.findById(request.getIdLider())
                .orElseThrow(() -> new RuntimeException("Líder no encontrado"));
                
        if (!"LIDER".equals(lider.getRol().getNombre())) {
            throw new RuntimeException("El usuario seleccionado no tiene el rol de LIDER");
        }
        
        if (grupoFamiliarRepository.findByLiderId(lider.getId()).isPresent()) {
            throw new RuntimeException("Este líder ya tiene un grupo familiar asignado");
        }

        Sector sector = sectorRepository.findById(request.getIdSector())
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));

        GrupoFamiliar grupo = GrupoFamiliar.builder()
                .nombre(request.getNombre())
                .direccion(request.getDireccion())
                .lider(lider)
                .sector(sector)
                .activo(request.getActivo() != null ? request.getActivo() : true)
                .build();

        return grupoFamiliarRepository.save(grupo);
    }

    public GrupoFamiliar actualizar(Long id, GrupoFamiliarRequest request) {
        GrupoFamiliar grupo = obtenerPorId(id);

        if (request.getNombre() != null) grupo.setNombre(request.getNombre());
        if (request.getDireccion() != null) grupo.setDireccion(request.getDireccion());
        if (request.getActivo() != null) grupo.setActivo(request.getActivo());

        if (request.getIdLider() != null && !request.getIdLider().equals(grupo.getLider().getId())) {
            Usuario lider = usuarioRepository.findById(request.getIdLider())
                    .orElseThrow(() -> new RuntimeException("Líder no encontrado"));
            if (!"LIDER".equals(lider.getRol().getNombre())) {
                throw new RuntimeException("El usuario seleccionado no tiene el rol de LIDER");
            }
            // Verificar si el nuevo líder ya tiene un grupo
            grupoFamiliarRepository.findByLiderId(lider.getId()).ifPresent(g -> {
                if (!g.getId().equals(grupo.getId())) {
                    throw new RuntimeException("El nuevo líder ya tiene otro grupo familiar asignado");
                }
            });
            grupo.setLider(lider);
        }

        if (request.getIdSector() != null && !request.getIdSector().equals(grupo.getSector().getId())) {
            Sector sector = sectorRepository.findById(request.getIdSector())
                    .orElseThrow(() -> new RuntimeException("Sector no encontrado"));
            grupo.setSector(sector);
        }

        return grupoFamiliarRepository.save(grupo);
    }

    public void eliminar(Long id) {
        GrupoFamiliar grupo = obtenerPorId(id);
        grupoFamiliarRepository.delete(grupo);
    }

    public void cambiarEstado(Long id) {
        GrupoFamiliar grupo = obtenerPorId(id);
        grupo.setActivo(!grupo.getActivo());
        grupoFamiliarRepository.save(grupo);
    }

    public List<GrupoFamiliar> obtenerPorSector(Long sectorId) {
        return grupoFamiliarRepository.findBySectorId(sectorId);
    }
}
