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
public class ReciboDto extends ReporteDto {
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

    @Override
    public String getValor(String property) {
        switch (property){
            case "id":
                return String.valueOf(getId());
            case "nombre":
                return String.valueOf(getNombre());
            case "ci":
                return String.valueOf(getCi());
                case "fecha":
                    return String.valueOf(getFecha());
                    case "montoTotal":
                        return String.valueOf(getMontoTotal());
            case "metodoPago":
                return String.valueOf(getMetodoPago());

            default:return "";
        }
    }
}
