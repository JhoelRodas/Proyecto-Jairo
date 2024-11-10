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
public class
Mascota extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "especie", nullable = false, length = 10)
    private String especie;

    @Column(name = "raza", nullable = false, length = 25)
    private String raza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ci_cliente",referencedColumnName = "id")
    private Cliente ciCliente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_historial")
    private HistorialClinico idHistorial;
}