package bo.com.jvargas.veterinaria.api.compra;

import bo.com.jvargas.veterinaria.datos.model.dto.DetalleDto;
import bo.com.jvargas.veterinaria.negocio.compra.DetalleService;
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
@RequestMapping("/api/detalle")
public class DetalleController {

    private final DetalleService service;

    @GetMapping
    public ResponseEntity<?> listarDetalles() {
        List<DetalleDto> detalles = service.listarDetalles();
        return ResponseEntity.ok(detalles);
    }

    @PostMapping
    public ResponseEntity<?> guardarDetalle(@RequestBody DetalleDto detalleDto) {
        try {
            Optional<DetalleDto> detalleGuardado = service.insertarDetalle(detalleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(detalleGuardado);
        } catch (RuntimeException e) {
            // Enviar mensaje de error en caso de fallo (e.g., si la cantidad excede el stock)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> guardarDetalles(@RequestBody List<DetalleDto> detallesDto) {
        try {
            List<DetalleDto> detallesGuardados = service.insertarDetalles(detallesDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(detallesGuardados);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping
    public ResponseEntity<?> eliminarDetalle(
            @RequestParam("idProducto") Long idProducto,
            @RequestParam("idNotaCompra") Long idNotaCompra) {
        try {
            service.eliminarDetalle(idProducto, idNotaCompra);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
