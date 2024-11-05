package bo.com.jvargas.veterinaria.negocio.compra.impl;

import bo.com.jvargas.veterinaria.datos.model.NotaCompra;
import bo.com.jvargas.veterinaria.datos.model.Proveedor;
import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDto;
import bo.com.jvargas.veterinaria.datos.repository.compra.NotaCompraRepository;
import bo.com.jvargas.veterinaria.datos.repository.compra.ProveedorRepository;
import bo.com.jvargas.veterinaria.negocio.compra.NotaCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    @Override
    public List<NotaCompraDto> listar() {
        return notaCompraRepository.findAllByDeletedFalse().stream()
                .map((NotaCompraDto::toDto))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public NotaCompraDto guardar(NotaCompraDto nuevaNotaCompra) {
        NotaCompra notaAGuardar = NotaCompraDto.toEntity(nuevaNotaCompra);
        Optional<Proveedor> optionalProveedor = buscarProveedor(nuevaNotaCompra);

        if (optionalProveedor.isEmpty())
            return null;

        Proveedor proveedor = optionalProveedor.get();
        notaAGuardar.setIdProveedor(proveedor);

        NotaCompra notaCompraGuardada = notaCompraRepository.save(notaAGuardar);
        return NotaCompraDto.toDto(notaCompraGuardada);
    }

    private Optional<Proveedor> buscarProveedor(NotaCompraDto notaCompra) {
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
