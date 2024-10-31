package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoAuth;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String contrasenia;
    private String name;
    private String lastname;
    private String fullName;
    private UserStatus userStatus;
    private String email;
    private boolean generatedPassword;
    private Long idAuthRole;
    private String rolName;
    private String telefono;
    private TipoAuth tipoAuth;
    private Long idGerencia;
    private List<Integer> idGerenciaList;

    public UserDto(Long id, String username, String name, String lastname, String fullName, UserStatus userStatus, String email, boolean generatedPassword, Long idAuthRole, String rolName, String telefono, TipoAuth tipoAuth) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.fullName = fullName;
        this.userStatus = userStatus;
        this.email = email;
        this.generatedPassword = generatedPassword;
        this.idAuthRole = idAuthRole;
        this.rolName = rolName;
        this.telefono = telefono;
        this.tipoAuth = tipoAuth;
    }

    public static UserDto fromAuthUser(AuthUser authUser) {
        return UserDto.builder()
                .id(authUser.getId())
                .username(authUser.getUsername())
                .name(authUser.getName())
                .lastname(authUser.getLastname())
                .fullName(authUser.getName() + " " + authUser.getLastname())
                .userStatus(authUser.getUserStatus())
                .email(authUser.getEmail())
                .generatedPassword(authUser.isGeneratedPassword())
                .idAuthRole(authUser.getIdAuthRole().getId())
                .rolName(authUser.getIdAuthRole().getName())
                .build();
    }
}
