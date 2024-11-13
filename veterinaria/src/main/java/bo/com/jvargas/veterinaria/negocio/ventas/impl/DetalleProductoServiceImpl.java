package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.DetalleProducto;
import bo.com.jvargas.veterinaria.datos.model.DetalleProductoId;
import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.Recibo;
import bo.com.jvargas.veterinaria.datos.model.dto.DetalleProductoDto;
import bo.com.jvargas.veterinaria.datos.repository.inventario.ProductoRepository;
import bo.com.jvargas.veterinaria.datos.repository.ventas.DetalleProductoRepository;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ReciboRepository;
import bo.com.jvargas.veterinaria.negocio.ventas.DetalleProductoService;
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
@Service("DetalleProductoService")
public class DetalleProductoServiceImpl implements DetalleProductoService {

    private final DetalleProductoRepository detalleProductoRepository;
    private final ProductoRepository productoRepository;
    private final ReciboRepository reciboRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleProductoDto> listarDetalleProductos() {
        return detalleProductoRepository.findAll().stream()
                .map(DetalleProductoDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleProductoDto> listarDetalles(Long idRecibo) {
        return detalleProductoRepository.findAllByIdRecibo_Id(idRecibo).stream()
                .map(DetalleProductoDto::toDto2)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void insertarDetalleProducto(DetalleProductoDto nuevoDetalle) {
        Producto producto = getProducto(nuevoDetalle);
        Recibo recibo = getRecibo(nuevoDetalle);
        DetalleProducto detalleProducto = getDetalleProducto(nuevoDetalle,
                producto, recibo);
        detalleProductoRepository.save(detalleProducto);
    }

    private Producto getProducto(DetalleProductoDto nuevoDetalle) {
        Long idProducto = nuevoDetalle.getIdProducto();
        Optional<Producto> o =
                productoRepository.findByIdAndDeletedFalse(idProducto);

        if (o.isEmpty())
            throw new RuntimeException("Producto con ID " + idProducto +
                    " no encontrado");

        return o.get();
    }

    private Recibo getRecibo(DetalleProductoDto nuevoDetalle) {
        Long idRecibo = nuevoDetalle.getIdRecibo();
        Optional<Recibo> o = reciboRepository.findByIdAndDeletedFalse(idRecibo);

        if (o.isEmpty())
            throw new RuntimeException("Recibo con ID " + idRecibo +
                    " no encontrado");

        return o.get();
    }

    private DetalleProducto getDetalleProducto(
            DetalleProductoDto nuevoDetalle, Producto producto, Recibo recibo) {
        if (nuevoDetalle.getCant() > producto.getStock()) {
            throw new RuntimeException("La cantidad solicitada para el " +
                    "producto ID" + producto.getId() +
                    " excede el stock disponible");
        }

        DetalleProducto detalleProducto = new DetalleProducto();
        DetalleProductoId detalleProductoId = new DetalleProductoId(
                recibo.getId(), producto.getId());
        detalleProducto.setId(detalleProductoId);
        detalleProducto.setIdRecibo(recibo);
        detalleProducto.setIdProducto(producto);
        detalleProducto.setCant(nuevoDetalle.getCant());
        detalleProducto.setMonto(nuevoDetalle.getMonto());
        return detalleProducto;
    }

    @Override
    @Transactional
    public void insertarDetallesProductos(List<DetalleProductoDto> nuevosDetalles) {
        if (nuevosDetalles == null || nuevosDetalles.isEmpty())
            throw new RuntimeException("La lista de detalles de productos" +
                    " no puede estar vacia");

        List<DetalleProducto> detalleProductos = ajustarDetalles(nuevosDetalles);
        detalleProductoRepository.saveAll(detalleProductos);
    }

    private List<DetalleProducto> ajustarDetalles(
            List<DetalleProductoDto> nuevosDetalles) {
        List<DetalleProducto> detalleProductos = new LinkedList<>();

        for (DetalleProductoDto detalleDto : nuevosDetalles) {
            Producto producto = getProducto(detalleDto);
            Recibo recibo = getRecibo(detalleDto);
            DetalleProducto detalleProducto = getDetalleProducto(detalleDto,
                    producto, recibo);

            detalleProductos.add(detalleProducto);
        }

        return detalleProductos;
    }

    @Override
    public void elimiarDetalle(Long idRecibo, Long idProducto) {
        DetalleProductoId detalleProductoId = new DetalleProductoId(
                idRecibo, idProducto);

        Optional<DetalleProducto> o =
                detalleProductoRepository.findById(detalleProductoId);

        if (o.isEmpty())
            throw new RuntimeException("Detalle no encontrado");

        detalleProductoRepository.delete(o.get());
    }
}
