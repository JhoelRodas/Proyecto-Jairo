package bo.com.jvargas.veterinaria.negocio.admusuarios.impl;

import bo.com.jvargas.veterinaria.datos.model.sistema.*;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.ResourceType;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoAuth;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus;
import bo.com.jvargas.veterinaria.datos.repository.sistema.*;
import bo.com.jvargas.veterinaria.negocio.admusuarios.InitializerService;
import bo.com.jvargas.veterinaria.utils.OperationException;
import bo.com.jvargas.veterinaria.utils.ResourceActionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("initializerService")
public class InitializerServiceImpl implements InitializerService {
    private final AuthActionRepository authActionRepository;
    private final AuthRoleRepository authRoleRepository;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthResourceRepository authResourceRepository;
    private final AuthRoleResourceRepository authRoleResourceRepository;
    private final AuthPrivilegeRepository authPrivilegeRepository;
    private final AuthResourceActionRepository authResourceActionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {OperationException.class, Exception.class})
    public void initAll() {
        addDefaultRolUsers();
        addActions();
        buildResources();
        buildDefaultMenu();
    }

    private void addDefaultRolUsers() {
        AuthRole root = authRoleRepository.findByName("ROL_ROOT")
                .orElse(AuthRole.builder()
                        .name("ROL_ROOT")
                        .description("Rol para usuarios de mantenimiento")
                        .roleStatus(EntityState.ACTIVO)
                        .baseRole(true)
                        .build());
        if (root.getId() == null) {
            this.authRoleRepository.save(root);
        }
        AuthRole cliente = authRoleRepository.findByName("ROL_CLIENTE")
                .orElse(AuthRole.builder()
                        .name("ROL_CLIENTE")
                        .description("Rol para usuarios clientes")
                        .roleStatus(EntityState.ACTIVO)
                        .baseRole(true)
                        .build());
        if (cliente.getId() == null) {
            this.authRoleRepository.save(cliente);
        }

        if (this.authUserRepository.findByUsername("admin").isEmpty()) {
            this.authUserRepository.save(AuthUser.builder()
                    .name("admin")
                    .lastname("admin")
                    .username("admin")
                    .email("soporte@mc4.com.bo")
                    .password(passwordEncoder.encode("admin"))
                    .idAuthRole(root)
                    .userStatus(UserStatus.ACTIVO)
                    .tipoAuth(TipoAuth.DB)
                    .build());
        }
    }

    private void addActions() {
        for (AuthAction authAction : ResourceActionUtil.accionesBaseList()) {
            Optional<AuthAction> actionOptional = this.authActionRepository.findByCode(authAction.getCode());
            log.info("Accion: {} -> {}", authAction.getAction(), actionOptional.isPresent());
            AuthAction authActionToSave;
            if (actionOptional.isPresent()) {
                authActionToSave = actionOptional.get();
                authActionToSave.setAction(authAction.getAction());
            } else {
                authActionToSave = authAction;
            }
            this.authActionRepository.save(authActionToSave);
        }
    }

    private AuthResource addResource(String nombre,
                                     String descripcion,
                                     String url,
                                     int ordenMenu,
                                     String icono,
                                     ResourceType type,
                                     String badge,
                                     String badgeColor,
                                     String customClass,
                                     String frontendCode,
                                     AuthResource authResourcePadre,
                                     String[] availableActionCodeList) {
        AuthResource authResource = authResourceRepository.findByUrl(url).orElse(AuthResource.builder()
                .url(url)
                .build());
        log.info("Recurso: {} -> {}", authResource.getUrl(), authResource.getId());
        authResource.setName(nombre);
        authResource.setDescription(descripcion);
        authResource.setMenuPosition(ordenMenu);
        authResource.setIcon(icono);
        authResource.setResourceStatus(EntityState.ACTIVO);
        authResource.setIdAuthResourceParent(authResourcePadre);
        authResource.setType(type);
        authResource.setBadge(badge);
        authResource.setBadgeColor(badgeColor);
        authResource.setCustomClass(customClass);
        authResource.setFrontendCode(frontendCode);
        authResourceRepository.save(authResource);
        if (availableActionCodeList != null) {
            for (String actionCode : availableActionCodeList) {
                AuthAction authAction = this.authActionRepository.findByCode(actionCode).orElseThrow(() -> new OperationException("Error"));
                Optional<AuthResourceAction> authResourceActionOptional = this.authResourceActionRepository.findByResourceAndAction(authResource, authAction);
                log.info("Recurso: {}, Accion: {} -> {}", authResource.getUrl(), authAction.getCode(), authResourceActionOptional.isPresent());
                if (authResourceActionOptional.isEmpty()) {
                    this.authResourceActionRepository.save(AuthResourceAction.builder()
                            .idAuthResource(authResource)
                            .idAuthAction(authAction)
                            .build());
                }
            }
        }
        return authResource;
    }


    private void buildResources() {
        AuthResource recPadreAdm = addResource("ADM DE USUARIOS", "ADMINISTRACION\nDE USUARIOS", "administracion", 1, "security", ResourceType.item, null, null, null, null, null, null);
        addResource("Usuarios", "Interfaz para administración de Usuarios", recPadreAdm.getUrl().concat("/usuario"), 1, "insert_link", ResourceType.item, null, null, null, "PAGE_USER", recPadreAdm, ResourceActionUtil.commonsActionsCode);
        addResource("Rol", "Interfaz para administración de Roles", recPadreAdm.getUrl().concat("/rol"), 2, "insert_link", ResourceType.item, null, null, null, "PAGE_ROL", recPadreAdm, ResourceActionUtil.commonsActionsCode);
        addResource("Privilegios", "Interfaz para administración de privilegio", recPadreAdm.getUrl().concat("/access"), 3, "insert_link", ResourceType.item, null, null, null, "PAGE_ACCESS", recPadreAdm, ResourceActionUtil.commonsActionsCode);
        addResource("Bitacora", "Interfaz para administración de Bitacora", recPadreAdm.getUrl().concat("/bitacora"), 4, "insert_link", ResourceType.item, null, null, null, "PAGE_BITACORA", recPadreAdm, ResourceActionUtil.commonsActionsCode);

        AuthResource recPadreVentas = addResource("VENTAS", "VENTAS", "ventas", 2, "security", ResourceType.item, null, null, null, null, null, null);
        addResource("Cliente", "Interfaz para el Cliente", recPadreVentas.getUrl().concat("/cliente"), 1, "insert_link", ResourceType.item, null, null, null, "PAGE_CLIENTE", recPadreVentas, ResourceActionUtil.commonsActionsCode);
        addResource("Mascota", "Interfaz para la Mascota", recPadreVentas.getUrl().concat("/mascota"), 2, "insert_link", ResourceType.item, null, null, null, "PAGE_MASCOTA", recPadreVentas, ResourceActionUtil.commonsActionsCode);
        addResource("VentaProducto", "Interfaz para la venta", recPadreVentas.getUrl().concat("/venta"), 3, "insert_link", ResourceType.item, null, null, null, "PAGE_VENTA", recPadreVentas, ResourceActionUtil.commonsActionsCode);
        addResource("Servicios", "Interfaz para los servicios", recPadreVentas.getUrl().concat("/servicio"), 4, "insert_link", ResourceType.item, null, null, null, "PAGE_SERVICIO", recPadreVentas, ResourceActionUtil.commonsActionsCode);
        addResource("Agenda", "Interfaz para la Agenda", recPadreVentas.getUrl().concat("/agenda"), 5, "insert_link", ResourceType.item, null, null, null, "PAGE_AGENDA", recPadreVentas, ResourceActionUtil.commonsActionsCode);
        addResource("historialClinico", "Interfaz para el Historial", recPadreVentas.getUrl().concat("/historialClinico"), 6, "insert_link", ResourceType.item, null, null, null, "PAGE_HISTORIAL", recPadreVentas, ResourceActionUtil.commonsActionsCode);
        addResource("Atencion", "Interfaz para la Atencion", recPadreVentas.getUrl().concat("/atencion"), 7, "insert_link", ResourceType.item, null, null, null, "PAGE_ATENCION", recPadreVentas, ResourceActionUtil.commonsActionsCode);

        AuthResource recPadreCompra = addResource("COMPRA", "COMPRA", "compras", 3, "security", ResourceType.item, null, null, null, null, null, null);
        addResource("Proveedor", "Interfaz para el Proveedor", recPadreCompra.getUrl().concat("/proveedor"), 1, "insert_link", ResourceType.item, null, null, null, "PAGE_PROVEEDOR", recPadreCompra, ResourceActionUtil.commonsActionsCode);
        addResource("Nota de Compra", "Interfaz para la Nota de Compra", recPadreCompra.getUrl().concat("/nota-Compra"), 2, "insert_link", ResourceType.item, null, null, null, "PAGE_NOTACOMPRA", recPadreCompra, ResourceActionUtil.commonsActionsCode);

        AuthResource recPadreInventario = addResource("INVENTARIO", "INVENTARIO", "inventario", 4, "security", ResourceType.item, null, null, null, null, null, null);
        addResource("Producto", "Interfaz para del Producto", recPadreInventario.getUrl().concat("/producto"), 1, "insert_link", ResourceType.item, null, null, null, "PAGE_PRODUCTO", recPadreInventario, ResourceActionUtil.commonsActionsCode);
        addResource("Categoria", "Interfaz para las Categorias", recPadreInventario.getUrl().concat("/categoria"), 2, "insert_link", ResourceType.item, null, null, null, "PAGE_CATEGORIA", recPadreInventario, ResourceActionUtil.commonsActionsCode);
        addResource("Estante", "Interfaz para los Estantes", recPadreInventario.getUrl().concat("/estante"), 3, "insert_link", ResourceType.item, null, null, null, "PAGE_ESTANTE", recPadreInventario, ResourceActionUtil.commonsActionsCode);




        //        AuthResource recPadreSeguridad = addResource("Seguridad", "Módulo de seguridad", "security", 1, "security", ResourceType.item, null, null, null, null, null, null);
//
//        // Seeeders
//        addResource("Roles", "Interfaz para administración de roles", recPadreSeguridad.getUrl().concat("/roles"), 1, "insert_link", ResourceType.item, null, null, null, "PAGE_ROLES", recPadreSeguridad, ResourceActionUtil.roleActionsCode);
//        addResource("Usuarios", "Interfaz para administración de usuarios", recPadreSeguridad.getUrl().concat("/users"), 2, "insert_link", ResourceType.item, null, null, null, "PAGE_USUARIOS", recPadreSeguridad, ResourceActionUtil.userActionsCode);
//        addResource("Recursos", "Interfaz para administración de recursos del sistema", recPadreSeguridad.getUrl().concat("/resources"), 3, "insert_link", ResourceType.item, null, null, null, "PAGE_RECURSOS", recPadreSeguridad, ResourceActionUtil.resourceActionsCode);
//        addResource("Accesos", "Interfaz para configuración de accesos a roles", recPadreSeguridad.getUrl().concat("/access"), 4, "insert_link", ResourceType.item, null, null, null, "PAGE_ACCESOS", recPadreSeguridad, ResourceActionUtil.accessActionsCode);
    }

    private void buildDefaultMenu() {
        AuthRole root = this.authRoleRepository.findRolByName("ROL_ROOT");
//        AuthRole colaborador = this.authRoleRepository.findRolByName("ROL_COLABORADOR");
//        AuthRole directora = this.authRoleRepository.findRolByName("ROL_DIRECTORA");
//        AuthRole callcenter = this.authRoleRepository.findRolByName("ROL_CALLCENTER");
//        AuthRole gerente = this.authRoleRepository.findRolByName("ROL_GERENTE");

        AuthResource recPadreAdm = addPrivilegesRoleResource("administracion", null, root);
        addPrivilegesRoleResource(recPadreAdm.getUrl().concat("/usuario"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreAdm.getUrl().concat("/rol"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreAdm.getUrl().concat("/access"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreAdm.getUrl().concat("/bitacora"), ResourceActionUtil.commonsActionsCode, root);

        AuthResource recPadreVentas = addPrivilegesRoleResource("ventas", null, root);
        addPrivilegesRoleResource(recPadreVentas.getUrl().concat("/cliente"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreVentas.getUrl().concat("/mascota"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreVentas.getUrl().concat("/venta"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreVentas.getUrl().concat("/servicio"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreVentas.getUrl().concat("/agenda"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreVentas.getUrl().concat("/historialClinico"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreVentas.getUrl().concat("/atencion"), ResourceActionUtil.commonsActionsCode, root);

        AuthResource recPadreCompras = addPrivilegesRoleResource("compras", null, root);
        addPrivilegesRoleResource(recPadreCompras.getUrl().concat("/proveedor"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreCompras.getUrl().concat("/nota-Compra"), ResourceActionUtil.commonsActionsCode, root);

        AuthResource recPadreInventario = addPrivilegesRoleResource("inventario", null, root);
        addPrivilegesRoleResource(recPadreInventario.getUrl().concat("/producto"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreInventario.getUrl().concat("/categoria"), ResourceActionUtil.commonsActionsCode, root);
        addPrivilegesRoleResource(recPadreInventario.getUrl().concat("/estante"), ResourceActionUtil.commonsActionsCode, root);



    }

    private AuthResource addPrivilegesRoleResource(String url,
                                                   String[] availableActionCodeList,
                                                   AuthRole... roles) {

        AuthResource authResource = authResourceRepository.findByUrl(url).orElseThrow(() -> new OperationException("Error"));
        for (AuthRole authRole : roles) {
            AuthRoleResource authRoleResource = authRoleResourceRepository.findByRoleAndResource(authRole, authResource).orElse(AuthRoleResource.builder()
                    .idAuthResource(authResource)
                    .idAuthRole(authRole)
                    .build());
            log.info("Rol: {}, Recurso: {} -> {}", authRole.getName(), authResource.getUrl(), authRoleResource.getId());
            if (authRoleResource.getId() == null) {
                authRoleResourceRepository.save(authRoleResource);
            }
            if (availableActionCodeList != null) {
                for (String actionCode : availableActionCodeList) {
                    AuthAction authAction = this.authActionRepository.findByCode(actionCode).orElseThrow(() -> new OperationException("Error"));
                    Optional<AuthPrivilege> authPrivilegeOpt = authPrivilegeRepository.findByActionAndRoleResource(authAction, authRoleResource);
                    log.info("Rol: {}, Recurso: {}, Accion: {} -> {}", authRole.getName(), authResource.getUrl(), authAction.getCode(), authPrivilegeOpt.isPresent());
                    if (authPrivilegeOpt.isEmpty()) {
                        authPrivilegeRepository.save(AuthPrivilege.builder()
                                .idAuthAction(authAction)
                                .idAuthRoleResource(authRoleResource)
                                .build());
                    }
                }
            }
//
//            this.authPrivilegeRepository.save(AuthPrivilege.builder()
//                    .idAuthAction(this.authActionRepository.findByCode(ResourceActionUtil.LIST_ACTION_CODE).orElse(null))
//                    .idAuthRoleResource(authRoleResource)
//                    .build());
//            if (authRole.getName().equals(Constants.ROL_ROOT) && authResource.getUrl().equals("security/log")) {
//                this.authPrivilegeRepository.save(AuthPrivilege.builder()
//                        .idAuthAction(this.authActionRepository.findByCode(ResourceActionUtil.VIEW_LOG_ACTION).orElse(null))
//                        .idAuthRoleResource(authRoleResource)
//                        .build());
//            }
//            if (authRole.getName().equals(Constants.ROL_ROOT) && authResource.getUrl().equals("security/access")) {
//                this.authPrivilegeRepository.save(AuthPrivilege.builder()
//                        .idAuthAction(this.authActionRepository.findByCode(ResourceActionUtil.CONFIG_ACCESS_ACTION).orElse(null))
//                        .idAuthRoleResource(authRoleResource)
//                        .build());
//            }
        }
        return authResource;
    }

}
