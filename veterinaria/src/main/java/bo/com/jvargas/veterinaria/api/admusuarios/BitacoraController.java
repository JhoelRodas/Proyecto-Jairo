package bo.com.jvargas.veterinaria.api.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.Bitacora;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;
import bo.com.jvargas.veterinaria.negocio.admusuarios.BitacoraService;
import bo.com.jvargas.veterinaria.utils.ApiResponseException;
import bo.com.jvargas.veterinaria.utils.OperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bitacora")
public class BitacoraController {
    private final BitacoraService bitacoraService;

    @GetMapping("/listar")
    public ResponseEntity<List<Bitacora>> listPageableByDates(
            @Nullable @RequestParam(value = "process") TipoProceso proceso,
            @Nullable @RequestParam("from") Date from,
            @Nullable @RequestParam("to") Date to,
            @RequestParam(value = "q", defaultValue = "") String message) {

        try {
            return ok(bitacoraService.listar(from, to, proceso, message));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar el servicio, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar el servicio: ", e);
            throw ApiResponseException.serverError("Error");
        }
    }

}
