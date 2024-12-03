package bo.com.jvargas.veterinaria.utils;

import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReporteRequest;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFGeneratorRecibo {

    public static byte[] generarReportePDFRecibos(List<ReciboDto> recibos, ReporteRequest reporteRequest) throws Exception {
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // Agregar logo
        agregarLogo(document);

        // Título del reporte
        agregarTitulo(document, "Reporte de Recibos - Veterinaria");

        // Información de resumen
        agregarResumen(document, recibos.size(), reporteRequest);

        // Línea separadora
        agregarSeparador(document);

        // Crear tabla con datos
        PdfPTable table = crearTabla(recibos, reporteRequest);

        // Agregar tabla al documento
        document.add(table);

        document.close();
        return out.toByteArray();
    }

    private static void agregarLogo(Document document) {
        try {
            String logoPath = "src\\main\\resources\\logo_veterinaria.png"; // Ruta del logo
            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 50);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }
    }

    private static void agregarTitulo(Document document, String tituloTexto) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLACK);
        Paragraph titulo = new Paragraph(tituloTexto, titleFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(10f);
        document.add(titulo);
    }

    private static void agregarResumen(Document document, int totalRecibos, ReporteRequest reporteRequest) throws DocumentException {
        Font summaryFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
        Paragraph resumen = new Paragraph("Total de recibos: " + totalRecibos, summaryFont);
        document.add(resumen);

        if (reporteRequest.getFiltros() != null && !reporteRequest.getFiltros().isEmpty()) {
            Paragraph filtrosTitulo = new Paragraph("Filtros aplicados:", summaryFont);
            filtrosTitulo.setSpacingBefore(5f);
            document.add(filtrosTitulo);

            for (var entry : reporteRequest.getFiltros().entrySet()) {
                Paragraph filtroTexto = new Paragraph(entry.getKey() + ": " + entry.getValue(), summaryFont);
                document.add(filtroTexto);
            }
        }
    }

    private static void agregarSeparador(Document document) throws DocumentException {
        LineSeparator separator = new LineSeparator(2, 100, new Color(51, 102, 153), Element.ALIGN_CENTER, -2);
        document.add(new Chunk(separator));
        document.add(new Paragraph(" "));
    }

    private static PdfPTable crearTabla(List<ReciboDto> recibos, ReporteRequest reporteRequest) throws DocumentException {
        List<String> columnas = reporteRequest.getColumnas();
        int numColumnas = columnas.size();
        PdfPTable table = new PdfPTable(numColumnas);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        float[] columnWidths = calcularAnchosColumnas(columnas);
        table.setWidths(columnWidths);

        // Encabezados de la tabla
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        for (String header : columnas) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header.toUpperCase(), headerFont));
            headerCell.setBackgroundColor(Color.GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(5);
            table.addCell(headerCell);
        }

        // Datos de la tabla
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean isEvenRow = true;
        for (ReciboDto recibo : recibos) {
            for (String columna : columnas) {
                String valor = obtenerValorColumna(recibo, columna, formatter);
                PdfPCell cell = new PdfPCell(new Phrase(valor, bodyFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(4);
                cell.setBackgroundColor(isEvenRow ? new Color(245, 245, 255) : Color.WHITE);
                table.addCell(cell);
            }
            isEvenRow = !isEvenRow;
        }
        return table;
    }

    private static float[] calcularAnchosColumnas(List<String> columnas) {
        float[] columnWidths = new float[columnas.size()];
        for (int i = 0; i < columnas.size(); i++) {
            String columna = columnas.get(i);
            columnWidths[i] = columna.equalsIgnoreCase("id") || columna.equalsIgnoreCase("ci") ? 1.0f : 2.0f;
        }
        return columnWidths;
    }

    private static String obtenerValorColumna(ReciboDto recibo, String columna, DateTimeFormatter formatter) {
        switch (columna.toLowerCase()) {
            case "id":
                return String.valueOf(recibo.getId());
            case "fecha":
                return recibo.getFecha() != null ? recibo.getFecha().format(formatter) : "N/A";
            case "nombre":
                return recibo.getNombre();
            case "ci":
                return recibo.getCi();
            case "monto total":
                return recibo.getMontoTotal() != null ? recibo.getMontoTotal().toString() : "N/A";
            case "metodo pago":
                return recibo.getMetodoPago();
            default:
                return "N/A";
        }
    }
}
