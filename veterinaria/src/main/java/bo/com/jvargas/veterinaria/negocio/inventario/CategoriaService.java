package bo.com.jvargas.veterinaria.negocio.inventario;

import bo.com.jvargas.veterinaria.datos.model.dto.CategoriaDto;

import java.util.List;

/**
 * @author GERSON
 */

public interface CategoriaService {
    List<CategoriaDto> listarCategorias();

    void guardarCategoria(CategoriaDto nuevaCategoria);

    void actualizarCategoria(Long id, CategoriaDto nuevaCategoria);

    void eliminarCategoria(Long id);
}
