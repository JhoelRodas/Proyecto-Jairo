package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthPrivilege;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDto implements Serializable {
    private Long id;
    private Long idAuthAction;
    private Long idAuthRoleResource;
    private Boolean enable;

    public static PrivilegeDto buildFromPrivilege(AuthPrivilege authPrivilege) {
        return PrivilegeDto.builder()
                .id(authPrivilege.getId())
                .idAuthAction(authPrivilege.getIdAuthAction().getId())
                .idAuthRoleResource(authPrivilege.getIdAuthRoleResource().getId())
                .enable(!authPrivilege.isDeleted())
                .build();
    }
}
