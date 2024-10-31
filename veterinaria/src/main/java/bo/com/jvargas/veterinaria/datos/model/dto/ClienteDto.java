package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto implements Serializable {
    private Long id;
    private String ci;
    private String extension;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;
    private Long idAuthUser;

    public ClienteDto(Cliente cliente) {
        this.id = cliente.getId();
        this.ci = cliente.getCi();
        this.extension = cliente.getExtension();
        this.nombre = cliente.getNombre();
        this.telefono = cliente.getTelefono();
        this.correo = cliente.getCorreo();
        this.direccion = cliente.getDireccion();
        this.idAuthUser = cliente.getIdAuthUser()!= null?cliente.getIdAuthUser().getId():null;
    }


}
