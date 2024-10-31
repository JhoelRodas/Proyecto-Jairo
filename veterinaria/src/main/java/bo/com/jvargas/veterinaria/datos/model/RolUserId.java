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
public class RolUserId implements java.io.Serializable {
    private static final long serialVersionUID = 1190116599915680998L;
    @Column(name = "id_rol", nullable = false)
    private Long idRol;

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RolUserId entity = (RolUserId) o;
        return Objects.equals(this.idUser, entity.idUser) &&
                Objects.equals(this.idRol, entity.idRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idRol);
    }

}