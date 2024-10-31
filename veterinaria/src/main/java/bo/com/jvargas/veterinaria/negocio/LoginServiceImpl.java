package bo.com.jvargas.veterinaria.negocio;

import bo.com.jvargas.veterinaria.datos.model.Usuario;
import bo.com.jvargas.veterinaria.datos.model.dto.Login;
import bo.com.jvargas.veterinaria.datos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("loginService")

public class LoginServiceImpl implements LoginService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public Login login(String usuario, String clave) {
        Usuario usuarioEnt = usuarioRepository.findByNombre(usuario);
        if (usuarioEnt == null || !usuarioEnt.getContrasenia().equals(clave)) {
            return null;
        }
        return Login.builder()
                .usuario(usuario)
                .password(clave)
                .token("Token de acceso")
                .build();
    }
}
