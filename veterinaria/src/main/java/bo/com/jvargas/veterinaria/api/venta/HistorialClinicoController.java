package bo.com.jvargas.veterinaria.api.venta;

import bo.com.jvargas.veterinaria.datos.model.HistorialClinico;
import bo.com.jvargas.veterinaria.datos.model.dto.HistorialClinicoDto;
import bo.com.jvargas.veterinaria.negocio.ventas.HistorialClinicoService;
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
@RequestMapping("/api/historial")
public class HistorialClinicoController {

    private final HistorialClinicoService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listarHistoriales());
    }

    @GetMapping("/get")
    public ResponseEntity<?> obtenerHistorial(@RequestParam("id") Long id) {
        try {
            HistorialClinicoDto hitorial = service.obtenerHistorial(id);
            return ResponseEntity.ok(hitorial);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarHistorial(
            @RequestBody HistorialClinico historialNuevo) {
        service.registrarHistorial(historialNuevo);
        return ResponseEntity.accepted().build();
    }

    @PutMapping
    public ResponseEntity<?> actualizarHistorial(
            @RequestParam("id") Long id,
            @RequestBody HistorialClinico historialNuevo) {
        try {
            service.actualizarHistorial(id, historialNuevo);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> elimiarHistorial(@PathVariable Long id) {
        try {
            service.eliminarHistorial(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
