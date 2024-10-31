package bo.com.jvargas.veterinaria.datos.model.sistema;

import bo.com.jvargas.veterinaria.datos.model.AuditableEntity;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by :cesaraugusto
 * Date       :30-10-18
 * Project    onboarding
 * Package    :bo.com.mc4.onboarding.model
 */
@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTH_ROLE")
public class AuthRole extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    @Comment("Identificador único auto generado del registro.")
    private Long id;

    @Basic
    @Column(name = "NAME", nullable = false, length = 35)
    @Comment("Define el nombre del rol para el usuario.")
    private String name;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false)
    @Comment("Define una descripción mas detallada del rol.")
    private String description;

    @Column(name = "BASE_ROLE", nullable = false)
    @Comment("Define si el rol es una configuración base del proyecto (No modificable).")
    private boolean baseRole = false;


    @Column(name = "ROLE_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Define el estado del rol.")
    private EntityState roleStatus;
}
