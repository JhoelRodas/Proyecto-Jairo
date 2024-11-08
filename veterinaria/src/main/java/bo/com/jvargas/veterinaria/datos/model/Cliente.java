package bo.com.jvargas.veterinaria.datos.model;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ci", nullable = false, length = 250)
    private String ci;

    @Column(name = "extension", nullable = false, length = 100)
    private String extension;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", nullable = true, length = 20)
    private String telefono;

    @Column(name = "correo", nullable = true, length = 60)
    private String correo;

    @Column(name = "direccion", nullable = true, length = 60)
    private String direccion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_auth_user", nullable = false, referencedColumnName = "id")
    private AuthUser idAuthUser;

    public Cliente(String ci, String extension, String nombre) {
        this.ci = ci;
        this.extension = extension;
        this.nombre = nombre;
    }
}