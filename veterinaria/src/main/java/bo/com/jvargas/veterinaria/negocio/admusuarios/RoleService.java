package bo.com.jvargas.veterinaria.negocio.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRole;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.PrivilegeDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.RoleDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserAccessDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserAuxDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;

import java.util.List;

public interface RoleService {

    void save(UserAuxDto userAuxDto, AuthRole roles);

    void update(UserAuxDto userAuxDto, AuthRole roles);

    AuthRole findById(Long id);

    AuthRole createRole(RoleDto roleDto);

    List<AuthRole> getRoleList(String filter);

    AuthRole updateRole(RoleDto roleDto, Long roleId);

    void deleteRole(Long roleId);

    AuthRole updateStateRole(Long roleId, EntityState newState);

    List<UserAccessDto> findUserAccessByRol(Long roleId);

    void configurePrivilegesByRolAndResource(Long roleId, Long resourceId, List<PrivilegeDto> privilegeList);

    void deleteRoleResource(Long roleId, Long resourceId);

}
