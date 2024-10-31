package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "atencion_servicio")
public class AtencionServicio {
    @EmbeddedId
    private AtencionServicioId id;

    @MapsId("idServicio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio idServicio;

    @MapsId("idAtencion")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_atencion", nullable = false)
    private Atencion idAtencion;

}