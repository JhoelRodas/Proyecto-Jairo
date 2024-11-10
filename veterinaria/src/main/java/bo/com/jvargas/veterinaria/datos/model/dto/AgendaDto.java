package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Agenda;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author GERSON
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;
    private String estado;
    private String ci;
    private String extension;
    private String cliente;
    private String telefono;

    public static AgendaDto toDto(Agenda agenda) {
        return AgendaDto.builder()
                .id(agenda.getId())
                .fecha(agenda.getFecha())
                .hora(agenda.getHora())
                .descripcion(agenda.getDescripcion())
                .estado(agenda.getEstado())
                .ci(agenda.getIdCliente().getCi())
                .extension(agenda.getIdCliente().getExtension())
                .cliente(agenda.getIdCliente().getNombre())
                .telefono(agenda.getIdCliente().getTelefono())
                .build();
    }

    public static Agenda toEntity(AgendaDto agendaDto) {
        return Agenda.builder()
                .id(agendaDto.getId())
                .fecha(agendaDto.getFecha())
                .hora(agendaDto.getHora())
                .descripcion(agendaDto.getDescripcion())
                .estado(agendaDto.getEstado())
                .idCliente(null)
                .build();
    }
}
