package bo.com.jvargas.veterinaria.negocio.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserAccessDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.v1.ResourceDto;

import java.util.List;
import java.util.Map;

public interface ResourceService {
    List<UserAccessDto> getResourceList();

    Map<String, Boolean> getActionsByUser(String frontendCode, String username);

    void updateResource(Long resourceId, ResourceDto resourceDto);
}
