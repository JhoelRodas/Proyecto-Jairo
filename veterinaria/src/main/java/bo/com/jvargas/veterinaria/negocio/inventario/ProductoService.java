package bo.com.jvargas.veterinaria.negocio.inventario;

import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto;

import java.util.List;

public interface ProductoService {
    List<ProductoDto> lista();

    void registar(ProductoDto productoDto);

    void actualizar(Long id, Producto producto);

    void eliminar(Long id);
}
