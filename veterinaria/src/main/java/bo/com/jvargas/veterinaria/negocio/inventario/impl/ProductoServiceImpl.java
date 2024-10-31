package bo.com.jvargas.veterinaria.negocio.inventario.impl;

import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto;
import bo.com.jvargas.veterinaria.datos.repository.inventario.ProductoRepositoy;
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
    private final ProductoRepositoy productoRepositoy;

    @Override
    public List<ProductoDto> lista() {
        return productoRepositoy.listar();
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
        productoRepositoy.save(producto);
    }

    @Override
    public void actualizar(Long id, Producto producto) {
        Producto productoBuscado = productoRepositoy.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoBuscado.setNombre(producto.getNombre());
        productoBuscado.setPrecioUnitario(producto.getPrecioUnitario());
        productoBuscado.setStock(producto.getStock());
        productoBuscado.setDescripcion(producto.getDescripcion());
        productoBuscado.setIdCategoria(producto.getIdCategoria());

        productoRepositoy.save(productoBuscado);
    }

    @Override
    public void eliminar(Long id) {
        Producto productoBuscado = productoRepositoy.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoBuscado.setDeleted(true);
        productoRepositoy.save(productoBuscado);
    }

}
