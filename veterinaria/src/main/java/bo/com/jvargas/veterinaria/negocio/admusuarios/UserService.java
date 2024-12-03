package bo.com.jvargas.veterinaria.negocio.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.RecoveryPasswordDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserPassword;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.v1.ResourceDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    AuthUser findUserByUsername(String username);

    String changePassword(AuthUser authAuthUser, UserPassword password);

    String recuperarPassword(RecoveryPasswordDto request);

    List<ResourceDto> userResourceList(String username);

    List<UserDto> getUserList(String filter, Pageable pageable);

    AuthUser createUser(UserDto userDto);

    AuthUser updateUser(UserDto userDto, Long userId);

    void deleteUser(Long userId);

    AuthUser updateUserStatus(Long userId, UserStatus userStatus);

    UserDto userInfo(String username);

}

