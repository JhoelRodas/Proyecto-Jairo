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
@Table(name = "AUTH_ACTION")
public class AuthAction extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    @Comment("Identificador único auto generado del registro.")
    private Long id;

    @Column(name = "ACTION", nullable = false, length = 30)
    @Comment("Define el nombre de la acción permitida para el usuario.")
    private String action;

    @Column(name = "CODE", nullable = false, length = 30)
    @Comment("Define un código único para identificar la acción permitida para el usuario.")
    private String code;

    @Column(name = "BASE_ACTION", nullable = false)
    @Comment("Define si la acción es una configuración base del proyecto (No modificable).")
    private boolean baseAction;
}
