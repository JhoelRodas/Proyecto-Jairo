package bo.com.jvargas.veterinaria.api.inventario;

import bo.com.jvargas.veterinaria.datos.model.dto.CategoriaDto;
import bo.com.jvargas.veterinaria.negocio.inventario.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/categoria")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listarCategorias());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody CategoriaDto nuevaCategoria) {
        try {
            service.guardarCategoria(nuevaCategoria);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizar(
            @RequestParam Long id,
            @RequestBody CategoriaDto nuevaCategoria) {
        try {
            service.actualizarCategoria(id, nuevaCategoria);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminarCategoria(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
