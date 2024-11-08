package bo.com.jvargas.veterinaria.api.inventario;

import bo.com.jvargas.veterinaria.datos.model.dto.EstanteDto;
import bo.com.jvargas.veterinaria.negocio.inventario.EstanteService;
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
@RequestMapping("/api/estante")
public class EstanteController {

    private final EstanteService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listarEstantes());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody EstanteDto estanteNuevo) {
        service.guardarEstante(estanteNuevo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestParam("id") Long id,
                                        @RequestBody EstanteDto estanteNuevo) {
        try {
            service.actualizarEstante(id, estanteNuevo);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminarEstante(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
