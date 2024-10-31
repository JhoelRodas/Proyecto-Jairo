package bo.com.jvargas.veterinaria.datos.model.sistema;

import bo.com.jvargas.veterinaria.datos.model.AuditableEntity;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoAuth;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTH_USER")
public class AuthUser extends AuditableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    @Comment("Identificador único auto generado del registro.")
    private Long id;

    @Basic
    @Column(name = "USERNAME", nullable = true, length = 20)
    @Comment("Define el usuario para inicio de sesión en el sistema.")
    private String username;

    @Basic
    @Column(name = "NAME",length = 100)
    @Comment("Define el nombre del usuario.")
    private String name;

    @Basic
    @Column(name = "LASTNAME", nullable = true, length = 100)
    @Comment("Define el segundo nombre del usuario.")
    private String lastname;

    @Column(name = "USER_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @Comment("Define el estado del usuario.")
    private UserStatus userStatus;

    @Basic
    @Column(name = "EMAIL", length = 250)
    @Comment("Define la el correo del usuario.")
    private String email;

    @Column(name = "GENERATED_PASSWORD", nullable = true)
    @Comment("Define si la contraseña del usuario ha sido generada.")
    private boolean generatedPassword = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_AUTH")
    @Comment("Define el tipo de usuario.")
    private TipoAuth tipoAuth = TipoAuth.DB;

    @Basic
    @JsonIgnore
    @Column(name = "PASSWORD", nullable = false, length = 500)
    @Comment("Define la contraseña cifrada par inicio de sesión.")
    private String password;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_AUTH_ROLE", nullable = false)
    @Comment("Identificador único auto generado del registro.")
    private AuthRole idAuthRole;

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(idAuthRole.getName()));
        return authorities ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.userStatus == UserStatus.ACTIVO;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted();
    }
}
