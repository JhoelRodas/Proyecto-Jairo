package bo.com.jvargas.veterinaria.api.venta;

import bo.com.jvargas.veterinaria.datos.model.dto.HistorialClinicoDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReporteRequest;
import bo.com.jvargas.veterinaria.negocio.ventas.ReciboService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/*lo que agregue */
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/recibo")
public class ReciboController {

    private final ReciboService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.listarRecibos());
    }

    @GetMapping("/getRecibo")
    public ResponseEntity<?> obtenerRecibo(@RequestParam("id") Long id) {
        try {
            ReciboDetalleDto recibo = service.verRecibo(id);
            return ResponseEntity.ok(recibo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody ReciboDetalleDto nuevoRecibo) {
        try {
            service.guardarRecibo(nuevoRecibo);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> anular(@PathVariable Long id) {
        try {
            service.anularRecibo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/reporte")
    public ResponseEntity<?> generarReporteRecibos(@RequestBody ReporteRequest reporteRequest) {
        try {
            byte[] reporteBytes = service.generarReporteRecibos(reporteRequest);
    
            // Determinar el tipo de contenido y el nombre del archivo según el formato
            HttpHeaders headers = new HttpHeaders();
            if ("excel".equalsIgnoreCase(reporteRequest.getFormato())) {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  // Tipo para archivos binarios (excel)
                headers.setContentDispositionFormData("attachment", "reporte_recibos.xlsx");
            } else {
                headers.setContentType(MediaType.APPLICATION_PDF);  // Tipo para PDF
                headers.setContentDispositionFormData("attachment", "reporte_recibos.pdf");
            }
    
            // Devolver la respuesta con el reporte y las cabeceras adecuadas
            return new ResponseEntity<>(reporteBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Si ocurre un error, devolver un mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
    
}
