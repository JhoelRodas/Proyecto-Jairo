package bo.com.jvargas.veterinaria.negocio.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAction;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthPrivilege;

import java.util.List;

public interface ActionService {
    List<AuthAction> findAllActions();

    List<AuthPrivilege> getPrivilegesByRolAndResource(Long roleId, Long resourceId);

    List<AuthAction> findAvailableActionsByResource(Long resourceId);
}
