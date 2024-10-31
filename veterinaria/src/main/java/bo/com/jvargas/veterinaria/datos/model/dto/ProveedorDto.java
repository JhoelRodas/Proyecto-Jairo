package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Proveedor;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDto implements Serializable {
    private Long id;
    private String nombre;
    private Integer telefono;
    private String correo;
    private String direccion;
    private String encargado;

    public ProveedorDto(Proveedor proveedor) {
        this.id = proveedor.getId();
        this.nombre = proveedor.getNombre();
        this.direccion = proveedor.getDireccion();
        this.correo = proveedor.getCorreo();
        this.telefono = proveedor.getTelefono();
        this.encargado = proveedor.getEncargado();
    }
}
