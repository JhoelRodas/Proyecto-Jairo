package bo.com.jvargas.veterinaria.api.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAction;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthPrivilege;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.ActionDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.PrivilegeDto;
import bo.com.jvargas.veterinaria.negocio.admusuarios.ActionService;
import bo.com.jvargas.veterinaria.utils.ApiResponseException;
import bo.com.jvargas.veterinaria.utils.OperationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@Tag(name = "action", description = "API para gestion de acciones")
@RestController
@RequestMapping("/api/v1/action")
public class ActionController {
    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping("find-available-by-resource")
    @Operation(summary = "",
            description = "",
            tags = {"action"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<List<ActionDto>> findAvailableActionsByResource(@RequestParam("resourceId") Long resourceId) {
        try {
            List<AuthAction> actionList = this.actionService.findAvailableActionsByResource(resourceId);
            return ok(actionList.stream().map(ActionDto::buildFromAuthAction)
                            .collect(Collectors.toList()));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

    @GetMapping("/privileges-by-role-and-resource")
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
    public ResponseEntity<List<PrivilegeDto>> getPrivilegesByRolAndResource(@RequestParam("roleId") Long roleId,
                                                                                          @RequestParam("resourceId") Long resourceId) {
        try {
            List<AuthPrivilege> privilegeList = this.actionService.getPrivilegesByRolAndResource(roleId, resourceId);
            return ok(privilegeList.stream().map(PrivilegeDto::buildFromPrivilege)
                            .collect(Collectors.toList()));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }
}
