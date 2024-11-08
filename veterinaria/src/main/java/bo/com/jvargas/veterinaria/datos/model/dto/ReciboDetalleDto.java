package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Recibo;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author GERSON
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReciboDetalleDto {
    private Long id;
    private LocalDate fecha;
    private BigDecimal montoTotal;
    private String metodoPago;
    private String ci;
    private String extension;
    private String nombre;
    private List<DetalleProductoDto> detalles;

    public static Recibo toEntity(ReciboDetalleDto reciboDetalleDto) {
        return Recibo.builder()
                .id(reciboDetalleDto.getId())
                .fecha(reciboDetalleDto.getFecha())
                .montoTotal(reciboDetalleDto.getMontoTotal())
                .metodoPago(reciboDetalleDto.getMetodoPago())
                .idCliente(null)
                .build();
    }
}
