package bo.com.jvargas.veterinaria.api.compra;

import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDto;
import bo.com.jvargas.veterinaria.negocio.compra.NotaCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/notacompra")
public class NotaCompraController {
    private final NotaCompraService service;

    @GetMapping
    public ResponseEntity<List<NotaCompraDto>> listar() {
        List<NotaCompraDto> notas = service.listar();
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/get")
    public ResponseEntity<?> obtenerNotaCompra(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(service.verNotaDeCompra(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarNotaCompra(
            @RequestBody NotaCompraDetalleDto notaCompraDetalleDto) {
        NotaCompraDto notaCompraGuardada = service.guardar(notaCompraDetalleDto);

        if (notaCompraGuardada == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(notaCompraGuardada);
    }

    @PutMapping
    public ResponseEntity<NotaCompraDto> actualizarNotaCompra(
            @RequestParam ("id") Long id,
            @RequestBody NotaCompraDto notaCompraDto) {

        Optional<NotaCompraDto> notaCompraActualizada =
                service.actualizar(id, notaCompraDto);

        if (notaCompraActualizada.isPresent())
            return ResponseEntity.ok(notaCompraActualizada.get());
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarNotaCompra(@RequestParam ("id") Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
