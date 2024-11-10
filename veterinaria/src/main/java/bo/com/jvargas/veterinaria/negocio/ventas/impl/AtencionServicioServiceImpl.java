package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.Atencion;
import bo.com.jvargas.veterinaria.datos.model.AtencionServicio;
import bo.com.jvargas.veterinaria.datos.model.AtencionServicioId;
import bo.com.jvargas.veterinaria.datos.model.Servicio;
import bo.com.jvargas.veterinaria.datos.model.dto.AtencionServicioDto;
import bo.com.jvargas.veterinaria.datos.repository.ventas.AtencionRepository;
import bo.com.jvargas.veterinaria.datos.repository.ventas.AtencionServicioRespository;
import bo.com.jvargas.veterinaria.datos.repository.ventas.ServicioRepository;
import bo.com.jvargas.veterinaria.negocio.ventas.AtencionServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Service("AtencionServicioService")
public class AtencionServicioServiceImpl implements AtencionServicioService {

    private final AtencionServicioRespository atencionServicioRespository;
    private final ServicioRepository servicioRepository;
    private final AtencionRepository atencionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AtencionServicio> listar() {
        return atencionServicioRespository.findAll();
    }

    @Override
    @Transactional
    public void insertarServicios(List<AtencionServicioDto> nuevosServicios) {
        if (nuevosServicios == null || nuevosServicios.isEmpty())
            throw new IllegalArgumentException("La lista de servicios no" +
                    " no debe estar vacia");

        List<AtencionServicio> atencionServicios = ajustarServicios(nuevosServicios);
        atencionServicioRespository.saveAll(atencionServicios);
    }

    private List<AtencionServicio> ajustarServicios(
            List<AtencionServicioDto> nuevosServicios) {
        List<AtencionServicio> list = new LinkedList<>();

        for (AtencionServicioDto atencionServicio : nuevosServicios) {
            Servicio servicio = getServicio(atencionServicio);
            Atencion atencion = getAtencion(atencionServicio);
            AtencionServicio atencionServicio1 = getAtencionServicio(
                    servicio, atencion);
            list.add(atencionServicio1);
        }

        return list;
    }

    private Servicio getServicio(AtencionServicioDto atencion) {
        Long idServicio = atencion.getIdServicio();
        Optional<Servicio> o = servicioRepository
                .findByIdAndDeletedFalse(idServicio);

        if (o.isEmpty())
            throw new IllegalArgumentException("El servicio con el ID " +
                    idServicio + " no existe");

        return o.get();
    }

    private Atencion getAtencion(AtencionServicioDto atencion) {
        Long idAtencion = atencion.getIdAtencion();
        Optional<Atencion> o = atencionRepository
                .findByIdAndDeletedFalse(idAtencion);

        if (o.isEmpty())
            throw new IllegalArgumentException("La atencion con el ID " +
                    idAtencion + " no existe");

        return o.get();
    }

    private AtencionServicio getAtencionServicio(
            Servicio servicio, Atencion atencion) {
        AtencionServicio atencionServicio = new AtencionServicio();
        atencionServicio.setId(new AtencionServicioId(
                servicio.getId(), atencion.getId()));
        atencionServicio.setIdAtencion(atencion);
        atencionServicio.setIdServicio(servicio);
        return atencionServicio;
    }

    @Override
    @Transactional
    public void eliminarServicio(Long idAtencion, Long idServicio) {
        AtencionServicioId atencionServicioId = new AtencionServicioId(
                idServicio, idAtencion);

        Optional<AtencionServicio> o =
                atencionServicioRespository.findById(atencionServicioId);

        if (o.isEmpty())
            throw new IllegalArgumentException("Atencion y Servicio no encontrado");

        atencionServicioRespository.delete(o.get());
    }
}
