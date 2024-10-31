package bo.com.jvargas.veterinaria.datos.model.sistema;

import bo.com.jvargas.veterinaria.datos.model.AuditableEntity;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.ResourceType;
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
@Table(name = "AUTH_RESOURCE")
public class AuthResource extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    @Comment("Identificador único auto generado del registro.")
    private Long id;

    @Basic
    @Column(name = "NAME", length = 100, nullable = false)
    @Comment("Define el nombre del recurso disponible para el usuario.")
    private String name;

    @Column(name = "DESCRIPTION", length = 100)
    @Comment("Define una descripción más detallada del recurso.")
    private String description;

    @Basic
    @Column(name = "URL", nullable = false)
    @Comment("Define la url usada para disponer de ese recurso en la aplicación.")
    private String url;

    @Basic
    @Column(name = "ICON", length = 50, nullable = false)
    @Comment("Define el icono utilizado para identificar el recurso en la barra de navegación de la aplicación.")
    private String icon;

    @Basic
    @Column(name = "MENU_POSITION", nullable = false)
    @Comment("Define el orden en el que mostrara el recurso.")
    private Integer menuPosition;

    @Column(name = "RESOURCE_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Define si el recurso esta activo.")
    private EntityState resourceStatus;

    @Column(name = "FRONTEND_CODE", length = 50)
    @Comment("Define un identificador único para la aplicación web.")
    private String frontendCode;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    @Comment("Define el tipo de recurso.")
    private ResourceType type;

    @Column(name = "BADGE", length = 2)
    @Comment("Define una insignia para el recurso.")
    private String badge;

    @Column(name = "BADGE_COLOR")
    @Comment("Define el color de la insignia.")
    private String badgeColor;

    @Column(name = "CUSTOM_CLASS")
    @Comment("Define una clase personalizada para el recurso.")
    private String customClass;

    @JoinColumn(name = "ID_AUTH_RECURSO_PADRE", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("Identificador único auto generado del registro.")
    private AuthResource idAuthResourceParent;

}
