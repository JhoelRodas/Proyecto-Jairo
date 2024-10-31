package bo.com.jvargas.veterinaria.negocio.compra;

import bo.com.jvargas.veterinaria.datos.model.Proveedor;
import bo.com.jvargas.veterinaria.datos.model.dto.ProveedorDto;

import java.util.List;

public interface ProveedorService {
    List<ProveedorDto> listar();
    void registrar(Proveedor proveedor);
    void actualizar(Long id, Proveedor proveedor);
    void eliminar(Long id);
}
