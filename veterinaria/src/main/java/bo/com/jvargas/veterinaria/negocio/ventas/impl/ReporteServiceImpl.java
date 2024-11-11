package bo.com.jvargas.veterinaria.negocio.ventas.impl;

import bo.com.jvargas.veterinaria.datos.model.dto.*;
import bo.com.jvargas.veterinaria.negocio.ventas.ReciboService;
import bo.com.jvargas.veterinaria.negocio.ventas.ReporteService;
import bo.com.jvargas.veterinaria.utils.OperationException;
import bo.com.jvargas.veterinaria.utils.ReporteUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.management.OperationsException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Slf4j
@RequiredArgsConstructor
@Service("reporteService")
public class ReporteServiceImpl implements ReporteService {
    private final ReciboService reciboService;
    @Override
    public DocumentoDto descargarReporte(BodyReporteDto bodyReporteDto) throws OperationsException {
        FiltroReporteDto filters = bodyReporteDto.getFiltros();
        int charWith = 255;
        List<Integer> widthList = ReporteUtil.buildColumsWidth(charWith, bodyReporteDto.getHeaders());
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(bodyReporteDto.getTipoReporte());

            int rowIndex = 0;
            ReporteUtil.crearTitulo(workbook, sheet, bodyReporteDto.getTipoReporte(), rowIndex);
            rowIndex ++;

            CellStyle tituloStyle = workbook.createCellStyle();
            XSSFFont fontTitulo = ReporteUtil.crearFuente(workbook, "Arial", (short)15, true, IndexedColors.BLACK.getIndex());
            tituloStyle.setFont(fontTitulo);

            CellStyle valorStyle = workbook.createCellStyle();
            XSSFFont fontValor = ReporteUtil.crearFuente(workbook, "Arial", (short)12, false, IndexedColors.BLACK.getIndex());
            valorStyle.setFont(fontValor);

            rowIndex ++;

            List<String> headers = ReporteUtil.buildDataHeaders(bodyReporteDto.getHeaders());

            ReporteUtil.crearCabeceras(workbook, sheet, rowIndex, "Arial", (short)10, IndexedColors.WHITE.getIndex(), IndexedColors.GREY_50_PERCENT.getIndex(), true, headers, widthList);

            XSSFFont cellFont = ReporteUtil.crearFuente(workbook, "Arial", (short)10, false, IndexedColors.BLACK.getIndex());
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(cellFont);
            cellStyle.setLocked(true);

            rowIndex ++;
            insertarData(workbook,sheet,bodyReporteDto.getHeaders(),rowIndex,filters, bodyReporteDto.getTipoReporte());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return DocumentoDto.builder()
                    .nombre("Excel.xls")
                    .tipoRespaldo("application/vnd.ms-excel")
                    .base64Archivo(new String(Base64.encodeBase64(out.toByteArray()))).build();
        } catch (IOException e) {
            log.error("Error al generar reporte Excel", e);
            throw new OperationsException("Ocurrió un error inesperado al generar reporte.");
        }
    }

    private void insertarData(XSSFWorkbook workbook, Sheet sheet, List<CabeceraReporteDto> cabeceras, int rowIndex, FiltroReporteDto filters, String tipoReporte) {
        switch (tipoReporte){
            case "Ventas":
                insertarDataReporteVentas(sheet,cabeceras,filters,rowIndex);
                break;
//            case DIRECTORA_PROSPECTO:
//                insertarDataReporteDirectoraProspecto(sheet,cabeceras,filters,rowIndex);
//                break;
            default:
                throw new OperationException(String.format("Formato de reporte: '%s' no válido.", tipoReporte));
        }

    }

    private void insertarDataReporteVentas(Sheet sheet, List<CabeceraReporteDto> cabeceras, FiltroReporteDto filters, int rowIndex){
      //  Pageable pageable = ApiUtil.buildPageableWithSort(0, 200, "id", Sort.Direction.DESC);
        List<? extends ReporteDto> dataPage = reciboService.listarRecibosReporte(filters.getFrom(), filters.getTo());
        ReporteUtil.setDataCellReporte(sheet,dataPage,cabeceras,rowIndex);
    }

}
