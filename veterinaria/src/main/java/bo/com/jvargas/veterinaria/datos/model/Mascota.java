package bo.com.jvargas.veterinaria.datos.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mascota")
public class Mascota extends AuditableEntity implements Serializable {
    @Id
@GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 15)
    private String nombre;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "sexo", nullable = false, length = 15)
    private String sexo;

    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ci_cliente",referencedColumnName = "id")
    private Cliente ciCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especie")
    private Especie idEspecie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_raza")
    private Raza idRaza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial")
    private HistorialClinico idHistorial;

}