package bo.com.jvargas.veterinaria.api.venta;

import bo.com.jvargas.veterinaria.datos.model.Servicio;
import bo.com.jvargas.veterinaria.datos.model.dto.ServicioDto;
import bo.com.jvargas.veterinaria.negocio.ventas.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/servicio")
public class ServiceController {

    private final ServicioService service;

    @GetMapping
    public ResponseEntity<?> listarServicios() {
        return ResponseEntity.ok(service.listarServicios());
    }

    @PostMapping
    public ResponseEntity<?> guardarServicio(@RequestBody ServicioDto servicioNuevo) {
        Optional<ServicioDto> o = service.crearServicio(servicioNuevo);
        if (o.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
    }

    @PutMapping
    public ResponseEntity<?> actualizarServicio(
            @RequestParam("id") Long id,
            @RequestBody ServicioDto servicioNuevo) {
        try {
            Optional<ServicioDto> o = service.actualizarServicio(id, servicioNuevo);
            return ResponseEntity.ok(o.orElseThrow());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Long id) {
        try {
            service.elimiarServicio(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
