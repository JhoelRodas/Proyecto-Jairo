package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRole;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private boolean baseRole;
    private EntityState roleStatus;
    private String baseRoleStr;
    public RoleDto(AuthRole entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.baseRole = entity.isBaseRole();
        this.roleStatus = entity.getRoleStatus();
        this.baseRoleStr = entity.isBaseRole() ? "Si" : "No";
    }

    public static RoleDto fromAuthRole(AuthRole authRole) {
        return new RoleDto(authRole);
    }
}
