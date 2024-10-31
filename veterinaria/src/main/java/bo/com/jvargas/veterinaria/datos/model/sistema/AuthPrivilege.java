package bo.com.jvargas.veterinaria.datos.model.sistema;

import bo.com.jvargas.veterinaria.datos.model.AuditableEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTH_PRIVILEGE")
public class AuthPrivilege extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    @Comment("Identificador único auto generado del registro.")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AUTH_ACTION", nullable = false)
    @Comment("Identificador único auto generado del registro.")
    private AuthAction idAuthAction;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AUTH_ROLE_RESOURCE", nullable = false)
    @Comment("Identificador único auto generado del registro.")
    private AuthRoleResource idAuthRoleResource;
}
