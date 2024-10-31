package bo.com.jvargas.veterinaria.negocio;

import bo.com.jvargas.veterinaria.datos.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> lista();
    void registrar(Usuario usuario);
    void actualizar(Long id, Usuario usuario);
    void eliminar(Long id);
}
