package bo.com.jvargas.veterinaria.negocio.inventario.impl;

import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto;
import bo.com.jvargas.veterinaria.datos.repository.inventario.ProductoRepository;
import bo.com.jvargas.veterinaria.negocio.inventario.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service("ProductoService")
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoDto> lista() {
        return productoRepository.listar();
    }

    @Override
    @Transactional
    public void registar(ProductoDto productoDto) {
        Producto producto = new Producto();
        producto.setId(productoDto.getId());
        producto.setNombre(productoDto.getNombre());
        producto.setPrecioUnitario(productoDto.getPrecioUnitario());
        producto.setStock(productoDto.getStock());
        producto.setDescripcion(productoDto.getDescripcion());
        productoRepository.save(producto);
    }

    @Override
    public void actualizar(Long id, Producto producto) {
        Producto productoBuscado = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoBuscado.setNombre(producto.getNombre());
        productoBuscado.setPrecioUnitario(producto.getPrecioUnitario());
        productoBuscado.setStock(producto.getStock());
        productoBuscado.setDescripcion(producto.getDescripcion());
        productoBuscado.setIdCategoria(producto.getIdCategoria());

        productoRepository.save(productoBuscado);
    }

    @Override
    public void eliminar(Long id) {
        Producto productoBuscado = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoBuscado.setDeleted(true);
        productoRepository.save(productoBuscado);
    }

}
