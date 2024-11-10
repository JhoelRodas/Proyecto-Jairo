package bo.com.jvargas.veterinaria.datos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AtencionServicioId implements java.io.Serializable {
    private static final long serialVersionUID = 3400744806985688907L;
    @Column(name = "id_servicio", nullable = false)
    private Long idServicio;

    @Column(name = "id_atencion", nullable = false)
    private Long idAtencion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AtencionServicioId entity = (AtencionServicioId) o;
        return Objects.equals(this.idServicio, entity.idServicio) &&
                Objects.equals(this.idAtencion, entity.idAtencion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idServicio, idAtencion);
    }

}