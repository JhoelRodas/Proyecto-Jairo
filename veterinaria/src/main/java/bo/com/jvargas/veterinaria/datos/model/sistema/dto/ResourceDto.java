package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthResource;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private String url;
    private String icono;
    private Integer ordenMenu;
    private String acciones;
    private EntityState estado;
    private Integer nivel;
    private ResourceDto idRecursoPadre;
    private String identificadorWeb;
    private List<ResourceDto> listaRecursos;


    public ResourceDto(AuthResource authResource) {
        this.id = authResource.getId();
        this.nombre = authResource.getName();
        this.descripcion = authResource.getDescription();
        this.url = authResource.getUrl();
        this.icono = authResource.getIcon();
        this.ordenMenu = authResource.getMenuPosition();
//        this.acciones = recurso.getAcciones();
        this.estado = authResource.getResourceStatus();
//        this.nivel = authResource.getNivel();

        if(authResource.getIdAuthResourceParent() != null) {
            this.idRecursoPadre = new ResourceDto(authResource.getIdAuthResourceParent());
        }
//        this.identificadorWeb = recurso.getIdentificadorWeb();
    }




}
