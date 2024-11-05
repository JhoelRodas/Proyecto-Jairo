package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.NotaCompra;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author GERSON
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaCompraDto {
    private Long id;
    private BigDecimal montoTotal;
    private LocalDate fecha;
    private String nombreProveedor;

    public static NotaCompraDto toDto(NotaCompra notaCompra) {
        if (notaCompra == null)
            return null;

        return NotaCompraDto.builder()
                .id(notaCompra.getId())
                .montoTotal(notaCompra.getMontoTotal())
                .fecha(notaCompra.getFecha())
                .nombreProveedor(notaCompra.getIdProveedor().getNombre())
                .build();
    }

    public static NotaCompra toEntity(NotaCompraDto notaCompraDto) {
        if (notaCompraDto == null)
            return null;

        return NotaCompra.builder()
                .id(notaCompraDto.getId())
                .montoTotal(notaCompraDto.getMontoTotal())
                .fecha(notaCompraDto.getFecha())
                .idProveedor(null)
                .build();
    }
}
