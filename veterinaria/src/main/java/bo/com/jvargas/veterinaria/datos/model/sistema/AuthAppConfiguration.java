package bo.com.jvargas.veterinaria.datos.model.sistema;

import bo.com.jvargas.veterinaria.datos.model.AuditableEntity;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.ApplicationType;
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
@Table(name = "AUTH_APP_CONFIGURATION")
public class AuthAppConfiguration extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    @Comment("Identificador único auto generado del registro.")
    private Long id;

    @Column(name = "APPLICATION_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Define el tipo de aplicación.")
    private ApplicationType applicationType;

    @Lob
    @Column(name = "JSON_CONFIG", nullable = false)
    @Comment("Define el tema de la aplicación, posición de componentes y las partes visibles.")
    private String jsonConfig;

    @JoinColumn(name = "ID_AUTH_USER", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("Identificador único auto generado del registro.")
    private AuthUser idAuthUser;
}
