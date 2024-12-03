package bo.com.jvargas.veterinaria.api.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.dto.ResponseBody;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.*;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthRoleRepository;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthUserRepository;
import bo.com.jvargas.veterinaria.negocio.admusuarios.BitacoraService;
import bo.com.jvargas.veterinaria.negocio.admusuarios.UserService;
import bo.com.jvargas.veterinaria.security.JwtTokenProvider;
import bo.com.jvargas.veterinaria.utils.ApiResponseException;
import bo.com.jvargas.veterinaria.utils.ApiUtil;
import bo.com.jvargas.veterinaria.utils.FormatUtil;
import bo.com.jvargas.veterinaria.utils.OperationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@Tag(name = "auth", description = "API para procesos de autentificación")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    // region [Injections]
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BitacoraService bitacoraService;
    // endregion [Injections]

    @PostMapping("/signin")
    @Operation(summary = "Autentificación de usuario",
            description = "Método para autentificar a un usuario ",
            tags = {"auth"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
            })
    public ResponseEntity<ResponseBody<OKAuthDto>> signin(
            @Parameter(schema = @Schema(implementation = AuthenticationDto.class),description = "Request de autenticación")
            @RequestBody AuthenticationDto data) {
        try {
            String token = validateAuthData(data);
            log.info("Sesión iniciada por el usuario: {}", data.getUsername());
            bitacoraService.info(TipoProceso.INICIAR_SESION, "Sesion iniciada por el usuario: {}", data.getUsername());
            return ok(ApiUtil.buildResponseWithDefaults(OKAuthDto.builder()
                    .username(data.getUsername())
                    .token(token)
                    .build()));
        } catch (OperationException | BadCredentialsException e) {
            log.error("No se logró autentificar al usuario: {}. Causa. {}", data.getUsername(), e.getMessage());
            bitacoraService.error(TipoProceso.INICIAR_SESION, "Error al iniciar sesion por el usuario: {}", data.getUsername(), e);
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error al autentificar el usuario: {}", data.getUsername(), e);
            bitacoraService.error(TipoProceso.INICIAR_SESION, "Error inesperado al iniciar sesion por el usuario: {}", data.getUsername(), e);
            throw ApiResponseException.serverError("Se generó un error al autentificar al usuario");
        }
    }

    @GetMapping("/logout")
    @Operation(summary = "logout",
            description = "Método para cerrar sesión ",
            tags = {"auth"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<?>> logout() {
        try {
            User authAuthUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (authAuthUser != null) {
                log.info("Cerrando sesión del usuario: {}", authAuthUser.getUsername());
                // log importante para el perfil
//                logService.infoApp(ProcessType.SESION, "Sesión finalizada por el usuario: " + authAuthUser.getUsername(), "", authAuthUser.getUsername());
            }
            return ok(ApiUtil.buildResponseWithDefaults(null));
        } catch (OperationException | BadCredentialsException e) {
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            throw ApiResponseException.serverError(FormatUtil.defaultError());
        }
    }

    @PostMapping("/cambiar-password")
    @Operation(summary = "Cambiar Contraseña",
            description = "Método para cambiar contraseña de un usuario",
            tags = {"auth"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<?>> changePassword(
            @Parameter(schema = @Schema(implementation = UserPassword.class), description = "Request para cambio de contraseña")
            @RequestBody UserPassword data,
            @AuthenticationPrincipal Authentication authentication) {
        try {
            if (authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();
                AuthUser authAuthUser = AuthUser.builder()
                        .username(user.getUsername())
                        .build();
                this.userService.changePassword(authAuthUser, data);
                log.info("El usuario: {} ha cambiado exitosamente su contrasenia", authAuthUser.getUsername());
                return ok(ApiUtil.buildResponseWithDefaults(null));
            }
            throw ApiResponseException.unauthorized("El usuario no ha iniciado su sesión");
        } catch (OperationException e) {
            throw ApiResponseException.badRequest("Cambio contraseña", e.getMessage());
        } catch (Exception e) {
            throw ApiResponseException.serverError(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    @RequestMapping(value = "/perfil", method = RequestMethod.GET)
    @Operation(summary = "Obtiene el perfil del usuario",
            description = "Método para obtener el perfil del usuario",
            tags = {"auth"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "400", description = "Error en request", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Error en genérico en el servidor", content = @Content(schema = @Schema(hidden = true))),
            }, security = @SecurityRequirement(name = "bearerToken"))
    public ResponseEntity<ResponseBody<UserDto>> getUser(@Parameter(hidden = true) @AuthenticationPrincipal Authentication authentication) {
        try {
            UserDto userDto = new UserDto();
            if (userDto == null) {
                throw ApiResponseException.notContent("No se encontró ningún dato para el usuario");
            }
            return ok(ApiUtil.buildResponseWithDefaults(userDto));
        } catch (Exception e) {
            log.error("Se generó un error genérico al obtener el perfil del usuario: {}", authentication.getName());
            throw ApiResponseException.serverError("No se logró obtener el perfil del usuario");
        }
    }


    @PostMapping("/recuperar-password")
    @Operation(summary = "Recuperar contraseña",
            description = "Método para recuperar contraseña de un usuario",
            tags = {"auth"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Recurso no encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
            })
    public ResponseEntity<ResponseBody<String>> recuperarContrasenia(
            @Parameter(schema = @Schema(implementation = RecoveryPasswordDto.class), description = "Request para cambio de contraseña")
            @RequestBody RecoveryPasswordDto data) {
        try {
            return ok(ApiUtil.buildResponseWithDefaults(this.userService.recuperarPassword(data)));
        } catch (OperationException e) {
            throw ApiResponseException.badRequest("Cambio contraseña", e.getMessage());
        } catch (Exception e) {
            log.error("Se genero un error al recuperar contraseña", e);
            throw ApiResponseException.serverError(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }


    private String validateAuthData(AuthenticationDto data) {
        AuthUser authAuthUser = authUserRepository.findByUsername(data.getUsername()).orElseThrow(() -> new BadCredentialsException("Credenciales incorrectas."));
        if (authAuthUser.getUserStatus() == UserStatus.BLOQUEADO) {
            throw new OperationException("Cuenta bloqueada, comuníquese con el aministrador.");
        }
        return this.dbLogin(data, authAuthUser);
    }


    private String dbLogin(AuthenticationDto data, AuthUser authAuthUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.createToken(data.getUsername(), authAuthUser);
        } catch (AuthenticationException e) {
            log.error("Error al verificar las credenciales", e);
            throw new BadCredentialsException("Las credenciales son incorrectas");
        } catch (Exception e) {
            log.error("Error de autentificacion: ", e);
            throw e;
        }
    }


}
