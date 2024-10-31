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
public class AtencionEnfermedadId implements java.io.Serializable {
    private static final long serialVersionUID = -1776447737885196268L;
    @Column(name = "id_atencion", nullable = false)
    private Integer idAtencion;

    @Column(name = "id_enfermedad", nullable = false)
    private Long idEnfermedad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AtencionEnfermedadId entity = (AtencionEnfermedadId) o;
        return Objects.equals(this.idEnfermedad, entity.idEnfermedad) &&
                Objects.equals(this.idAtencion, entity.idAtencion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEnfermedad, idAtencion);
    }

}