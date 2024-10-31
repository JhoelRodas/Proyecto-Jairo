package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthResource;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.ResourceType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDto implements Serializable {
    private Long resourceId;
    private String name;
    private Boolean isParent;
    private String url;
    private Boolean checked;
    private Long idRoleResource;

    private String icon;
    private String route;
    private Integer position;
    private ResourceType type;

    public UserAccessDto(Long resourceId, String name, Boolean isParent, String url, Boolean checked, Long idRoleResource) {
        this.resourceId = resourceId;
        this.name = name;
        this.isParent = isParent;
        this.url = url;
        this.checked = checked;
        this.idRoleResource = idRoleResource;
    }

    public UserAccessDto(Long resourceId, String name, Boolean isParent, String url, String icon, String route, Integer position, ResourceType type) {
        this.resourceId = resourceId;
        this.name = name;
        this.isParent = isParent;
        this.url = url;
        this.icon = icon;
        this.route = route;
        this.position = position;
        this.type = type;
    }

    public static UserAccessDto buildFromResourceParent(AuthResource parent) {
        return UserAccessDto.builder()
                .resourceId(parent.getId())
                .name(parent.getName())
                .isParent(true)
                .build();
    }
}
