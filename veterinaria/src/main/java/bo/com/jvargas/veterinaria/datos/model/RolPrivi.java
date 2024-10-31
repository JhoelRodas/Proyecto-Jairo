package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "rol_privi")
public class RolPrivi {
    @EmbeddedId
    private RolPriviId id;

    @MapsId("idRol")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol idRol;

    @MapsId("idPrivi")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_privi", nullable = false)
    private Privilegio idPrivi;

}