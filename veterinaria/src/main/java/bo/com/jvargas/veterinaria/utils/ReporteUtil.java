package bo.com.jvargas.veterinaria.utils;

import bo.com.jvargas.veterinaria.datos.model.dto.CabeceraReporteDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReporteDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ReporteUtil {
    public static void crearCabeceras(XSSFWorkbook workbook, Sheet sheet, int rowIndex, String fontStyle, short fontSize, short fontIndexColor, short bgIndexColor, boolean bold, List<String> headerList, List<Integer> widthList) {
        Row header = sheet.createRow(rowIndex);
        for (int i = 0; i < headerList.size(); i++) sheet.autoSizeColumn(i);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(bgIndexColor);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = crearFuente(workbook, fontStyle, fontSize, bold, fontIndexColor);
        headerStyle.setFont(font);

        for (int i = 0; i < headerList.size(); i++) {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headerList.get(i));
            headerCell.setCellStyle(headerStyle);
        }
    }

    public static void crearTitulo(XSSFWorkbook workbook, Sheet sheet, String titulo, int rowIndex) {
        try {
            Row row0 = sheet.createRow(rowIndex);
            for (int i = 0; i < 8; i++) row0.createCell(i);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            row0.setHeight((short) 800);

            CellStyle tituloStyle = workbook.createCellStyle();
            XSSFFont fontTitulo = crearFuente(workbook, "Arial", (short)20, true, IndexedColors.GREY_50_PERCENT.getIndex());
            tituloStyle.setFont(fontTitulo);
            row0.getCell(0).setCellStyle(tituloStyle);
            row0.getCell(0).setCellValue(titulo);

        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    public static XSSFFont crearFuente(XSSFWorkbook workbook, String fuente, short fontSize, boolean bold, short color) {
        XSSFFont fontTitulo = workbook.createFont();
        fontTitulo.setFontName(fuente);
        fontTitulo.setFontHeightInPoints(fontSize);
        fontTitulo.setBold(bold);
        fontTitulo.setColor(color);
        return fontTitulo;
    }

    public static List<String> buildDataHeaders(List<CabeceraReporteDto> cabeceraReporteDtos){
        List<CabeceraReporteDto> reporteDtos = new ArrayList<>();
        for (CabeceraReporteDto cabeceraReporteDto : cabeceraReporteDtos) {
            reporteDtos.add(CabeceraReporteDto.builder()
                    .name(cabeceraReporteDto.getName())
                    .property(cabeceraReporteDto.getProperty()).build());
        }
        return reporteDtos.stream().map(CabeceraReporteDto::getName).collect(Collectors.toList());
    }

    public static List<Integer> buildColumsWidth(int charWith, List<CabeceraReporteDto> cabeceraReporteDtos) {
        List<Integer> widthList = new ArrayList<>();
        for (CabeceraReporteDto cabeceraReporteDto : cabeceraReporteDtos) {
            widthList.add(20 * charWith);
        }
        return widthList;
    }

    public static void setDataCellReporte(Sheet sheet, List<? extends ReporteDto> data, List<CabeceraReporteDto> cabeceras, int rowIndex){
        for (ReporteDto reporteDto : data) {
            Row row = sheet.createRow(rowIndex);
            for (int j = 0; j < cabeceras.size(); j++) {
                CabeceraReporteDto cabeceraReporteDto = cabeceras.get(j);
                Cell cell = row.createCell(j);
                String valor = reporteDto.getValor(cabeceraReporteDto.getProperty());
                if (valor == null){
                    valor = "sin valor";
                }
                cell.setCellValue(valor);
            }
            rowIndex++;
        }
    }
}
