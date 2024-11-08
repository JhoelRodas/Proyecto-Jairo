package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Recibo;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author GERSON
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReciboDto {
    private Long id;
    private LocalDate fecha;
    private BigDecimal montoTotal;
    private String metodoPago;
    private String ci;
    private String extension;
    private String nombre;

    public static ReciboDto toDto(Recibo recibo) {
        return ReciboDto.builder()
                .id(recibo.getId())
                .fecha(recibo.getFecha())
                .montoTotal(recibo.getMontoTotal())
                .metodoPago(recibo.getMetodoPago())
                .ci(recibo.getIdCliente().getCi())
                .extension(recibo.getIdCliente().getExtension())
                .nombre(recibo.getIdCliente().getNombre())
                .build();
    }
}
