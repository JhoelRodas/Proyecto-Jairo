package bo.com.jvargas.veterinaria.negocio.inventario.impl;

import bo.com.jvargas.veterinaria.datos.model.Estante;
import bo.com.jvargas.veterinaria.datos.model.dto.EstanteDto;
import bo.com.jvargas.veterinaria.datos.repository.inventario.EstanteRepository;
import bo.com.jvargas.veterinaria.negocio.inventario.EstanteService;
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
@Service("EstanteService")
public class EstanteServiceImpl implements EstanteService {

    private final EstanteRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<EstanteDto> listarEstantes() {
        return repository.findAllByDeletedFalse().stream()
                .map(EstanteDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void guardarEstante(EstanteDto estanteNuevo) {
        Estante estante = EstanteDto.toEntity(estanteNuevo);
        repository.save(estante);
    }

    @Override
    @Transactional
    public void actualizarEstante(Long id, EstanteDto estanteNuevo) {
        Estante estanteAct = getEstante(id);
        actualizarDatos(estanteAct, estanteNuevo);
        repository.save(estanteAct);
    }

    private Estante getEstante(Long id) {
        Optional<Estante> o = repository.findByIdAndDeletedFalse(id);
        if (o.isEmpty())
            throw new RuntimeException("No existe estante con id " + id);

        return o.get();
    }

    private void actualizarDatos(Estante estanteAct, EstanteDto estanteNuevo) {
        estanteAct.setNombre(estanteNuevo.getNombre());
        estanteAct.setDescripcion(estanteNuevo.getDescripcion());
    }

    @Override
    @Transactional
    public void eliminarEstante(Long id) {
        Estante estante = getEstante(id);
        estante.setDeleted(true);
        repository.save(estante);
    }
}
