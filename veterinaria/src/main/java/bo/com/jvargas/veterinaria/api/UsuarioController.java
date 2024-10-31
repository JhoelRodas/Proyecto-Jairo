package bo.com.jvargas.veterinaria.api;

import bo.com.jvargas.veterinaria.datos.model.Usuario;
import bo.com.jvargas.veterinaria.negocio.LoginService;
import bo.com.jvargas.veterinaria.negocio.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario2")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.lista());
    }

    @PostMapping()
    public ResponseEntity<Void> registro(@RequestBody Usuario usuario) {
        usuarioService.registrar(usuario);
        return ResponseEntity.ok().build();
    }

    //Query(URL) params
    @PutMapping()
    public ResponseEntity<Void> actualiza(@RequestParam("id") Long id, @RequestBody Usuario usuario) {
        usuarioService.actualizar(id, usuario);
        return ResponseEntity.ok().build();
    }

    //Path variables
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
