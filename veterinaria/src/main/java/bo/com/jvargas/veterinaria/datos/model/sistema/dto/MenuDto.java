package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by :LeOnardO Bozo Ramos
 * Date       :28/12/2018
 * Project    onboarding
 * Package    :bo.com.mc4.core.model.dto
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private String url;
    private String icono;
    private Integer ordenMenu;
    private String acciones;
    private EntityState estado;
    private Integer nivel;
    private Integer idRecursoPadre;
    private String identificadorWeb;
    private List<MenuDto> listaRecursos = new ArrayList<>();

    public MenuDto(Long id, String nombre, String descripcion, String url, String icono, Integer ordenMenu, String acciones, EntityState estado, Integer nivel, Integer idRecursoPadre, String identificadorWeb) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
        this.icono = icono;
        this.ordenMenu = ordenMenu;
        this.acciones = acciones;
        this.estado = estado;
        this.nivel = nivel;
        this.idRecursoPadre = idRecursoPadre;
        this.identificadorWeb = identificadorWeb;
    }


}
