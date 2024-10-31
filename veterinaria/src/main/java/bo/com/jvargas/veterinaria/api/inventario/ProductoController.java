package bo.com.jvargas.veterinaria.api.inventario;

import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.dto.ClienteDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;
import bo.com.jvargas.veterinaria.negocio.inventario.ProductoService;
import bo.com.jvargas.veterinaria.negocio.sistema.BitacoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/producto")
public class ProductoController {
    private final ProductoService productoService;
 //   private final BitacoraSer vice bitacoraService;

    @GetMapping()
    public ResponseEntity<List<ProductoDto>> listar() {
        return ResponseEntity.ok(productoService.lista());
    }

    @PostMapping()
    public ResponseEntity<Void> registrarProducto(@RequestBody ProductoDto producto) {
            productoService.registar(producto);
            return ResponseEntity.ok().build();

    }

    @PutMapping()
    public ResponseEntity<Void> actualizarProducto(@RequestParam("id") Long id , @RequestBody Producto producto) {
        productoService.actualizar(id, producto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable("id") Long id) {
        productoService.eliminar(id);
        return ResponseEntity.ok().build();
    }

}
