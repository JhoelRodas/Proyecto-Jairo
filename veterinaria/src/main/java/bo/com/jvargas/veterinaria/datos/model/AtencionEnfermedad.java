package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "atencion_enfermedad")
public class AtencionEnfermedad {
    @EmbeddedId
    private AtencionEnfermedadId id;

    @MapsId("idAtencion")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_atencion", nullable = false)
    private Atencion idAtencion;

    @MapsId("idEnfermedad")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_enfermedad", nullable = false)
    private Enfermedad idEnfermedad;

}