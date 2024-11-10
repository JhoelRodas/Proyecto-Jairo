package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Mascota;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDto implements Serializable {
    private Long id;
    private String nombre;
    private Integer edad;
    private String sexo;
    private String color;
    private String especie;
    private String raza;
    private String nombreCliente;
    private String ci;
    private Long idHistorial;

    public MascotaDto(Mascota mascota) {
        this.id = mascota.getId();
        this.nombre = mascota.getNombre();
        this.edad = mascota.getEdad();
        this.sexo = mascota.getSexo();
        this.color = mascota.getColor();
        this.nombreCliente = mascota.getCiCliente() != null ? mascota.getCiCliente().getNombre() : null;
        this.ci = mascota.getCiCliente() != null ? mascota.getCiCliente().getCi() : null;
        this.especie = mascota.getEspecie();
        this.raza = mascota.getRaza();
        this.idHistorial = mascota.getIdHistorial() != null ? mascota.getIdHistorial().getId() : null;
    }


}
