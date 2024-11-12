package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.NotaCompra;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author GERSON
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaCompraDetalleDto {
    private Long id;
    private BigDecimal montoTotal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha;
    private String nombreProveedor;
    private List<DetalleDto> detalles;

    public static NotaCompra toEntity(NotaCompraDetalleDto notaCompraDto) {
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
