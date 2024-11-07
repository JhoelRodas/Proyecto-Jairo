package bo.com.jvargas.veterinaria.api.venta;

import bo.com.jvargas.veterinaria.datos.model.Servicio;
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
    public ResponseEntity<?> guardarServicio(@RequestBody Servicio servicioNuevo) {
        Optional<Servicio> o = service.crearServicio(servicioNuevo);
        if (o.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
    }

    @PutMapping
    public ResponseEntity<?> actualizarServicio(
            @RequestParam("id") Long id,
            @RequestBody Servicio servicioNuevo) {
        try {
            Optional<Servicio> o = service.actualizarServicio(id, servicioNuevo);
            return ResponseEntity.ok(o.orElseThrow());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
