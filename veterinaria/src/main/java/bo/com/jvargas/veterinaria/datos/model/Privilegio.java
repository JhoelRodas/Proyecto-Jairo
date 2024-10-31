package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "privilegio")
public class Privilegio {
    @Id
@GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

}