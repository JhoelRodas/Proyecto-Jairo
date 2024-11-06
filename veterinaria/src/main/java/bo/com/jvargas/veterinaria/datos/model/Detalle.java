package bo.com.jvargas.veterinaria.datos.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "detalle")
public class Detalle {
    @EmbeddedId
    private DetalleId id;

    @MapsId("idProducto")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto idProducto;

    @MapsId("idNotaCompra")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_nota_compra", nullable = false)
    private NotaCompra idNotaCompra;

    @Column(name = "cantidad", nullable = false)
    private Short cantidad;

    @Column(name = "monto", nullable = false, precision = 6, scale = 2)
    private BigDecimal monto;

}