package bo.com.jvargas.veterinaria.api.compra;


import bo.com.jvargas.veterinaria.datos.model.Proveedor;
import bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ProveedorDto;
import bo.com.jvargas.veterinaria.negocio.compra.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/proveedor")
public class ProveedorController {
    private final ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorDto>> listarProductos() {
        return ResponseEntity.ok(proveedorService.listar());
    }

    @PostMapping
    public ResponseEntity<Void> registrarProveedor(@RequestBody Proveedor proveedor) {
        proveedorService.registrar(proveedor);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> actualizarProveedor(@RequestParam ("id") Long id, @RequestBody Proveedor proveedor) {
        proveedorService.actualizar(id, proveedor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable ("id") Long id) {
        proveedorService.eliminar(id);
        return ResponseEntity.ok().build();
    }



}
