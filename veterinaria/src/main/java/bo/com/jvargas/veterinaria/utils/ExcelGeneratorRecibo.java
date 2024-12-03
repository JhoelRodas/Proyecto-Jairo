package bo.com.jvargas.veterinaria.utils;

import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReporteRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelGeneratorRecibo {

    public static byte[] generarReporteExcelRecibos(List<ReciboDto> recibos, ReporteRequest reporteRequest) throws IOException {
        // Crear un libro de trabajo de Excel (XSSFWorkbook)
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte de Recibos");

        // Establecer un estilo para todo el documento
        CellStyle commonStyle = crearEstiloComun(workbook);
        CellStyle titleStyle = crearEstiloTitulo(workbook);
        CellStyle headerStyle = crearEstiloCabecera(workbook);
        CellStyle dateCellStyle = crearEstiloFecha(workbook);
        CellStyle numberCellStyle = crearEstiloNumero(workbook);

        // Crear una fila para el título
        Row tituloRow = sheet.createRow(0);
        Cell tituloCell = tituloRow.createCell(0);
        tituloCell.setCellValue("Reporte de Recibos - Veterinaria");
        tituloCell.setCellStyle(titleStyle);

        // Fusión de celdas para el título (ocupa toda la primera fila)
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, reporteRequest.getColumnas().size() - 1));

        // Crear una fila para las cabeceras de la tabla
        Row headerRow = sheet.createRow(1);
        List<String> columnas = reporteRequest.getColumnas();
        int colIndex = 0;

        // Agregar las cabeceras a la fila
        for (String columna : columnas) {
            Cell cell = headerRow.createCell(colIndex++);
            cell.setCellValue(columna.toUpperCase());
            cell.setCellStyle(headerStyle);
        }

        // Crear las filas para los datos de los recibos
        int rowIndex = 2; // Empezamos después de las cabeceras
        for (ReciboDto recibo : recibos) {
            Row dataRow = sheet.createRow(rowIndex++);
            colIndex = 0;
            for (String columna : columnas) {
                Cell cell = dataRow.createCell(colIndex++);
                String valor = obtenerValorColumna(recibo, columna, dateCellStyle, numberCellStyle);
                cell.setCellValue(valor);
                cell.setCellStyle(commonStyle);
            }
        }

        // Autoajustar el ancho de las columnas
        for (int i = 0; i < columnas.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Convertir el libro de trabajo a un arreglo de bytes (ByteArrayOutputStream)
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }

    private static CellStyle crearEstiloTitulo(Workbook workbook) {
        CellStyle estiloTitulo = workbook.createCellStyle();
        Font fontTitulo = workbook.createFont();
        fontTitulo.setBold(true);
        fontTitulo.setFontHeightInPoints((short) 14);
        estiloTitulo.setFont(fontTitulo);
        estiloTitulo.setAlignment(HorizontalAlignment.CENTER);
        estiloTitulo.setVerticalAlignment(VerticalAlignment.CENTER);
        estiloTitulo.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        estiloTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return estiloTitulo;
    }

    private static CellStyle crearEstiloCabecera(Workbook workbook) {
        CellStyle estiloCabecera = workbook.createCellStyle();
        Font fontCabecera = workbook.createFont();
        fontCabecera.setBold(true);
        estiloCabecera.setFont(fontCabecera);
        estiloCabecera.setAlignment(HorizontalAlignment.CENTER);
        estiloCabecera.setVerticalAlignment(VerticalAlignment.CENTER);
        estiloCabecera.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estiloCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloCabecera.setBorderTop(BorderStyle.THIN);
        estiloCabecera.setBorderBottom(BorderStyle.THIN);
        estiloCabecera.setBorderLeft(BorderStyle.THIN);
        estiloCabecera.setBorderRight(BorderStyle.THIN);
        return estiloCabecera;
    }

    private static CellStyle crearEstiloComun(Workbook workbook) {
        CellStyle estiloComun = workbook.createCellStyle();
        estiloComun.setBorderTop(BorderStyle.THIN);
        estiloComun.setBorderBottom(BorderStyle.THIN);
        estiloComun.setBorderLeft(BorderStyle.THIN);
        estiloComun.setBorderRight(BorderStyle.THIN);
        return estiloComun;
    }

    private static CellStyle crearEstiloFecha(Workbook workbook) {
        CellStyle estiloFecha = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        estiloFecha.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        return estiloFecha;
    }

    private static CellStyle crearEstiloNumero(Workbook workbook) {
        CellStyle estiloNumero = workbook.createCellStyle();
        estiloNumero.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        return estiloNumero;
    }

    private static String obtenerValorColumna(ReciboDto recibo, String columna, CellStyle dateCellStyle, CellStyle numberCellStyle) {
        switch (columna.toLowerCase()) {
            case "id":
                return String.valueOf(recibo.getId());
            case "fecha":
                return recibo.getFecha() != null ? recibo.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
            case "nombre":
                return recibo.getNombre();
            case "ci":
                return recibo.getCi();
            case "monto total":
                return recibo.getMontoTotal() != null ? recibo.getMontoTotal().toString() : "";
            case "metodo pago":
                return recibo.getMetodoPago();
            default:
                return "";
        }
    }
}
