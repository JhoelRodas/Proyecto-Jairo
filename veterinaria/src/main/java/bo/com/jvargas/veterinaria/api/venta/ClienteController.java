package bo.com.jvargas.veterinaria.api.venta;

import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.dto.ClienteDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;
import bo.com.jvargas.veterinaria.negocio.ventas.ClienteService;
import bo.com.jvargas.veterinaria.negocio.admusuarios.BitacoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    private final BitacoraService bitacoraService;

    @GetMapping()
    public ResponseEntity<List<ClienteDto>> listar() {
        return ResponseEntity.ok(clienteService.lista());
    }

    @PostMapping()
    public ResponseEntity<Void> registrarCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.registrar(cliente);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            bitacoraService.error(TipoProceso.GESTIONAR_CLIENTE, "Error al registrar cliente: {}", cliente.getCi(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping()
    public ResponseEntity<Void> actualizarCliente(@RequestParam("id") Long id , @RequestBody Cliente cliente) {
        clienteService.actualizar(id, cliente);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable("id") Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.ok().build();
    }




}
