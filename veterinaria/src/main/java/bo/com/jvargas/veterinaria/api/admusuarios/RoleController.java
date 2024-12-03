package bo.com.jvargas.veterinaria.api.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.dto.ResponseBody;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.PrivilegeDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.RoleDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserAccessDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState;
import bo.com.jvargas.veterinaria.negocio.admusuarios.RoleService;
import bo.com.jvargas.veterinaria.utils.ApiResponseException;
import bo.com.jvargas.veterinaria.utils.ApiUtil;
import bo.com.jvargas.veterinaria.utils.OperationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@Tag(name = "role", description = "API para gestion de roles")
@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    @Operation(summary = "",
            description = "",
            tags = {"role"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<List<RoleDto>> getRoleList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                                     @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                                     @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir,
                                                     @RequestParam(value = "filter", defaultValue = "") String filter) {
        try {
//            Pageable pageable = ApiUtil.buildPageableWithSort(page, size, sortBy, sortDir);
//            Page<AuthRole> pageRole = this.roleService.getRoleList(filter);
//            return ok(ApiUtil.buildResponseWithDefaults(
//                    ApiUtil.buildResponsePaged(pageRole, pageRole.getContent().stream().map(RoleDto::fromAuthRole)
//                            .collect(Collectors.toList()))
//            ));
            return ok(roleService.getRoleList(filter).stream().map(RoleDto::fromAuthRole).collect(Collectors.toList()));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

    @PostMapping()
    @Operation(summary = "",
            description = "",
            tags = {"role"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<RoleDto>> createRole(@RequestBody RoleDto roleDto) {
        try {
            return ok(ApiUtil.buildResponseWithDefaults(RoleDto.fromAuthRole(this.roleService.createRole(roleDto))));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

    @PutMapping("/{roleId}")
    @Operation(summary = "",
            description = "",
            tags = {"role"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<RoleDto>> updateRole(@RequestBody RoleDto roleDto,
                                                            @PathVariable("roleId") Long roleId) {
        try {
            return ok(ApiUtil.buildResponseWithDefaults(RoleDto.fromAuthRole(this.roleService.updateRole(roleDto, roleId))));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

    @DeleteMapping("/{roleId}")
    @Operation(summary = "",
            description = "",
            tags = {"role"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<Boolean>> deleteRole(@PathVariable("roleId") Long roleId) {
        try {
            this.roleService.deleteRole(roleId);
            return ok(ApiUtil.buildResponseWithDefaults(true));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

    @PutMapping("/update-state")
    @Operation(summary = "",
            description = "",
            tags = {"role"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<RoleDto>> updateStateRole(@RequestParam("roleId") Long roleId,
                                                                 @RequestParam("state") EntityState newState) {
        try {
            return ok(ApiUtil.buildResponseWithDefaults(RoleDto.fromAuthRole(this.roleService.updateStateRole(roleId, newState))));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

    @GetMapping("/access-list")
    @Operation(summary = "",
            description = "",
            tags = {"role"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<List<UserAccessDto>> findResourceByRol(@RequestParam("roleId") Long roleId) {
        try {
            List<UserAccessDto> userAccessDtoList = this.roleService.findUserAccessByRol(roleId);
            return ResponseEntity.ok(userAccessDtoList);
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

    @PostMapping("/configure-privileges")
    @Operation(summary = "",
            description = "",
            tags = {"role"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<Boolean>> configurePrivileges(@RequestParam("roleId") Long roleId,
                                                                     @RequestParam("resourceId") Long resourceId,
                                                                     @RequestBody List<PrivilegeDto> privilegeList) {
        try {
            this.roleService.configurePrivilegesByRolAndResource(roleId, resourceId, privilegeList);
            return ok(ApiUtil.buildResponseWithDefaults(true));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }
}
