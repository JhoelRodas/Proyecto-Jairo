package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.Recibo;
import bo.com.jvargas.veterinaria.datos.model.dto.DetalleProductoDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReporteRequest;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ClienteRepository;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ReciboRepository;
import bo.com.jvargas.veterinaria.negocio.ventas.ClienteService;
import bo.com.jvargas.veterinaria.negocio.ventas.DetalleProductoService;
import bo.com.jvargas.veterinaria.negocio.ventas.ReciboService;
import bo.com.jvargas.veterinaria.utils.ExcelGeneratorRecibo;
import bo.com.jvargas.veterinaria.utils.PDFGeneratorRecibo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.Map;

@RequiredArgsConstructor
@Service("ReciboService")
public class ReciboServiceImpl implements ReciboService {

    private final ReciboRepository reciboRepository;
    private final ClienteRepository clienteRepository;
    private final DetalleProductoService detalleService;
    private final ClienteService clienteService;

    @Override
    @Transactional(readOnly = true)
    public List<ReciboDto> listarRecibos() {
        return reciboRepository.findAllByDeletedFalse().stream()
                .map(ReciboDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReciboDetalleDto verRecibo(Long id) {
        Recibo recibo = reciboRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el recibo con el ID " + id));

        Long idRecibo = recibo.getId();
        List<DetalleProductoDto> detalles = detalleService
                .listarDetalles(idRecibo);
        return ReciboDetalleDto.toDto(recibo, detalles);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReciboDto> listarRecibosReporte(Date from, Date to) {
        LocalDate fromLocal = LocalDate.ofEpochDay(from.getTime());
        LocalDate toLocal = LocalDate.ofEpochDay(to.getTime());
        return reciboRepository.listaFiltrada(fromLocal, toLocal).stream()
                .map(ReciboDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void guardarRecibo(ReciboDetalleDto nuevoRecibo) {
        Recibo reciboAGuardar = ReciboDetalleDto.toEntity(nuevoRecibo);

        reciboAGuardar.setFecha(LocalDate.now());
        Cliente cliente = getCliente(nuevoRecibo);
        reciboAGuardar.setIdCliente(cliente);

        Recibo reciboGuardado = reciboRepository.save(reciboAGuardar);
        Long idReciboGuardado = reciboGuardado.getId();
        List<DetalleProductoDto> detalles = nuevoRecibo.getDetalles();
        actualizarIdReciboEnLosDetalles(idReciboGuardado, detalles);
        detalleService.insertarDetallesProductos(detalles);
    }

    private Cliente getCliente(ReciboDetalleDto nuevoRecibo) {
        Optional<Cliente> o = clienteRepository.findByCiAndDeletedFalse(nuevoRecibo.getCi());

        if (o.isPresent())
            return o.get();

        Cliente cliente = new Cliente(nuevoRecibo.getCi(), nuevoRecibo.getExtension(),
                nuevoRecibo.getNombre());

        clienteService.registrar(cliente);
        Optional<Cliente> optional = clienteRepository.findByCiAndDeletedFalse(nuevoRecibo.getCi());

        return optional.orElseThrow();
    }

    private void actualizarIdReciboEnLosDetalles(
            Long idRecibo, List<DetalleProductoDto> detalles) {
        for (DetalleProductoDto detalle : detalles) {
            detalle.setIdRecibo(idRecibo);
        }
    }

    @Override
    @Transactional
    public void anularRecibo(Long id) {
        Optional<Recibo> o = reciboRepository.findByIdAndDeletedFalse(id);
        if (o.isEmpty())
            throw new RuntimeException("No existe un recibo con el ID " + id);

        Recibo recibo = o.get();
        recibo.setDeleted(true);
        reciboRepository.save(recibo);
    }

    /* agregado para reporte todo lo de abajo */
    @Override
    @Transactional(readOnly = true)
    public byte[] generarReporteRecibos(ReporteRequest reporteRequest) throws Exception {
        List<Recibo> recibos = aplicarFiltrosYOrden(reporteRequest);
        List<ReciboDto> recibosDto = recibos.stream()
                .map(ReciboDto::toDto)
                .collect(Collectors.toList());

        // Verificar el formato solicitado (Excel o PDF)
        if ("excel".equalsIgnoreCase(reporteRequest.getFormato())) {
            return ExcelGeneratorRecibo.generarReporteExcelRecibos(recibosDto, reporteRequest);
        } else if ("pdf".equalsIgnoreCase(reporteRequest.getFormato())) {
            return PDFGeneratorRecibo.generarReportePDFRecibos(recibosDto, reporteRequest);
        } else {
            throw new IllegalArgumentException("Formato no soportado");
        }
    }

    private List<Recibo> aplicarFiltrosYOrden(ReporteRequest reporteRequest) {
        Specification<Recibo> spec = buildSpecifications(reporteRequest.getFiltros());
        Sort sort = buildSort(reporteRequest.getOrden());
        return reciboRepository.findAll(spec, sort);
    }

    private Specification<Recibo> buildSpecifications(Map<String, Object> filtros) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

            if (filtros != null) {
                // Ejemplo para filtros de fecha
                if (filtros.containsKey("fecha_desde") && filtros.containsKey("fecha_hasta")) {
                    LocalDate fechaDesde = LocalDate.parse((String) filtros.get("fecha_desde"));
                    LocalDate fechaHasta = LocalDate.parse((String) filtros.get("fecha_hasta"));
                    predicates.add(criteriaBuilder.between(root.get("fecha"), fechaDesde, fechaHasta));
                } else if (filtros.containsKey("fecha")) {
                    LocalDate fecha = LocalDate.parse((String) filtros.get("fecha"));
                    predicates.add(criteriaBuilder.equal(root.get("fecha"), fecha));
                }

                // Filtro por nombre del cliente
                if (filtros.containsKey("nombre_cliente")) {
                    String nombreCliente = (String) filtros.get("nombre_cliente");
                    predicates
                            .add(criteriaBuilder.like(root.get("idCliente").get("nombre"), "%" + nombreCliente + "%"));
                }

                // Agregar más filtros según sea necesario
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Sort buildSort(ReporteRequest.Orden orden) {
        if (orden != null && orden.getColumna() != null && orden.getDireccion() != null) {
            Sort.Direction direction = orden.getDireccion().equalsIgnoreCase("Asc") ? Sort.Direction.ASC
                    : Sort.Direction.DESC;
            return Sort.by(direction, orden.getColumna());
        }
        return Sort.unsorted();
    }
}
