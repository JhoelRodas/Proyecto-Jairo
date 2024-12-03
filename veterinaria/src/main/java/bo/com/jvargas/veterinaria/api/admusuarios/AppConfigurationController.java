package bo.com.jvargas.veterinaria.api.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAppConfiguration;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.AppConfigurationDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.ApplicationType;
import bo.com.jvargas.veterinaria.negocio.admusuarios.AppConfigurationService;
import bo.com.jvargas.veterinaria.utils.ApiUtil;
import bo.com.jvargas.veterinaria.utils.ApiResponseException;
import bo.com.jvargas.veterinaria.datos.model.dto.ResponseBody;
import bo.com.jvargas.veterinaria.utils.OperationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@Tag(name = "role", description = "API para gestion de roles")
@RestController
@RequestMapping("/api/v1/theme")
public class AppConfigurationController {
    @Value("${build.version}")
    private String version;

    private final AppConfigurationService appConfigurationService;

    public AppConfigurationController(AppConfigurationService appConfigurationService) {
        this.appConfigurationService = appConfigurationService;
    }

    @GetMapping("/find")
    @Operation(summary = "",
            description = "",
            tags = {"theme"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<String>> findAppTheme(@RequestParam("applicationType") ApplicationType applicationType) {
        try {
            User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String appConfig = null;
            AuthAppConfiguration appTheme = this.appConfigurationService.findAppTheme(userAuth.getUsername(), applicationType);
            if (appTheme != null) appConfig = appTheme.getJsonConfig();
            return ok(ApiUtil.buildResponseWithDefaults(appConfig));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

    @PutMapping("/update")
    @Operation(summary = "",
            description = "",
            tags = {"theme"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Registro creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<String>> updateAppTheme(@RequestParam("applicationType") ApplicationType applicationType,
                                                               @RequestBody AppConfigurationDto appConfigurationDto) {
        try {
            User userAuth = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            this.appConfigurationService.updateAppTheme(userAuth.getUsername(), applicationType, appConfigurationDto);
            return ok(ApiUtil.buildResponseWithDefaults(null));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }


    @GetMapping("/version")
    String version() {
        return version;
    }
}
