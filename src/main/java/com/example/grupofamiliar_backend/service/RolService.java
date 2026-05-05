package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.entity.Rol;
import com.example.grupofamiliar_backend.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    public Rol obtenerPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    public Rol obtenerPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con nombre: " + nombre));
    }

    public Rol crear(Rol rol) {
        if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre del rol no puede estar vacío");
        }
        return rolRepository.save(rol);
    }

    public Rol actualizar(Long id, Rol rolActualizado) {
        Rol rol = obtenerPorId(id);
        if (rolActualizado.getNombre() != null) {
            rol.setNombre(rolActualizado.getNombre());
        }
        if (rolActualizado.getDescripcion() != null) {
            rol.setDescripcion(rolActualizado.getDescripcion());
        }
        return rolRepository.save(rol);
    }

    public void eliminar(Long id) {
        Rol rol = obtenerPorId(id);
        rolRepository.delete(rol);
    }
}
