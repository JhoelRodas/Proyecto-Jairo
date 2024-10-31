package bo.com.jvargas.veterinaria.datos.model.sistema;

import bo.com.jvargas.veterinaria.datos.model.AuditableEntity;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.NivelLog;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BITACORA")
public class Bitacora extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    @Comment("Identificador único auto generado del registro.")
    private Long id;

    @Basic
    @Column(name = "IP", nullable = false)
    @Comment("Define la direccion ip.")
    private String ip;

    @Column(name = "TIPO_PROCESO", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Define el tipo de proceso de log para el registro.")
    private TipoProceso tipoProceso;

    @Column(name = "NIVEL_LOG", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Define el tipo de log para el registro.")
    private NivelLog nivelLog;

    @Lob
    @Type(type = "text")
    @Column(name = "LOG", nullable = false)
    @Comment("Define el texto log del registro.")
    private String log;


}
