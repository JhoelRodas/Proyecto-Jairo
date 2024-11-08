package bo.com.jvargas.veterinaria.datos.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "detalle_producto")
public class DetalleProducto {
    @EmbeddedId
    private DetalleProductoId id;

    @MapsId("idRecibo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_recibo", nullable = false)
    private Recibo idRecibo;

    @MapsId("idProducto")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto idProducto;

    @Column(name = "cant", nullable = false)
    private Long cant;

    @Column(name = "monto", nullable = false, precision = 5, scale = 2)
    private BigDecimal monto;

}