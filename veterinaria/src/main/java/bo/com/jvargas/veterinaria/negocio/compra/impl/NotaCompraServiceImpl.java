package bo.com.jvargas.veterinaria.negocio.compra.impl;

import bo.com.jvargas.veterinaria.datos.model.NotaCompra;
import bo.com.jvargas.veterinaria.datos.model.Proveedor;
import bo.com.jvargas.veterinaria.datos.model.dto.DetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDto;
import bo.com.jvargas.veterinaria.datos.repository.compra.NotaCompraRepository;
import bo.com.jvargas.veterinaria.datos.repository.compra.ProveedorRepository;
import bo.com.jvargas.veterinaria.negocio.compra.DetalleService;
import bo.com.jvargas.veterinaria.negocio.compra.NotaCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Service("NotaCompraService")
public class NotaCompraServiceImpl implements NotaCompraService {

    private final NotaCompraRepository notaCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final DetalleService detalleService;

    @Transactional(readOnly = true)
    @Override
    public List<NotaCompraDto> listar() {
        return notaCompraRepository.findAllByDeletedFalse().stream()
                .map((NotaCompraDto::toDto))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public NotaCompraDto verNotaDeCompra(Long id) {
        NotaCompra notaCompra = getNotaCompra(id);
        Long idNotaCompra = notaCompra.getId();
        List<DetalleDto> listaDeDetalles = detalleService
                .getDetalles(idNotaCompra);

        return NotaCompraDto.toDto2(notaCompra, listaDeDetalles);
    }

    private NotaCompra getNotaCompra(Long id) {
        return notaCompraRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la nota de compra con el ID " + id
                ));
    }

    @Transactional
    @Override
    public NotaCompraDto guardar(NotaCompraDetalleDto nuevaNotaCompraDetalle) {
        NotaCompra notaAGuardar = NotaCompraDetalleDto.toEntity(nuevaNotaCompraDetalle);
        Optional<Proveedor> optionalProveedor = buscarProveedor(nuevaNotaCompraDetalle);

        if (optionalProveedor.isEmpty())
            return null;

        Proveedor proveedor = optionalProveedor.get();
        notaAGuardar.setIdProveedor(proveedor);
        notaAGuardar.setFecha(LocalDate.now());

        NotaCompra notaCompraGuardada = notaCompraRepository.save(notaAGuardar);

        Long idNotaCompraGuardada = notaCompraGuardada.getId();
        List<DetalleDto> detalles = nuevaNotaCompraDetalle.getDetalles();
        actualizarIdNotaEnDetalle(detalles, idNotaCompraGuardada);

        detalleService.insertarDetalles(detalles);

        return NotaCompraDto.toDto(notaCompraGuardada);
    }

    private void actualizarIdNotaEnDetalle(List<DetalleDto> detalles,
                                           Long idNotaCompra) {
        for (DetalleDto detalleDto : detalles) {
            detalleDto.setIdNotaCompra(idNotaCompra);
        }
    }

    private Optional<Proveedor> buscarProveedor(NotaCompraDetalleDto notaCompra) {
        String nombreProveedor = notaCompra.getNombreProveedor();
        return proveedorRepository.findByNombreAndDeletedFalse(nombreProveedor);
    }

    @Transactional
    @Override
    public Optional<NotaCompraDto> actualizar(
            Long id,NotaCompraDto notaCompraAActualizar) {
        Optional<NotaCompra> o = notaCompraRepository.findByIdAndDeletedFalse(id);

        if (o.isEmpty())
            return Optional.empty();

        NotaCompra notaCompra = o.get();
        actualizarDatosNotaCompra(notaCompra, notaCompraAActualizar);

        NotaCompra notaCompraActualizada = notaCompraRepository.save(notaCompra);

        return Optional.of(NotaCompraDto.toDto(notaCompraActualizada));
    }

    private void actualizarDatosNotaCompra(NotaCompra notaCompra,
                                           NotaCompraDto notaCompraAActualizar) {
        // Actualizar los campos básicos
        notaCompra.setMontoTotal(notaCompraAActualizar.getMontoTotal());
        notaCompra.setFecha(notaCompraAActualizar.getFecha());
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        NotaCompra notaCompra = notaCompraRepository.
                findByIdAndDeletedFalse(id).orElseThrow(()->new RuntimeException(
                        "Nota de compra no encontrada o eliminada"));

        notaCompra.setDeleted(true);

        notaCompraRepository.save(notaCompra);
    }
}
