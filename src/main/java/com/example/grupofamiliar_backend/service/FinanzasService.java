package com.example.grupofamiliar_backend.service;

import com.example.grupofamiliar_backend.entity.CategoriaFinanciera;
import com.example.grupofamiliar_backend.entity.MovimientoFinanciero;
import com.example.grupofamiliar_backend.entity.Reporte;
import com.example.grupofamiliar_backend.repository.CategoriaFinancieraRepository;
import com.example.grupofamiliar_backend.repository.MovimientoFinancieroRepository;
import com.example.grupofamiliar_backend.repository.ReporteRepository;
import com.example.grupofamiliar_backend.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanzasService {

    private final MovimientoFinancieroRepository movimientoRepository;
    private final CategoriaFinancieraRepository categoriaRepository;
    private final ReporteRepository reporteRepository;
    private final SectorRepository sectorRepository;

    // ────────────────────────────────────
    // MOVIMIENTOS FINANCIEROS
    // ────────────────────────────────────

    public List<MovimientoFinanciero> obtenerMovimientos() {
        return movimientoRepository.findAll();
    }

    public MovimientoFinanciero obtenerMovimiento(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
    }

    public List<MovimientoFinanciero> obtenerMovimientosPorFecha(java.time.LocalDate start, java.time.LocalDate end) {
        return movimientoRepository.findByFechaBetween(start, end);
    }

    public List<MovimientoFinanciero> obtenerMovimientosPorSector(Long sectorId) {
        return movimientoRepository.findBySectorId(sectorId);
    }

    public List<MovimientoFinanciero> obtenerMovimientosPorCategoria(Long categoriaId) {
        return movimientoRepository.findByCategoriaId(categoriaId);
    }

    public MovimientoFinanciero crearMovimiento(MovimientoFinanciero movimiento) {
        if (movimiento.getMonto() == null || movimiento.getMonto().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El monto debe ser mayor a cero");
        }
        if (movimiento.getFecha() == null) {
            throw new RuntimeException("El movimiento debe estar asociado a una fecha");
        }
        if (movimiento.getCategoria() == null) {
            throw new RuntimeException("El movimiento debe estar asociado a una categoría");
        }
        return movimientoRepository.save(movimiento);
    }

    public MovimientoFinanciero actualizarMovimiento(Long id, MovimientoFinanciero movimientoActualizado) {
        MovimientoFinanciero movimiento = obtenerMovimiento(id);
        if (movimientoActualizado.getMonto() != null) {
            movimiento.setMonto(movimientoActualizado.getMonto());
        }
        if (movimientoActualizado.getDescripcion() != null) {
            movimiento.setDescripcion(movimientoActualizado.getDescripcion());
        }
        if (movimientoActualizado.getFecha() != null) {
            movimiento.setFecha(movimientoActualizado.getFecha());
        }
        return movimientoRepository.save(movimiento);
    }

    public void eliminarMovimiento(Long id) {
        MovimientoFinanciero movimiento = obtenerMovimiento(id);
        movimientoRepository.delete(movimiento);
    }

    // ────────────────────────────────────
    // CATEGORÍAS FINANCIERAS
    // ────────────────────────────────────

    public List<CategoriaFinanciera> obtenerCategorias() {
        return categoriaRepository.findAll();
    }

    public CategoriaFinanciera obtenerCategoria(Byte id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }

    public List<CategoriaFinanciera> obtenerCategoriasPorTipo(String tipo) {
        return categoriaRepository.findByTipo(tipo);
    }

    public CategoriaFinanciera crearCategoria(CategoriaFinanciera categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la categoría no puede estar vacío");
        }
        if (categoria.getTipo() == null) {
            throw new RuntimeException("El tipo de categoría no puede estar vacío");
        }
        return categoriaRepository.save(categoria);
    }

    public CategoriaFinanciera actualizarCategoria(Byte id, CategoriaFinanciera categoriaActualizada) {
        CategoriaFinanciera categoria = obtenerCategoria(id);
        if (categoriaActualizada.getNombre() != null) {
            categoria.setNombre(categoriaActualizada.getNombre());
        }
        if (categoriaActualizada.getTipo() != null) {
            categoria.setTipo(categoriaActualizada.getTipo());
        }
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Byte id) {
        CategoriaFinanciera categoria = obtenerCategoria(id);
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }

    // ────────────────────────────────────
    // REPORTES Y RESÚMENES FINANCIEROS
    // ────────────────────────────────────

    public Object obtenerResumenFinanciero(java.time.LocalDate start, java.time.LocalDate end) {
        return movimientoRepository.findByFechaBetween(start, end);
    }

    public Map<String, Object> obtenerBalance(java.time.LocalDate start, java.time.LocalDate end) {
        Map<String, Object> balance = new HashMap<>();
        List<MovimientoFinanciero> movimientos = movimientoRepository.findByFechaBetween(start, end);
        
        java.math.BigDecimal ingresos = java.math.BigDecimal.ZERO;
        java.math.BigDecimal egresos = java.math.BigDecimal.ZERO;
        
        for (MovimientoFinanciero m : movimientos) {
            CategoriaFinanciera categoria = m.getCategoria();
            if (categoria != null) {
                if ("INGRESO".equals(categoria.getTipo())) {
                    ingresos = ingresos.add(m.getMonto());
                } else if ("EGRESO".equals(categoria.getTipo())) {
                    egresos = egresos.add(m.getMonto());
                }
            }
        }
        
        balance.put("ingresos", ingresos);
        balance.put("egresos", egresos);
        balance.put("balance", ingresos.subtract(egresos));
        return balance;
    }

    public Map<String, Object> obtenerOfrendaTotal(java.time.LocalDate start, java.time.LocalDate end) {
        Map<String, Object> ofrenda = new HashMap<>();
        List<MovimientoFinanciero> movimientos = movimientoRepository.findByFechaBetween(start, end);
        
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        for (MovimientoFinanciero m : movimientos) {
            CategoriaFinanciera categoria = m.getCategoria();
            if (categoria != null && "INGRESO".equals(categoria.getTipo())) {
                total = total.add(m.getMonto());
            }
        }
        
        ofrenda.put("total_ofrendas", total);
        return ofrenda;
    }

    // ────────────────────────────────────
    // OFRENDAS DESDE REPORTES
    // ────────────────────────────────────

    /**
     * Retorna ofrendas agrupadas por grupo familiar en el rango de fechas.
     * Opcionalmente filtra por sector (sectorId puede ser null para todos).
     */
    public List<Map<String, Object>> obtenerOfrendasPorGrupo(LocalDate desde, LocalDate hasta, Long sectorId) {
        List<Reporte> reportes;
        if (sectorId != null) {
            reportes = reporteRepository.findBySectorIdAndRangoDeFechas(sectorId, desde, hasta);
        } else {
            reportes = reporteRepository.findByRangoDeFechas(desde, hasta);
        }

        // Agrupar por grupo familiar
        Map<Long, Map<String, Object>> agrupado = new LinkedHashMap<>();
        for (Reporte r : reportes) {
            Long gId = r.getGrupoFamiliar().getId();
            agrupado.computeIfAbsent(gId, k -> {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("grupoId", gId);
                m.put("grupoNombre", r.getGrupoFamiliar().getNombre());
                m.put("sectorId", r.getGrupoFamiliar().getSector().getId());
                m.put("sectorNombre", r.getGrupoFamiliar().getSector().getNombre());
                m.put("liderNombre", r.getGrupoFamiliar().getLider() != null ? r.getGrupoFamiliar().getLider().getNombreCompleto() : "-");
                m.put("ofrendaSabado", java.math.BigDecimal.ZERO);
                m.put("ofrendaNinos", java.math.BigDecimal.ZERO);
                m.put("ofrendaMiercoles", java.math.BigDecimal.ZERO);
                m.put("totalOfrenda", java.math.BigDecimal.ZERO);
                return m;
            });
            Map<String, Object> entry = agrupado.get(gId);
            java.math.BigDecimal sab = toBD(r.getOfrendaSabado());
            java.math.BigDecimal nin = toBD(r.getOfrendaNinos());
            java.math.BigDecimal mie = toBD(r.getOfrendaMiercoles());
            entry.put("ofrendaSabado", ((java.math.BigDecimal) entry.get("ofrendaSabado")).add(sab));
            entry.put("ofrendaNinos", ((java.math.BigDecimal) entry.get("ofrendaNinos")).add(nin));
            entry.put("ofrendaMiercoles", ((java.math.BigDecimal) entry.get("ofrendaMiercoles")).add(mie));
            entry.put("totalOfrenda", ((java.math.BigDecimal) entry.get("totalOfrenda")).add(sab).add(nin).add(mie));
        }
        return new ArrayList<>(agrupado.values());
    }

    /**
     * Retorna ofrendas agrupadas por sector en el rango de fechas.
     */
    public List<Map<String, Object>> obtenerOfrendasPorSector(LocalDate desde, LocalDate hasta) {
        List<Reporte> reportes = reporteRepository.findByRangoDeFechas(desde, hasta);

        Map<Long, Map<String, Object>> agrupado = new LinkedHashMap<>();
        for (Reporte r : reportes) {
            Long sId = r.getGrupoFamiliar().getSector().getId();
            agrupado.computeIfAbsent(sId, k -> {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("sectorId", sId);
                m.put("sectorNombre", r.getGrupoFamiliar().getSector().getNombre());
                m.put("cantGrupos", 0);
                m.put("ofrendaSabado", java.math.BigDecimal.ZERO);
                m.put("ofrendaNinos", java.math.BigDecimal.ZERO);
                m.put("ofrendaMiercoles", java.math.BigDecimal.ZERO);
                m.put("totalOfrenda", java.math.BigDecimal.ZERO);
                return m;
            });
            Map<String, Object> entry = agrupado.get(sId);
            java.math.BigDecimal sab = toBD(r.getOfrendaSabado());
            java.math.BigDecimal nin = toBD(r.getOfrendaNinos());
            java.math.BigDecimal mie = toBD(r.getOfrendaMiercoles());
            entry.put("cantGrupos", (Integer) entry.get("cantGrupos") + 1);
            entry.put("ofrendaSabado", ((java.math.BigDecimal) entry.get("ofrendaSabado")).add(sab));
            entry.put("ofrendaNinos", ((java.math.BigDecimal) entry.get("ofrendaNinos")).add(nin));
            entry.put("ofrendaMiercoles", ((java.math.BigDecimal) entry.get("ofrendaMiercoles")).add(mie));
            entry.put("totalOfrenda", ((java.math.BigDecimal) entry.get("totalOfrenda")).add(sab).add(nin).add(mie));
        }
        return new ArrayList<>(agrupado.values());
    }

    private java.math.BigDecimal toBD(Object val) {
        if (val == null) return java.math.BigDecimal.ZERO;
        if (val instanceof java.math.BigDecimal) return (java.math.BigDecimal) val;
        return new java.math.BigDecimal(val.toString());
    }
}
