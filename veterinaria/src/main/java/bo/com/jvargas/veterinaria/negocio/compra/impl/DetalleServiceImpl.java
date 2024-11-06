package bo.com.jvargas.veterinaria.negocio.compra.impl;

import bo.com.jvargas.veterinaria.datos.model.Detalle;
import bo.com.jvargas.veterinaria.datos.model.DetalleId;
import bo.com.jvargas.veterinaria.datos.model.NotaCompra;
import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.dto.DetalleDto;
import bo.com.jvargas.veterinaria.datos.repository.compra.DetalleRepository;
import bo.com.jvargas.veterinaria.datos.repository.compra.NotaCompraRepository;
import bo.com.jvargas.veterinaria.datos.repository.inventario.ProductoRepository;
import bo.com.jvargas.veterinaria.negocio.compra.DetalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Service("DetalleService")
public class DetalleServiceImpl implements DetalleService {

    private final DetalleRepository detalleRepository;
    private final ProductoRepository productoRepository;
    private final NotaCompraRepository notaCompraRepository;

    @Transactional(readOnly = true)
    @Override
    public List<DetalleDto> listarDetalles() {
        return detalleRepository.findAll().stream()
                .map(DetalleDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<DetalleDto> insertarDetalle(DetalleDto detalleDto) {
        Optional<Producto> optionalProducto =
                productoRepository.findByIdAndDeletedFalse(detalleDto.getIdProducto());
        Optional<NotaCompra> optionalNotaCompra =
                notaCompraRepository.findByIdAndDeletedFalse(detalleDto.getIdNotaCompra());

        if (optionalProducto.isEmpty())
            throw new RuntimeException("Producto no encontrado");

        Detalle detalle = getDetalle(detalleDto, optionalNotaCompra, optionalProducto);

        Detalle detalleGuardado = detalleRepository.save(detalle);

        return Optional.of(DetalleDto.toDto(detalleGuardado));
    }

    private static Detalle getDetalle(DetalleDto detalleDto,
                                      Optional<NotaCompra> optionalNotaCompra,
                                      Optional<Producto> optionalProducto) {
        if (optionalNotaCompra.isEmpty())
            throw new RuntimeException("Nota de compra no encontrada");

        Producto producto = optionalProducto.get();
        NotaCompra notaCompra = optionalNotaCompra.get();

        if (detalleDto.getCantidad() > producto.getStock())
            throw new RuntimeException("La cantidad solicitada para el " +
                    "producto ID" + detalleDto.getIdProducto() +
                    " excede el stock disponible");

        // Crear el detalle y guardar en la base de datos (el trigger calculará el monto)
        Detalle detalle = new Detalle();
        detalle.setId(new DetalleId(detalleDto.getIdProducto(), detalleDto.getIdNotaCompra()));
        detalle.setIdProducto(producto);
        detalle.setIdNotaCompra(notaCompra);
        detalle.setCantidad(detalleDto.getCantidad());
        detalle.setMonto(detalleDto.getMonto());
        return detalle;
    }

    @Transactional
    @Override
    public List<DetalleDto> insertarDetalles(List<DetalleDto> detallesDto) {
        if (detallesDto == null || detallesDto.isEmpty())
            throw new RuntimeException("La lista de detalles no puede estar vacia");

        List<Detalle> detalles = insertar(detallesDto);

        List<Detalle> detallesGuardados = detalleRepository.saveAll(detalles);

        return detallesGuardados.stream()
                .map(DetalleDto::toDto)
                .collect(Collectors.toList());
    }

    private List<Detalle> insertar(List<DetalleDto> detallesDeto) {
        List<Detalle> detalles = new LinkedList<>();

        for (DetalleDto detalleDto : detallesDeto) {
            Optional<Producto> optionalProducto =
                    productoRepository.findByIdAndDeletedFalse(
                            detalleDto.getIdProducto());
            Optional<NotaCompra> optionalNotaCompra =
                    notaCompraRepository.findByIdAndDeletedFalse(
                            detalleDto.getIdNotaCompra());

            if (optionalProducto.isEmpty())
                throw new RuntimeException("Producto no encontrado");

            Detalle detalle = getDetalle(detalleDto, optionalNotaCompra,
                    optionalProducto);
            detalles.add(detalle);
        }

        return detalles;
    }

    @Transactional
    @Override
    public void eliminarDetalle(Long idProducto, Long idNotaCompra) {
        DetalleId detalleId = new DetalleId(idProducto, idNotaCompra);
        Optional<Detalle> oDetalle = detalleRepository.findById(detalleId);

        if (oDetalle.isEmpty())
            throw new RuntimeException("Detalle no encontrado");

        detalleRepository.delete(oDetalle.get());
    }
}
