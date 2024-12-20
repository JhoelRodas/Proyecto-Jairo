package bo.com.jvargas.veterinaria.datos.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "proveedor")
public class Proveedor extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "telefono", nullable = false)
    private Integer telefono;

    @Column(name = "correo", nullable = false, length = 60)
    private String correo;

    @Column(name = "direccion", nullable = false, length = 60)
    private String direccion;

    @Column(name = "encargado", nullable = false, length = 50)
    private String encargado;

}