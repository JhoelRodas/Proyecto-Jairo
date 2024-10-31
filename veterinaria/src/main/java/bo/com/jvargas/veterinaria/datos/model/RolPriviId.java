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
public class RolPriviId implements java.io.Serializable {
    private static final long serialVersionUID = 4168781511388419043L;
    @Column(name = "id_rol", nullable = false)
    private Long idRol;

    @Column(name = "id_privi", nullable = false)
    private Long idPrivi;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RolPriviId entity = (RolPriviId) o;
        return Objects.equals(this.idPrivi, entity.idPrivi) &&
                Objects.equals(this.idRol, entity.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrivi, idRol);
    }

}