package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.dto.ReporteSectorialDTO;
import com.example.grupofamiliar_backend.entity.ReporteSectorial;
import com.example.grupofamiliar_backend.entity.Sector;
import com.example.grupofamiliar_backend.entity.Usuario;
import com.example.grupofamiliar_backend.repository.ReporteSectorialRepository;
import com.example.grupofamiliar_backend.repository.SectorRepository;
import com.example.grupofamiliar_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteSectorialService {

    private final ReporteSectorialRepository reporteSectorialRepository;
    private final SectorRepository sectorRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ReporteSectorialDTO> obtenerTodos() {
        return reporteSectorialRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteSectorialDTO> obtenerPorSupervisor(Long supervisorId) {
        return reporteSectorialRepository.findBySupervisorId(supervisorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteSectorialDTO> obtenerPorSector(Long sectorId) {
        return reporteSectorialRepository.findBySectorId(sectorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteSectorialDTO> obtenerPorSectorYRango(Long sectorId, LocalDate desde, LocalDate hasta) {
        return reporteSectorialRepository.findBySectorIdAndRangoDeFechas(sectorId, desde, hasta).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReporteSectorialDTO> obtenerTodosPorRango(LocalDate desde, LocalDate hasta) {
        return reporteSectorialRepository.findByRangoDeFechas(desde, hasta).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReporteSectorialDTO obtenerPorId(Long id) {
        ReporteSectorial repo = reporteSectorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte Sectorial no encontrado con ID: " + id));
        return convertToDTO(repo);
    }

    @Transactional
    public ReporteSectorialDTO crearOActualizar(ReporteSectorialDTO dto) {
        if (dto.getSectorId() == null) {
            throw new RuntimeException("Se requiere sectorId");
        }
        if (dto.getSupervisorId() == null) {
            throw new RuntimeException("Se requiere supervisorId");
        }

        Sector sector = sectorRepository.findById(dto.getSectorId())
                .orElseThrow(() -> new RuntimeException("Sector no encontrado"));
        Usuario supervisor = usuarioRepository.findById(dto.getSupervisorId())
                .orElseThrow(() -> new RuntimeException("Supervisor no encontrado"));

        // Buscar reporte previo para la misma semana y sector
        ReporteSectorial reporte = reporteSectorialRepository
                .findBySectorAndSemanaDesdeAndSemanaHasta(sector, dto.getSemanaDesde(), dto.getSemanaHasta())
                .orElse(new ReporteSectorial());

        if (reporte.getId() != null && !reporte.getEstado().equals(ReporteSectorial.EstadoReporte.BORRADOR)) {
            throw new RuntimeException("Ya existe un reporte para esta semana que ya fue enviado o aprobado");
        }

        // Mapear campos
        reporte.setSector(sector);
        reporte.setSupervisor(supervisor);
        reporte.setSemanaDesde(dto.getSemanaDesde());
        reporte.setSemanaHasta(dto.getSemanaHasta());
        // Devocional
        reporte.setHorasOracion(dto.getHorasOracion() != null ? dto.getHorasOracion() : 0);
        reporte.setMinutosOracion(dto.getMinutosOracion() != null ? dto.getMinutosOracion() : 0);
        reporte.setLecturaBiblia(dto.getLecturaBiblia() != null ? dto.getLecturaBiblia() : false);
        reporte.setAyuno(dto.getAyuno() != null ? dto.getAyuno() : false);
        reporte.setCultoLiderazgo(dto.getCultoLiderazgo() != null ? dto.getCultoLiderazgo() : false);
        reporte.setDiezmo(dto.getDiezmo() != null ? dto.getDiezmo() : false);
        // Dinámicos
        reporte.setAtencionesJson(dto.getAtencionesJson());
        reporte.setSupervisionesJson(dto.getSupervisionesJson());
        reporte.setEvaluacionesEquipoJson(dto.getEvaluacionesEquipoJson());
        // Planificación
        reporte.setPlanificacionGrupo(dto.getPlanificacionGrupo());
        reporte.setPlanificacionFecha(dto.getPlanificacionFecha());
        reporte.setPlanificacionHora(dto.getPlanificacionHora());
        reporte.setPlanificacionPositivos(dto.getPlanificacionPositivos());
        reporte.setPlanificacionNegativos(dto.getPlanificacionNegativos());
        // Firma
        reporte.setFirma(dto.getFirma());

        if (reporte.getEstado() == null) {
            reporte.setEstado(ReporteSectorial.EstadoReporte.BORRADOR);
        }

        reporte = reporteSectorialRepository.save(reporte);
        log.info("Reporte Sectorial guardado como BORRADOR para el sector {}", sector.getNombre());
        return convertToDTO(reporte);
    }

    @Transactional
    public ReporteSectorialDTO enviar(Long id) {
        ReporteSectorial reporte = reporteSectorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte Sectorial no encontrado"));

        reporte.setEstado(ReporteSectorial.EstadoReporte.ENVIADO);
        reporte.setEnviadoEn(LocalDateTime.now());
        reporte = reporteSectorialRepository.save(reporte);
        log.info("Reporte Sectorial {} enviado", id);
        return convertToDTO(reporte);
    }

    @Transactional
    public ReporteSectorialDTO aprobar(Long id) {
        ReporteSectorial reporte = reporteSectorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte Sectorial no encontrado"));

        if (!reporte.getEstado().equals(ReporteSectorial.EstadoReporte.ENVIADO)) {
            throw new RuntimeException("Solo se pueden aprobar reportes en estado ENVIADO");
        }

        reporte.setEstado(ReporteSectorial.EstadoReporte.APROBADO);
        reporte = reporteSectorialRepository.save(reporte);
        log.info("Reporte Sectorial {} aprobado", id);
        return convertToDTO(reporte);
    }

    @Transactional
    public ReporteSectorialDTO rechazar(Long id) {
        ReporteSectorial reporte = reporteSectorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte Sectorial no encontrado"));

        reporte.setEstado(ReporteSectorial.EstadoReporte.BORRADOR);
        reporte.setEnviadoEn(null);
        reporte = reporteSectorialRepository.save(reporte);
        log.info("Reporte Sectorial {} rechazado y devuelto a borrador", id);
        return convertToDTO(reporte);
    }

    @Transactional
    public void eliminar(Long id) {
        ReporteSectorial reporte = reporteSectorialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte Sectorial no encontrado"));

        if (!reporte.getEstado().equals(ReporteSectorial.EstadoReporte.BORRADOR)) {
            throw new RuntimeException("Solo se pueden eliminar reportes en estado BORRADOR");
        }

        reporteSectorialRepository.delete(reporte);
        log.info("Reporte Sectorial {} eliminado", id);
    }

    private ReporteSectorialDTO convertToDTO(ReporteSectorial entity) {
        return ReporteSectorialDTO.builder()
                .id(entity.getId())
                .supervisorId(entity.getSupervisor().getId())
                .supervisorNombre(entity.getSupervisor().getNombreCompleto())
                .sectorId(entity.getSector().getId())
                .sectorNombre(entity.getSector().getNombre())
                .semanaDesde(entity.getSemanaDesde())
                .semanaHasta(entity.getSemanaHasta())
                .estado(entity.getEstado().toString())
                // Devocional
                .horasOracion(entity.getHorasOracion())
                .minutosOracion(entity.getMinutosOracion())
                .lecturaBiblia(entity.getLecturaBiblia())
                .ayuno(entity.getAyuno())
                .cultoLiderazgo(entity.getCultoLiderazgo())
                .diezmo(entity.getDiezmo())
                // Dinámicos
                .atencionesJson(entity.getAtencionesJson())
                .supervisionesJson(entity.getSupervisionesJson())
                .evaluacionesEquipoJson(entity.getEvaluacionesEquipoJson())
                // Planificación
                .planificacionGrupo(entity.getPlanificacionGrupo())
                .planificacionFecha(entity.getPlanificacionFecha())
                .planificacionHora(entity.getPlanificacionHora())
                .planificacionPositivos(entity.getPlanificacionPositivos())
                .planificacionNegativos(entity.getPlanificacionNegativos())
                // Firma
                .firma(entity.getFirma())
                .enviadoEn(entity.getEnviadoEn())
                .creadoEn(entity.getCreadoEn())
                .actualizadoEn(entity.getActualizadoEn())
                .build();
    }
}
