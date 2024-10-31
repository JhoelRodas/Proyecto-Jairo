package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.DetalleProducto;
import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.Recibo;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaDto implements Serializable {
    private Long idProducto;
    private Long idRecibo;
    private Long cant;
    private BigDecimal monto;

    public VentaDto(DetalleProducto detalleProducto) {
        this.idProducto = detalleProducto.getIdProducto()!= null ? detalleProducto.getIdProducto().getId(): null ;
        this.idRecibo = detalleProducto.getIdRecibo()!= null ? detalleProducto.getIdRecibo().getId(): null ;
        this.cant = detalleProducto.getCant();
        this.monto = detalleProducto.getMonto();
    }
}
