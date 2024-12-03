package bo.com.jvargas.veterinaria.negocio.admusuarios.impl;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAction;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthPrivilege;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthResource;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRole;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthActionRepository;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthResourceRepository;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthRoleRepository;
import bo.com.jvargas.veterinaria.negocio.admusuarios.ActionService;
import bo.com.jvargas.veterinaria.utils.FormatUtil;
import bo.com.jvargas.veterinaria.utils.OperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("actionService")
public class ActionServiceImpl implements ActionService {
    private final AuthActionRepository authActionRepository;
    private final AuthRoleRepository authRoleRepository;
    private final AuthResourceRepository authResourceRepository;

    public ActionServiceImpl(AuthActionRepository authActionRepository,
                             AuthRoleRepository authRoleRepository,
                             AuthResourceRepository authResourceRepository) {
        this.authActionRepository = authActionRepository;
        this.authRoleRepository = authRoleRepository;
        this.authResourceRepository = authResourceRepository;
    }

    @Override
    public List<AuthAction> findAllActions() {
        return this.authActionRepository.findAllActions();
    }

    @Override
    public List<AuthPrivilege> getPrivilegesByRolAndResource(Long roleId, Long resourceId) {
        AuthRole authRole = this.authRoleRepository.findById(roleId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", roleId)));

        AuthResource authResource = this.authResourceRepository.findById(resourceId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Recurso", resourceId)));

        return this.authActionRepository.findPrivilegesByRoleAndResource(authRole, authResource);
    }

    @Override
    public List<AuthAction> findAvailableActionsByResource(Long resourceId) {
        AuthResource authResource = this.authResourceRepository.findById(resourceId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Recurso", resourceId)));
        return this.authActionRepository.findAvailableActionsByResource(authResource);
    }
}
