package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.DetalleProducto;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author GERSON
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleProductoDto {
    private Long idRecibo;
    private Long idProducto;
    private String nombreProducto;
    private Long cant;
    private BigDecimal monto;

    public static DetalleProductoDto toDto(DetalleProducto detalleProducto) {
        return DetalleProductoDto.builder()
                .idRecibo(detalleProducto.getIdRecibo().getId())
                .idProducto(detalleProducto.getIdProducto().getId())
                .cant(detalleProducto.getCant())
                .monto(detalleProducto.getMonto())
                .build();

    }

    public static DetalleProductoDto toDto2(DetalleProducto detalleProducto) {
        return DetalleProductoDto.builder()
                .idProducto(detalleProducto.getIdProducto().getId())
                .nombreProducto(detalleProducto.getIdProducto().getNombre())
                .cant(detalleProducto.getCant())
                .monto(detalleProducto.getMonto())
                .build();
    }
}
