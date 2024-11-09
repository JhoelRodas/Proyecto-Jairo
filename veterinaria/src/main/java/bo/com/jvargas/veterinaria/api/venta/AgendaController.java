package bo.com.jvargas.veterinaria.api.venta;

import bo.com.jvargas.veterinaria.datos.model.dto.AgendaDto;
import bo.com.jvargas.veterinaria.negocio.ventas.AgendaService;
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
@RequestMapping("/api/agenda")
public class AgendaController {

    private final AgendaService agendaService;

    @GetMapping
    public ResponseEntity<?> listarAgendas() {
        return ResponseEntity.ok(agendaService.listar());
    }

    @PostMapping
    public ResponseEntity<?> registrarAgenda(@RequestBody AgendaDto agenda) {
        try {
            agendaService.registrar(agenda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizarAgenda(
            @RequestParam("id") Long id,
            @RequestBody AgendaDto agenda) {
        try {
            agendaService.actualizar(id, agenda);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable ("id") Long id) {
        try {
            agendaService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
