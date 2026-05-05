package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.dto.ReporteDTO;
import com.example.grupofamiliar_backend.entity.*;
import com.example.grupofamiliar_backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;

    private final SectorRepository sectorRepository;

    private final UsuarioRepository usuarioRepository;

    private final GrupoFamiliarRepository grupoFamiliarRepository;

    private final EmailService emailService;

    /**
     * Obtiene todos los reportes del período actual
     */
    public List<ReporteDTO> obtenerTodos() {
        return reporteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene reportes de un sector específico
     */
    public List<ReporteDTO> obtenerPorSector(Long sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));

        return reporteRepository.findByGrupoFamiliar_Sector(sector).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene reportes de un grupo familiar específico
     */
    public List<ReporteDTO> obtenerPorGrupoFamiliar(Long grupoId) {
        GrupoFamiliar grupoFamiliar = grupoFamiliarRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo Familiar no encontrado"));

        return reporteRepository.findByGrupoFamiliar(grupoFamiliar).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene reportes de un rango de fechas exactas
     */
    public List<ReporteDTO> obtenerPorSemana(java.time.LocalDate semanaDesde, java.time.LocalDate semanaHasta) {
        return reporteRepository.findBySemanaDesdeAndSemanaHasta(semanaDesde, semanaHasta).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los reportes en un rango de fechas (para ADMIN)
     */
    public List<ReporteDTO> obtenerTodosPorRango(LocalDate desde, LocalDate hasta) {
        return reporteRepository.findByRangoDeFechas(desde, hasta).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene reportes de un sector en un rango de fechas
     */
    public List<ReporteDTO> obtenerPorSectorYRango(Long sectorId, LocalDate desde, LocalDate hasta) {
        return reporteRepository.findBySectorIdAndRangoDeFechas(sectorId, desde, hasta).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la lista de grupos familiares de un sector que NO tienen reporte en el rango dado
     */
    public List<GrupoFamiliarAlertaDTO> obtenerLideresSinReporte(Long sectorId, LocalDate desde, LocalDate hasta) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));

        // Grupos del sector
        List<GrupoFamiliar> todosLosGrupos = grupoFamiliarRepository.findBySectorId(sectorId);

        // IDs de grupos que SÍ tienen reporte
        Set<Long> gruposConReporte = reporteRepository
                .findGruposConReporteBySectorIdAndRango(sectorId, desde, hasta)
                .stream().collect(Collectors.toSet());

        // Filtrar los que NO tienen reporte
        return todosLosGrupos.stream()
                .filter(g -> !gruposConReporte.contains(g.getId()))
                .map(g -> new GrupoFamiliarAlertaDTO(
                        g.getId(),
                        g.getNombre(),
                        g.getLider() != null ? g.getLider().getId() : null,
                        g.getLider() != null ? g.getLider().getNombreCompleto() : "Sin líder",
                        sector.getNombre()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un reporte por ID
     */
    public ReporteDTO obtenerPorId(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        return convertToDTO(reporte);
    }

    /**
     * Crea o actualiza un reporte
     */
    @Transactional
    public ReporteDTO crearOActualizar(ReporteDTO reporteDTO) {
        GrupoFamiliar grupoFamiliar;
        if (reporteDTO.getGrupoFamiliarId() != null) {
            grupoFamiliar = grupoFamiliarRepository.findById(reporteDTO.getGrupoFamiliarId())
                .orElseThrow(() -> new RuntimeException("Grupo Familiar no encontrado"));
        } else if (reporteDTO.getLiderId() != null) {
            grupoFamiliar = grupoFamiliarRepository.findByLiderId(reporteDTO.getLiderId())
                .orElseThrow(() -> new RuntimeException("Grupo Familiar no encontrado para el líder especificado"));
        } else {
            throw new RuntimeException("Se requiere grupoFamiliarId o liderId");
        }

        // Buscar si ya existe un reporte para este grupo y rango de fechas
        Reporte reporte = reporteRepository.findByGrupoFamiliarAndSemanaDesdeAndSemanaHasta(grupoFamiliar, reporteDTO.getSemanaDesde(), reporteDTO.getSemanaHasta())
                .orElse(new Reporte());

        if (reporte.getId() != null && !reporte.getEstado().equals(Reporte.EstadoReporte.BORRADOR)) {
            throw new RuntimeException("Ya existe un reporte para esta semana que no está en estado BORRADOR");
        }

        // Actualizar datos del reporte
        reporte.setGrupoFamiliar(grupoFamiliar);
        reporte.setSemanaDesde(reporteDTO.getSemanaDesde());
        reporte.setSemanaHasta(reporteDTO.getSemanaHasta());
        reporte.setTiempoOracion(reporteDTO.getTiempoOracion() != null ? reporteDTO.getTiempoOracion() : 0);
        reporte.setAyuno(reporteDTO.getAyuno() != null ? reporteDTO.getAyuno() : false);
        reporte.setCantHermanos(reporteDTO.getCantHermanos());
        reporte.setCantAmigos(reporteDTO.getCantAmigos());
        reporte.setCantAdolescentes(reporteDTO.getCantAdolescentes());
        reporte.setCantConvertidos(reporteDTO.getCantConvertidos());
        reporte.setCantNinosCristianos(reporteDTO.getCantNinosCristianos());
        reporte.setCantNinosAmigos(reporteDTO.getCantNinosAmigos());
        reporte.setCantVisitaConsolidacion(reporteDTO.getCantVisitaConsolidacion());
        reporte.setCantVisitaCasaDePaz(reporteDTO.getCantVisitaCasaDePaz());
        reporte.setCantVisitaHogar(reporteDTO.getCantVisitaHogar());
        reporte.setCantHrOracion(reporteDTO.getCantHrOracion());
        reporte.setCantHrMep(reporteDTO.getCantHrMep());
        reporte.setCantHrDiscipulado(reporteDTO.getCantHrDiscipulado());
        reporte.setCantRetiroEspiritual(reporteDTO.getCantRetiroEspiritual());
        reporte.setOfrendaSabado(reporteDTO.getOfrendaSabado());
        reporte.setOfrendaNinos(reporteDTO.getOfrendaNinos());
        reporte.setOfrendaMiercoles(reporteDTO.getOfrendaMiercoles());
        reporte.setObservaciones(reporteDTO.getObservaciones());

        reporte = reporteRepository.save(reporte);
        log.info("Reporte del grupo {} guardado en borrador", grupoFamiliar.getNombre());

        return convertToDTO(reporte);
    }

    /**
     * Envía un reporte (cambia estado a ENVIADO)
     */
    @Transactional
    public ReporteDTO enviar(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        reporte.setEstado(Reporte.EstadoReporte.ENVIADO);
        reporte.setEnviadoEn(java.time.LocalDateTime.now());

        reporte = reporteRepository.save(reporte);
        log.info("Reporte {} enviado", id);

        return convertToDTO(reporte);
    }

    /**
     * Aprueba un reporte (cambia estado a APROBADO)
     */
    @Transactional
    public ReporteDTO aprobar(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        if (!reporte.getEstado().equals(Reporte.EstadoReporte.ENVIADO)) {
            throw new RuntimeException("Solo se pueden aprobar reportes con estado ENVIADO");
        }

        reporte.setEstado(Reporte.EstadoReporte.APROBADO);
        reporte = reporteRepository.save(reporte);
        log.info("Reporte {} aprobado", id);

        // Enviar notificación al líder
        Usuario lider = reporte.getGrupoFamiliar().getLider();
        if (lider != null && lider.getEmail() != null) {
            emailService.enviarNotificacionAprobacion(lider.getEmail(), 
                reporte.getSemanaDesde().toString(), 
                reporte.getSemanaHasta().toString());
        }

        return convertToDTO(reporte);
    }

    /**
     * Rechaza un reporte (vuelve a BORRADOR)
     */
    @Transactional
    public ReporteDTO rechazar(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        reporte.setEstado(Reporte.EstadoReporte.ENVIADO);
        reporte.setEnviadoEn(null);

        reporte = reporteRepository.save(reporte);
        log.info("Reporte {} rechazado", id);

        return convertToDTO(reporte);
    }

    /**
     * Actualiza un reporte existente (solo si está en BORRADOR)
     */
    @Transactional
    public ReporteDTO actualizar(Long id, ReporteDTO reporteDTO) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        if (!reporte.getEstado().equals(Reporte.EstadoReporte.ENVIADO)) {
            throw new RuntimeException("Solo se pueden editar reportes en estado ENVIADO");
        }

        reporte.setTiempoOracion(reporteDTO.getTiempoOracion() != null ? reporteDTO.getTiempoOracion() : 0);
        reporte.setAyuno(reporteDTO.getAyuno() != null ? reporteDTO.getAyuno() : false);
        reporte.setCantHermanos(reporteDTO.getCantHermanos());
        reporte.setCantAmigos(reporteDTO.getCantAmigos());
        reporte.setCantAdolescentes(reporteDTO.getCantAdolescentes());
        reporte.setCantConvertidos(reporteDTO.getCantConvertidos());
        reporte.setCantNinosCristianos(reporteDTO.getCantNinosCristianos());
        reporte.setCantNinosAmigos(reporteDTO.getCantNinosAmigos());
        reporte.setCantVisitaConsolidacion(reporteDTO.getCantVisitaConsolidacion());
        reporte.setCantVisitaCasaDePaz(reporteDTO.getCantVisitaCasaDePaz());
        reporte.setCantVisitaHogar(reporteDTO.getCantVisitaHogar());
        reporte.setCantHrOracion(reporteDTO.getCantHrOracion());
        reporte.setCantHrMep(reporteDTO.getCantHrMep());
        reporte.setCantHrDiscipulado(reporteDTO.getCantHrDiscipulado());
        reporte.setCantRetiroEspiritual(reporteDTO.getCantRetiroEspiritual());
        reporte.setOfrendaSabado(reporteDTO.getOfrendaSabado());
        reporte.setOfrendaNinos(reporteDTO.getOfrendaNinos());
        reporte.setOfrendaMiercoles(reporteDTO.getOfrendaMiercoles());
        reporte.setObservaciones(reporteDTO.getObservaciones());

        reporte = reporteRepository.save(reporte);
        log.info("Reporte {} actualizado", id);

        return convertToDTO(reporte);
    }

    /**
     * Elimina un reporte (solo si está en BORRADOR)
     */
    @Transactional
    public void eliminar(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        if (!reporte.getEstado().equals(Reporte.EstadoReporte.ENVIADO)) {
            throw new RuntimeException("Solo se pueden eliminar reportes en estado ENVIADO");
        }

        reporteRepository.delete(reporte);
        log.info("Reporte {} eliminado", id);
    }

    private ReporteDTO convertToDTO(Reporte reporte) {
        return ReporteDTO.builder()
                .id(reporte.getId())
                .sectorId(reporte.getGrupoFamiliar().getSector().getId())
                .sectorNombre(reporte.getGrupoFamiliar().getSector().getNombre())
                .grupoFamiliarId(reporte.getGrupoFamiliar().getId())
                .grupoFamiliarNombre(reporte.getGrupoFamiliar().getNombre())
                .semanaDesde(reporte.getSemanaDesde())
                .semanaHasta(reporte.getSemanaHasta())
                .liderId(reporte.getGrupoFamiliar().getLider() != null ? reporte.getGrupoFamiliar().getLider().getId() : null)
                .liderNombre(reporte.getGrupoFamiliar().getLider() != null ? reporte.getGrupoFamiliar().getLider().getNombreCompleto() : null)
                .estado(reporte.getEstado().toString())
                .tiempoOracion(reporte.getTiempoOracion())
                .ayuno(reporte.getAyuno())
                .cantHermanos(reporte.getCantHermanos())
                .cantAmigos(reporte.getCantAmigos())
                .cantAdolescentes(reporte.getCantAdolescentes())
                .cantConvertidos(reporte.getCantConvertidos())
                .cantNinosCristianos(reporte.getCantNinosCristianos())
                .cantNinosAmigos(reporte.getCantNinosAmigos())
                .cantVisitaConsolidacion(reporte.getCantVisitaConsolidacion())
                .cantVisitaCasaDePaz(reporte.getCantVisitaCasaDePaz())
                .cantVisitaHogar(reporte.getCantVisitaHogar())
                .cantHrOracion(reporte.getCantHrOracion())
                .cantHrMep(reporte.getCantHrMep())
                .cantHrDiscipulado(reporte.getCantHrDiscipulado())
                .cantRetiroEspiritual(reporte.getCantRetiroEspiritual())
                .ofrendaSabado(reporte.getOfrendaSabado())
                .ofrendaNinos(reporte.getOfrendaNinos())
                .ofrendaMiercoles(reporte.getOfrendaMiercoles())
                .observaciones(reporte.getObservaciones())
                .build();
    }

    // DTO interno para alertas
    public static class GrupoFamiliarAlertaDTO {
        public Long grupoId;
        public String grupoNombre;
        public Long liderId;
        public String liderNombre;
        public String sectorNombre;

        public GrupoFamiliarAlertaDTO(Long grupoId, String grupoNombre, Long liderId, String liderNombre, String sectorNombre) {
            this.grupoId = grupoId;
            this.grupoNombre = grupoNombre;
            this.liderId = liderId;
            this.liderNombre = liderNombre;
            this.sectorNombre = sectorNombre;
        }
    }

}
