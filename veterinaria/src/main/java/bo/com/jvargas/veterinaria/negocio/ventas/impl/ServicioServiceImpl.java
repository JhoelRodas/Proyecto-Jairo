package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.Servicio;
import bo.com.jvargas.veterinaria.datos.model.dto.ServicioDto;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ServicioRepository;
import bo.com.jvargas.veterinaria.negocio.ventas.ServicioService;
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
@Service("ServicioService")
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<ServicioDto> listarServicios() {
        return repository.findAllByDeletedFalse().stream()
                .map(ServicioDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<ServicioDto> crearServicio(ServicioDto servicioNuevo) {
        Servicio servicio = ServicioDto.toEntity(servicioNuevo);
        Servicio servicioCreado = repository.save(servicio);
        return Optional.of(ServicioDto.toDto(servicioCreado));
    }

    @Override
    @Transactional
    public Optional<ServicioDto> actualizarServicio(Long id,
                                                 ServicioDto servicioNuevo) {
        Optional<Servicio> o = repository.findByIdAndDeletedFalse(id);

        if (o.isEmpty())
            throw new RuntimeException("Servicio no encontrado");

        Servicio servicioAct = o.get();
        actualizarDatos(servicioAct, servicioNuevo);

        Servicio servicioActualizado = repository.save(servicioAct);
        return Optional.of(ServicioDto.toDto(servicioActualizado));
    }

    private void actualizarDatos(Servicio servicioAct, ServicioDto servicioNuevo) {
        servicioAct.setNombre(servicioNuevo.getNombre());
        servicioAct.setPrecio(servicioNuevo.getPrecio());
        servicioAct.setDescripcion(servicioNuevo.getDescripcion());
    }

    @Override
    @Transactional
    public void elimiarServicio(Long id) {
        Optional<Servicio> o = repository.findByIdAndDeletedFalse(id);

        if (o.isEmpty())
            throw new RuntimeException("Servicio no encontrado");

        Servicio servicio = o.get();
        servicio.setDeleted(true);
        repository.save(servicio);
    }
}
