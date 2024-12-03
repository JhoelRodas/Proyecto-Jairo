package bo.com.jvargas.veterinaria.api.venta;

import bo.com.jvargas.veterinaria.datos.model.Mascota;
import bo.com.jvargas.veterinaria.datos.model.dto.MascotaDto;
import bo.com.jvargas.veterinaria.negocio.ventas.MascotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/mascota")
public class MascotaController {
    private final MascotaService mascotaService;

    @GetMapping()
    public ResponseEntity<List<MascotaDto>> listar() {
        return ResponseEntity.ok(mascotaService.lista());
    }

    @PostMapping()
    public ResponseEntity<Void> registro(@RequestBody MascotaDto mascota) {
        mascotaService.registrar(mascota);
        return ResponseEntity.ok().build();
    }

    //Query(URL) params
    @PutMapping()
    public ResponseEntity<Void> actualiza(@RequestParam("id") Long id, @RequestBody Mascota mascota) {
        mascotaService.actualizar(id, mascota);
        return ResponseEntity.ok().build();
    }

    //Path variables
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
