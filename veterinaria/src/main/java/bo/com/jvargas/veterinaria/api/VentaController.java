package bo.com.jvargas.veterinaria.api;


import bo.com.jvargas.veterinaria.datos.repository.inventario.ProductoRepository;
import bo.com.jvargas.veterinaria.negocio.ventas.ReciboService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/venta")
public class VentaController {
    private final ReciboService reciboService;
    private final ProductoRepository productoRepository;

//    @PostMapping
//    public ResponseEntity<List<VentaDto>> getListDetalleRecibo(@RequestBody VentaDto ventaDto){
//        Producto producto = new Producto();
//        producto.setId(ventaDto.getIdProducto());
//
//        Recibo recibo = new Recibo();
//        recibo.setId(ventaDto.getIdRecibo());
//
//        List<VentaDto> ventaDtoList = new ArrayList<>();
//        VentaDto result = new VentaDto();
//        result.setIdProducto(ventaDto.getIdProducto());
//        result.setIdRecibo(ventaDto.getIdRecibo());
//        result.setCant(ventaDto.getCant());
//        result.setMonto(ventaDto.getMonto());
//
//        ventaDtoList.add(result);
//
//
//        return ResponseEntity.ok(ventaDtoList);
//
//    }
}
