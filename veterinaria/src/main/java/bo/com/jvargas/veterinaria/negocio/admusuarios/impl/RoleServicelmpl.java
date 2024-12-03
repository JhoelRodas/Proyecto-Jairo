package bo.com.jvargas.veterinaria.negocio.admusuarios.impl;

//import bo.com.jvargas.veterinaria.negocio.sistema.RoleService;

import bo.com.jvargas.veterinaria.datos.model.sistema.*;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.PrivilegeDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.RoleDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserAccessDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserAuxDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;
import bo.com.jvargas.veterinaria.datos.repository.sistema.*;
import bo.com.jvargas.veterinaria.negocio.admusuarios.RoleService;
import bo.com.jvargas.veterinaria.utils.FormatUtil;
import bo.com.jvargas.veterinaria.utils.OperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("rolService")
public class RoleServicelmpl implements RoleService {
    private final AuthRoleRepository authRoleRepository;
    private final AuthResourceRepository authResourceRepository;
    private final AuthActionRepository authActionRepository;
    private final AuthPrivilegeRepository authPrivilegeRepository;
    private final AuthRoleResourceRepository authRoleResourceRepository;

    public void save(UserAuxDto userAuxDto, AuthRole roles) {
        authRoleRepository.save(roles);
    }

    @Override
    public void update(UserAuxDto userAuxDto, AuthRole roles) {
      authRoleRepository.save(roles);
    }


    @Override
    public AuthRole findById(Long id) {
        return authRoleRepository.findById(id).orElse(null);
    }

    @Override
    public AuthRole createRole(RoleDto roleDto) {
        Optional<AuthRole> authRoleOpt = this.authRoleRepository.finByName(roleDto.getName().trim());
        if (authRoleOpt.isPresent()) throw new OperationException(FormatUtil.yaRegistrado("Rol", "Nombre", roleDto.getName()));

        return this.authRoleRepository.save(AuthRole.builder()
                .name(roleDto.getName().trim())
                .description(roleDto.getDescription())
                .roleStatus(EntityState.ACTIVO)
                .build());
    }

    @Override
    public List<AuthRole> getRoleList(String filter) {
        if (filter == null || filter.isBlank()) filter = "%%";
        else filter = "%" + filter + "%";
        return this.authRoleRepository.pageWithFilter(filter);
    }

    @Override
    public AuthRole updateRole(RoleDto roleDto, Long roleId) {

        AuthRole authRole = this.authRoleRepository.findById(roleId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", roleId)));
        if (authRole.isBaseRole()) throw new OperationException("No se permite cambiar los datos de un rol base.");

        Optional<AuthRole> authRoleOpt = this.authRoleRepository.finByName(roleDto.getName().trim());
        if (authRoleOpt.isPresent() && !authRoleOpt.get().getId().equals(authRole.getId()))
            throw new OperationException(FormatUtil.yaRegistrado("Rol", "Nombre", roleDto.getName()));

        authRole.setName(roleDto.getName());
        authRole.setDescription(roleDto.getDescription());
        return this.authRoleRepository.save(authRole);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        AuthRole authRole = this.authRoleRepository.findById(roleId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", roleId)));

        if (authRole.isBaseRole()) throw new OperationException("No se permite eliminar un rol base.");
        this.authRoleRepository.deleteAuthRole(authRole);
    }

    @Override
    public AuthRole updateStateRole(Long roleId, EntityState newState) {
        if (newState == null) throw new OperationException(FormatUtil.requerido("Nuevo estado"));

        AuthRole authRole = this.authRoleRepository.findById(roleId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", roleId)));

        if (authRole.isBaseRole()) throw new OperationException("No se permite modificar un rol base.");

        authRole.setRoleStatus(newState);
        return this.authRoleRepository.save(authRole);
    }

    @Override
    public List<UserAccessDto> findUserAccessByRol(Long roleId) {
        AuthRole authRole = this.authRoleRepository.findById(roleId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", roleId)));

        List<UserAccessDto> resultList = new ArrayList<>();
        List<AuthResource> resourceParentList = this.authResourceRepository.findAllParents();
        for (AuthResource parent : resourceParentList) {
            resultList.add(UserAccessDto.buildFromResourceParent(parent));
            resultList.addAll(this.authResourceRepository.findAccessByRoleAndParent(authRole, parent));
        }
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {OperationException.class, Exception.class})
    public void configurePrivilegesByRolAndResource(Long roleId, Long resourceId, List<PrivilegeDto> privilegeList) {
        AuthRole authRole = this.authRoleRepository.findById(roleId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", roleId)));
        AuthResource authResource = this.authResourceRepository.findById(resourceId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Recurso", resourceId)));

        AuthRoleResource authRoleResource = this.authRoleResourceRepository.findByRoleAndResource(authRole, authResource)
                .orElse(AuthRoleResource.builder()
                        .idAuthResource(authResource)
                        .idAuthRole(authRole)
                        .build());
        if (authRoleResource.getId() == null) this.authRoleResourceRepository.save(authRoleResource);

        AuthRoleResource authRoleResourcePadre = this.authRoleResourceRepository.findByRoleAndResource(authRole, authResource.getIdAuthResourceParent())
                .orElse(AuthRoleResource.builder()
                        .idAuthResource(authResource.getIdAuthResourceParent())
                        .idAuthRole(authRole)
                        .build());
        if (authRoleResourcePadre.getId() == null) this.authRoleResourceRepository.save(authRoleResourcePadre);

        for (PrivilegeDto privilegeDto : privilegeList) {
            if (privilegeDto.getId() != null) {
                AuthPrivilege authPrivilege = this.authPrivilegeRepository.findById(privilegeDto.getId()).orElse(null);
                if (authPrivilege != null) {
                    if (!privilegeDto.getEnable()) this.authPrivilegeRepository.delete(authPrivilege);
                }
            } else {
                if (privilegeDto.getEnable()) {
                    AuthAction authAction = this.authActionRepository.findById(privilegeDto.getIdAuthAction())
                            .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Acción", privilegeDto.getIdAuthAction())));

                    this.authPrivilegeRepository.save(AuthPrivilege.builder()
                            .idAuthRoleResource(authRoleResource)
                            .idAuthAction(authAction)
                            .build());
                }
            }
        }
        this.deleteRoleResource(roleId, resourceId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {OperationException.class, Exception.class})
    public void deleteRoleResource(Long roleId, Long resourceId) {
        AuthRole authRole = this.authRoleRepository.findById(roleId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", roleId)));
        AuthResource authResource = this.authResourceRepository.findById(resourceId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Recurso", resourceId)));

        AuthRoleResource authRoleResource = this.authRoleResourceRepository.findByRoleAndResource(authRole, authResource)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol Recurso", 0)));

        int countProvileges = this.authPrivilegeRepository.countAllByRoleResource(authRoleResource);
        if (countProvileges == 0) {
            this.authRoleResourceRepository.delete(authRoleResource);
        }
    }
}
