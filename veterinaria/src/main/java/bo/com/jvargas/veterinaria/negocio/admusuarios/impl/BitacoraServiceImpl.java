package bo.com.jvargas.veterinaria.negocio.admusuarios.impl;

import bo.com.jvargas.veterinaria.datos.model.sistema.Bitacora;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.NivelLog;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;
import bo.com.jvargas.veterinaria.datos.repository.sistema.BitacoraRepository;
import bo.com.jvargas.veterinaria.negocio.admusuarios.BitacoraService;
import bo.com.jvargas.veterinaria.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service("bitacoraService")
public class BitacoraServiceImpl implements BitacoraService {
    private final BitacoraRepository repository;
    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Async
    public void info(TipoProceso processType, String mensaje, Object... arguments) {
        if (arguments != null && arguments.length > 0) {
            FormattingTuple tp = MessageFormatter.arrayFormat(mensaje, arguments);
            mensaje = tp.getMessage();
            if (tp.getThrowable() != null) {
                try {
                    mensaje += "\n\n" + ExceptionUtils.getStackTrace(tp.getThrowable());
                } catch (Exception e) {
                    mensaje += "\n\n No se obtuvo el stacktrace de error: " + tp.getThrowable().getMessage();
                }
            }
        }
        String ipDispositivo = obtenerDispositivo();
        this.repository.save(Bitacora.builder()
                .ip(ipDispositivo)
                .tipoProceso(processType)
                .nivelLog(NivelLog.INFO)
                .log(mensaje)
                .build());
    }
    private String obtenerDispositivo() {
        String ip = request.getRemoteAddr();

        // Si estás trabajando en localhost
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }

        String tipoDispositivo = obtenerTipoDispositivo();
//        String userAgent = request.getHeader("User-Agent");


        // Aquí puedes formatear la información que desees
        return "IP: " + ip + ", Tipo de dispositivo: "+ tipoDispositivo ;
//                + ", Navegador/Dispositivo: " + userAgent;
    }

    private String obtenerTipoDispositivo() {
        String userAgent = request.getHeader("User-Agent").toLowerCase();

        if (userAgent.contains("mobile") || userAgent.contains("android") || userAgent.contains("iphone")) {
            return "Teléfono Móvil";
        } else if (userAgent.contains("tablet")) {
            return "Tablet";
        } else if (userAgent.contains("windows") || userAgent.contains("mac") || userAgent.contains("linux")) {
            return "Computadora de Escritorio o Laptop";
        } else {
            return "Dispositivo Desconocido";
        }
    }

    @Override
//    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void error(TipoProceso processType, String mensaje, Object... arguments) {
        if (arguments != null && arguments.length > 0) {
            FormattingTuple tp = MessageFormatter.arrayFormat(mensaje, arguments);
            mensaje = tp.getMessage();
            if (tp.getThrowable() != null) {
                try {
                    mensaje += "\n\n" + ExceptionUtils.getStackTrace(tp.getThrowable());
                } catch (Exception e) {
                    mensaje += "\n\n No se obtuvo el stacktrace de error: " + tp.getThrowable().getMessage();
                }
            }
        }
        this.repository.save(Bitacora.builder()
                .ip(obtenerDispositivo())
                .tipoProceso(processType)
                .nivelLog(NivelLog.ERROR)
                .log(mensaje)
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bitacora> listar(Date inicio, Date fin, TipoProceso proceso, String coincidencia) {
        coincidencia = "%" + coincidencia + "%";
        if (inicio != null) inicio = DateUtil.formatToStart(inicio);
        if (fin != null) fin = DateUtil.formatToEnd(fin);
        return this.repository.listByFilters(inicio, fin, proceso, coincidencia);
    }
}
