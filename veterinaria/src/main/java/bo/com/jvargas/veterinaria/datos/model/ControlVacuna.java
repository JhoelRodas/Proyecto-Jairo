package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "control_vacuna")
public class ControlVacuna {
    @EmbeddedId
    private ControlVacunaId id;

    @MapsId("idVacuna")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private Vacuna idVacuna;

    @MapsId("idAtencion")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_atencion", nullable = false)
    private Atencion idAtencion;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

}