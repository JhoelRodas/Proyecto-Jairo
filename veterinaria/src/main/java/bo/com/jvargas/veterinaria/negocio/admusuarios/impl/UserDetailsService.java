package bo.com.jvargas.veterinaria.negocio.admusuarios.impl;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AuthUserRepository usuarioRepository;

    public UserDetailsService(AuthUserRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser =  this.usuarioRepository.obtenerUsuarioParaAutentificacion(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));

        return new User(
                authUser.getUsername(),
                authUser.getPassword(),
                authUser.isEnabled(),
                true,
                authUser.isAccountNonExpired(),
                true,
                authUser.getAuthorities()
        );
    }
    
}
