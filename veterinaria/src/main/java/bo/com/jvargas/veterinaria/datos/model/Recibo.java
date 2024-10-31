package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "recibo")
public class Recibo extends AuditableEntity implements Serializable {
    @Id
@GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "monto_total", nullable = false, precision = 5, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "metodo_pago", nullable = false, length = 8)
    private String metodoPago;

}