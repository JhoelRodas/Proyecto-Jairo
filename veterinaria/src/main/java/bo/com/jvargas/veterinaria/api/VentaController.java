package bo.com.jvargas.veterinaria.api;


import bo.com.jvargas.veterinaria.datos.model.DetalleProducto;
import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.Recibo;
import bo.com.jvargas.veterinaria.datos.model.dto.VentaDto;
import bo.com.jvargas.veterinaria.datos.repository.inventario.ProductoRepositoy;
import bo.com.jvargas.veterinaria.negocio.inventario.ProductoService;
import bo.com.jvargas.veterinaria.negocio.ventas.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/venta")
public class VentaController {
    private final VentaService ventaService;
    private final ProductoRepositoy productoRepositoy;

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
