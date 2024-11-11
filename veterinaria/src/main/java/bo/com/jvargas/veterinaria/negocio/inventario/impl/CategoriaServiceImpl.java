package bo.com.jvargas.veterinaria.negocio.inventario.impl;

import bo.com.jvargas.veterinaria.datos.model.Categoria;
import bo.com.jvargas.veterinaria.datos.model.Estante;
import bo.com.jvargas.veterinaria.datos.model.dto.CategoriaDto;
import bo.com.jvargas.veterinaria.datos.repository.inventario.CategoriaRepository;
import bo.com.jvargas.veterinaria.datos.repository.inventario.EstanteRepository;
import bo.com.jvargas.veterinaria.negocio.inventario.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Service("CategoriaService")
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final EstanteRepository estanteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDto> listarCategorias() {
        return categoriaRepository.findAllByDeletedFalse().stream()
                .map(CategoriaDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void guardarCategoria(CategoriaDto nuevaCategoria) {
        Categoria categoriaAGuardar = CategoriaDto.toEntity(nuevaCategoria);
        Estante estante = getEstante(nuevaCategoria.getIdEstante());

        categoriaAGuardar.setIdEstante(estante);
        categoriaRepository.save(categoriaAGuardar);
    }

    private Estante getEstante(Long id) {
        return estanteRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el estante con el ID " + id
                ));
    }

    @Override
    @Transactional
    public void actualizarCategoria(Long id, CategoriaDto nuevaCategoria) {
        if (!id.equals(nuevaCategoria.getId()))
            throw new IllegalArgumentException("IDs distintos");

        Categoria categoriaActual = getCategoria(id);
        actualizarDatos(categoriaActual, nuevaCategoria);
        categoriaRepository.save(categoriaActual);
    }

    private Categoria getCategoria(Long id) {
        return categoriaRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la categoria con ID " + id
                ));
    }

    private void actualizarDatos(Categoria categoriaActual,
                                 CategoriaDto nuevaCategoria) {
        if (nuevaCategoria.getIdEstante() == null) {
            categoriaActual.setNombre(nuevaCategoria.getNombre());
            return;
        }

        Estante estante = getEstante(nuevaCategoria.getIdEstante());
        categoriaActual.setNombre(nuevaCategoria.getNombre());
        categoriaActual.setIdEstante(estante);
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = getCategoria(id);
        categoria.setDeleted(true);
        categoriaRepository.save(categoria);
    }
}
