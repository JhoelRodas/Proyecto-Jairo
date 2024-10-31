package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ControlVacunaId implements java.io.Serializable {
    private static final long serialVersionUID = 2556323115335093901L;
    @Column(name = "id_vacuna", nullable = false)
    private Long idVacuna;

    @Column(name = "id_atencion", nullable = false)
    private Integer idAtencion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ControlVacunaId entity = (ControlVacunaId) o;
        return Objects.equals(this.idVacuna, entity.idVacuna) &&
                Objects.equals(this.idAtencion, entity.idAtencion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVacuna, idAtencion);
    }

}