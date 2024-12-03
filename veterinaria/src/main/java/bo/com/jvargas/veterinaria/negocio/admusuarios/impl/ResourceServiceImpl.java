package bo.com.jvargas.veterinaria.negocio.admusuarios.impl;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAction;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthResource;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserAccessDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.v1.ResourceDto;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthResourceRepository;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthUserRepository;
import bo.com.jvargas.veterinaria.negocio.admusuarios.ResourceService;
import bo.com.jvargas.veterinaria.utils.FormatUtil;
import bo.com.jvargas.veterinaria.utils.OperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    private final AuthResourceRepository authResourceRepository;
    private final AuthUserRepository authUserRepository;

    @Override
    public List<UserAccessDto> getResourceList() {
//        return this.authResourceRepository.findAll();
        List<UserAccessDto> resultList = new ArrayList<>();
        List<AuthResource> resourceParentList = this.authResourceRepository.findAllParents();
        for (AuthResource parent : resourceParentList) {
            resultList.add(UserAccessDto.buildFromResourceParent(parent));
            resultList.addAll(this.authResourceRepository.findAccessByParent(parent));
        }
        return resultList;
    }

    @Override
    public Map<String, Boolean> getActionsByUser(String frontendCode, String username) {
        AuthUser authUser = this.authUserRepository.findByUsername(username).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado(AuthUser.class.getName(), "username", username)));
        List<AuthAction> actionList = this.authResourceRepository.getActionsByRoleAndFrontendCode(authUser.getIdAuthRole(), frontendCode);
        Map<String, Boolean> actionObjResult = new HashMap<>();
        actionList.forEach(item -> actionObjResult.put(item.getCode(), true));
        return actionObjResult;
    }

    @Override
    public void updateResource(Long resourceId, ResourceDto resourceDto) {
        AuthResource authResource = authResourceRepository.findById(resourceId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Recurso", "Id", resourceId.toString())));
        authResource.setIcon(resourceDto.getIcon());
        authResource.setName(resourceDto.getName());
        authResource.setMenuPosition(resourceDto.getPosition());
        authResourceRepository.save(authResource);
    }
}
