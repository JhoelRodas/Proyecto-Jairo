package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "nota_compra")
public class NotaCompra {
    @Id
@GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "monto_total", nullable = false, precision = 6, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor idProveedor;

}