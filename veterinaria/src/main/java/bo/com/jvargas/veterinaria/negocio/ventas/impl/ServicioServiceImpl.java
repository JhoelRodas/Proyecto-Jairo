package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.Servicio;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ServicioRepository;
import bo.com.jvargas.veterinaria.negocio.ventas.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Service("ServicioService")
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> listarServicios() {
        return repository.findAllByDeletedFalse();
    }

    @Override
    @Transactional
    public Optional<Servicio> crearServicio(Servicio servicioNuevo) {
        Servicio servicioCreado = repository.save(servicioNuevo);
        return Optional.of(servicioCreado);
    }

    @Override
    @Transactional
    public Optional<Servicio> actualizarServicio(Long id,
                                                 Servicio servicioNuevo) {
        Optional<Servicio> o = repository.findByIdAndDeletedFalse(id);

        if (o.isEmpty())
            throw new RuntimeException("Servicio no encontrado");

        Servicio servicioAct = o.get();
        actualizarDatos(servicioAct, servicioNuevo);

        Servicio servicioActualizado = repository.save(servicioAct);
        return Optional.of(servicioActualizado);
    }

    private void actualizarDatos(Servicio servicioAct, Servicio servicioNuevo) {
        servicioAct.setNombre(servicioNuevo.getNombre());
        servicioAct.setPrecio(servicioNuevo.getPrecio());
    }

    @Override
    @Transactional
    public void elimiarServicio(Long id) {
        Optional<Servicio> o = repository.findByIdAndDeletedFalse(id);

        if (o.isEmpty())
            throw new RuntimeException("Servicio no encontrado");

        Servicio servicio = o.get();
        repository.delete(servicio);
    }
}
