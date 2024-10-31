package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
@GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "precio", nullable = false, precision = 5, scale = 2)
    private BigDecimal precio;

}