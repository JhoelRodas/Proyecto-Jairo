package bo.com.jvargas.veterinaria.negocio;

import bo.com.jvargas.veterinaria.datos.model.Usuario;
import bo.com.jvargas.veterinaria.datos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("usuarioService")

public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<Usuario> lista() {
        log.info("Service Listar");
        return usuarioRepository.listar();
    }


    @Override
    public void registrar(Usuario usuario) {
        log.info("Service registrar {}", usuario);
        //validar que no exista el mismo usuario
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        usuarioRepository.save(usuario);
    }

    @Override
    public void actualizar(Long id, Usuario usuario) {
        log.info("Service actualizar {}", usuario);
        Usuario usuarioBuscado = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioBuscado.setNombre(usuario.getNombre());
        usuarioBuscado.setContrasenia(usuario.getContrasenia());
        usuarioRepository.save(usuarioBuscado);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Service eliminar {}", id);
        usuarioRepository.deleteById(id);
    }
}
