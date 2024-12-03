package bo.com.jvargas.veterinaria.negocio.admusuarios.impl;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthResource;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRole;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.RecoveryPasswordDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserPassword;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.v1.ResourceDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoAuth;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthResourceRepository;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthRoleRepository;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthUserRepository;
import bo.com.jvargas.veterinaria.negocio.admusuarios.UserService;
import bo.com.jvargas.veterinaria.utils.FormatUtil;
import bo.com.jvargas.veterinaria.utils.OperationException;
import bo.com.jvargas.veterinaria.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {
    // region [Injections]
    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthResourceRepository authResourceRepository;
    // endregion [Injections]

    @Override
    public AuthUser findUserByUsername(String nombre) {
        return authUserRepository.findByUsername(nombre).orElse(null);
    }

    @Override
    public String recuperarPassword(RecoveryPasswordDto request) {
//        ValidationUtil.throwExceptionIfInvalidEmail("Dirección de correo", request.getEmail(), true);
        Optional<AuthUser> usuarioOption = this.authUserRepository.findByEmailIgnoreCaseAndDeletedFalse(request.getEmail());
        if (usuarioOption.isEmpty()) {
            log.error("No se encontro ningun usuario con email: {} para recuperacion de credenciales", request.getEmail());
            throw new OperationException("No se encontro nungún usuario con la dirección de correo: " + request.getEmail() + " para recuperar contraseña");
        }
        AuthUser authUser = usuarioOption.get();
        authUser.setGeneratedPassword(true);
        String newPassword = "12345";
        authUser.setPassword(passwordEncoder.encode(newPassword));
        this.authUserRepository.save(authUser);
        String urlSistema = "";
        log.info("Notificando via email al usuario: {}, email: {}", authUser.getUsername(), request.getEmail());
        return String.format("La nueva contraseña ha sido enviado a la dirección de correo %s", request.getEmail());
    }


    @Override
    public String changePassword(AuthUser user, UserPassword password) {
//        ValidationUtil.throwExceptionIfInvalidText("Contraseña", password.getPresentPassword(), true, 0, 30);
//        ValidationUtil.throwExceptionIfInvalidText("Nueva Contraseña", password.getNewPassword(), true, 6, 30);
//        ValidationUtil.throwExceptionIfInvalidText("Confirmación", password.getConfirmPassword(), true, 6, 30);
        AuthUser authUser = this.authUserRepository.findByUsername(user.getUsername()).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Usuario", "username", user.getUsername())));

        boolean verificador = passwordEncoder.matches(password.getPresentPassword(), authUser.getPassword());
        if (!verificador) {
            log.error("Error al actualizar la contraseña del usuario: {}. Causa: Contraseña actual con la contraseña de entrada no coinciden", user.getUsername());
            throw new OperationException("Contraseña actual no es correcta, intente nuevamente");
        }

        try {
            authUser.setPassword(passwordEncoder.encode(password.getNewPassword()));
            authUser.setGeneratedPassword(false);
            authUserRepository.save(authUser);
            log.info("El usuario: {} actualizo su contraseña", user.getUsername());
            return "Contraseña actualizada correctamente";
        } catch (Exception ex) {
            log.error("Error al actualizar la contraseña del usuario: {}", user.getUsername(), ex);
            throw new OperationException("Error al actualizar la contraseña del usuario");
        }
    }

    @Override
    public List<ResourceDto> userResourceList(String username) {
        AuthUser authUser = this.authUserRepository.findByUsername(username).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado(AuthUser.class.getName(), "username", username)));
        List<AuthResource> resourceList = this.authResourceRepository.findByRole(authUser.getIdAuthRole());
        List<ResourceDto> resourceDtoList = resourceList.stream()
                .map(ResourceDto::buildFromAuthResource)
                .collect(Collectors.toList());
        List<ResourceDto> resourceDtoParentList = resourceDtoList.stream()
                .filter(item -> item.getIdResourceParent() == null)
                .collect(Collectors.toList());

        return resourceDtoParentList.stream().peek(item -> {
            item.setSubItems(resourceDtoList.stream()
                    .filter(sItem -> sItem.getIdResourceParent() != null && sItem.getIdResourceParent().equals(item.getId()))
                    .collect(Collectors.toList()));
            item.getSubItems().sort(Comparator.comparing(ResourceDto::getPosition));
        }).sorted(Comparator.comparing(ResourceDto::getPosition)).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUserList(String filter, Pageable pageable) {
        if (StringUtil.isEmptyOrNull(filter)) filter = "%%";
        else filter = "%" + filter + "%";

        return this.authUserRepository.pageWithFilter(filter.toUpperCase());
    }

    @Override
    @Transactional
    public AuthUser createUser(UserDto userDto) {
//        ValidationUtil.throwExceptionIfInvalidText("Usuario", userDto.getUsername(), true, 20);
//        ValidationUtil.throwExceptionIfInvalidText("Nombre", userDto.getName(), true, 100);
//        ValidationUtil.throwExceptionIfInvalidText("Apellidos", userDto.getLastname(), true, 100);
//        ValidationUtil.throwExceptionIfInvalidText("Correo", userDto.getEmail(), false, 100);
//        ValidationUtil.throwExceptionIfInvalidNumber("Rol", userDto.getIdAuthRole(), true, 0L);

        Optional<AuthUser> authUserOptional = this.authUserRepository.findByUsername(userDto.getUsername());
        if (authUserOptional.isPresent())
            throw new OperationException(FormatUtil.yaRegistrado("Usuario", "Usuario", userDto.getUsername()));

        AuthRole authRole = this.authRoleRepository.findById(userDto.getIdAuthRole())
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", userDto.getIdAuthRole())));

        if (userDto.getTipoAuth() == null) userDto.setTipoAuth(TipoAuth.DB);
        String psswReq = "12345";
        if (userDto.getContrasenia() != null) psswReq = userDto.getContrasenia().trim();
        AuthUser authUser = AuthUser.builder()
                .username(userDto.getUsername())
                .name(userDto.getName())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .userStatus(UserStatus.ACTIVO)
                .password(this.passwordEncoder.encode(psswReq))
                .idAuthRole(authRole)
                .tipoAuth(userDto.getTipoAuth())
                .build();
        authUserRepository.save(authUser);
        return authUser;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, OperationException.class})
    public AuthUser updateUser(UserDto userDto, Long userId) {
//        ValidationUtil.throwExceptionIfInvalidText("Nombre", userDto.getName(), true, 100);
//        ValidationUtil.throwExceptionIfInvalidText("Apellidos", userDto.getLastname(), true, 100);
//        ValidationUtil.throwExceptionIfInvalidText("Correo", userDto.getEmail(), false, 100);
//        ValidationUtil.throwExceptionIfInvalidNumber("Rol", userDto.getIdAuthRole(), true, 0L);

        AuthUser authUser = this.authUserRepository.findById(userId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Usuario", userId)));

        Optional<AuthUser> authUserOpt = this.authUserRepository.findByUsername(userDto.getUsername());
        if (authUserOpt.isPresent() && !authUserOpt.get().getId().equals(authUser.getId()))
            throw new OperationException(FormatUtil.yaRegistrado("Usuario", "Usuario", userDto.getUsername()));

        authUser.setName(userDto.getName());
        authUser.setLastname(userDto.getLastname());
        authUser.setEmail(userDto.getEmail());
        return this.authUserRepository.save(authUser);
    }

    @Override
    public void deleteUser(Long userId) {
//        ValidationUtil.throwExceptionIfInvalidNumber("User Id", userId, true, 0L);
        AuthUser authUser = this.authUserRepository.findById(userId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Usuario", userId)));
        authUser.setDeleted(true);
        authUser.setUserStatus(UserStatus.ELIMINADO);
        this.authUserRepository.save(authUser);
    }

    @Override
    public AuthUser updateUserStatus(Long userId, UserStatus userStatus) {
//        ValidationUtil.throwExceptionIfInvalidNumber("User Id", userId, true, 0L);
        if (userStatus == null) throw new OperationException(FormatUtil.requerido("Estado"));

        AuthUser authUser = this.authUserRepository.findById(userId)
                .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Usuario", userId)));
        authUser.setUserStatus(userStatus);
        return this.authUserRepository.save(authUser);
    }

    @Override
    public UserDto userInfo(String username) {
        AuthUser authUser = this.authUserRepository.findByUsername(username).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado(AuthUser.class.getName(), "username", username)));
        return UserDto.builder()
                .id(authUser.getId())
                .name(authUser.getName())
                .lastname(authUser.getLastname())
                .fullName(authUser.getName() + " " + authUser.getLastname())
                .generatedPassword(authUser.isGeneratedPassword())
                .userStatus(authUser.getUserStatus())
                .email(authUser.getEmail())
                .build();
    }

}
