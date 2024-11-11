package bo.com.jvargas.veterinaria.datos.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "categoria")
public class Categoria extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estante", nullable = false)
    private Estante idEstante;

}