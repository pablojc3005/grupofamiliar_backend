package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.entity.Sector;
import com.example.grupofamiliar_backend.repository.ReporteRepository;
import com.example.grupofamiliar_backend.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsolidadosService {

    private final ReporteRepository reporteRepository;
    private final SectorRepository sectorRepository;

    public Object obtenerConsolidadoSectorial(Long sectorId, LocalDate semanaDesde, LocalDate semanaHasta) {
        // Implementar consulta a v_consolidado_sectorial
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));
        
        return reporteRepository.findBySectorIdInAndSemanaDesdeAndSemanaHasta(List.of(sectorId), semanaDesde, semanaHasta);
    }

    public Object obtenerConsolidadoGeneral(LocalDate semanaDesde, LocalDate semanaHasta) {
        // Implementar consulta a v_consolidado_general
        return reporteRepository.findBySemanaDesdeAndSemanaHasta(semanaDesde, semanaHasta);
    }

    public Object obtenerConsolidadoActualSector(Long sectorId) {
        // Mock current week
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.minusDays(hoy.getDayOfWeek().getValue() - 1);
        LocalDate finSemana = inicioSemana.plusDays(6);
        return obtenerConsolidadoSectorial(sectorId, inicioSemana, finSemana);
    }

    public Object obtenerConsolidadoActualGeneral() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.minusDays(hoy.getDayOfWeek().getValue() - 1);
        LocalDate finSemana = inicioSemana.plusDays(6);
        
        return obtenerConsolidadoGeneral(inicioSemana, finSemana);
    }

    public List<?> obtenerHistoricoSector(Long sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));
        
        return reporteRepository.findByGrupoFamiliar_Sector_Id(sector.getId());
    }

    public List<?> obtenerHistoricoGeneral() {
        return reporteRepository.findAll();
    }
}
