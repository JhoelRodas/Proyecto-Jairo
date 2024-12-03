package bo.com.jvargas.veterinaria.negocio.compra.impl;

import bo.com.jvargas.veterinaria.datos.model.NotaCompra;
import bo.com.jvargas.veterinaria.datos.model.Proveedor;
import bo.com.jvargas.veterinaria.datos.model.dto.DetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDto;
import bo.com.jvargas.veterinaria.datos.repository.compra.NotaCompraRepository;
import bo.com.jvargas.veterinaria.datos.repository.compra.ProveedorRepository;
import bo.com.jvargas.veterinaria.negocio.compra.DetalleService;
import bo.com.jvargas.veterinaria.negocio.compra.NotaCompraService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author GERSON
 */

@RequiredArgsConstructor
@Service("NotaCompraService")
public class NotaCompraServiceImpl implements NotaCompraService {

    private final NotaCompraRepository notaCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final DetalleService detalleService;

    @Transactional(readOnly = true)
    @Override
    public List<NotaCompraDto> listar() {
        return notaCompraRepository.findAllByDeletedFalse().stream()
                .map((NotaCompraDto::toDto))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] generarPdfNotaCompra(Long id) {
        NotaCompra notaCompra = getNotaCompra(id);  // Obtén la nota de compra
        List<DetalleDto> listaDeDetalles = detalleService.getDetalles(notaCompra.getId());

        // Crear PDF
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Agrega los datos principales de la nota de compra
            document.add(new Paragraph("Nota de Compra"));
            document.add(new Paragraph("ID de Nota-Compra: " + notaCompra.getId()));
            document.add(new Paragraph("Fecha: " + notaCompra.getFecha()));
            document.add(new Paragraph("ID Proveedor: " + (notaCompra.getIdProveedor() != null ? notaCompra.getIdProveedor().getId() : "N/A")));
            document.add(new Paragraph("Monto Total: " + notaCompra.getMontoTotal()));
            document.add(new Paragraph(" "));  // Espacio entre datos principales y tabla

            // Crear tabla de detalles
            PdfPTable table = new PdfPTable(3);  // 3 columnas: Producto, Cantidad, Monto
            table.setWidthPercentage(100);

            // Agregar encabezados a la tabla
            PdfPCell productoHeader = new PdfPCell(new Paragraph("Producto"));
            productoHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(productoHeader);

            PdfPCell cantidadHeader = new PdfPCell(new Paragraph("Cantidad"));
            cantidadHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cantidadHeader);

            PdfPCell montoHeader = new PdfPCell(new Paragraph("Monto"));
            montoHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(montoHeader);

            // Iterar sobre los detalles para llenarlos en la tabla
            for (DetalleDto detalle : listaDeDetalles) {

                table.addCell(new PdfPCell(new Paragraph(detalle.getNombreProducto() != null ? detalle.getNombreProducto() : "N/A")));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(detalle.getCantidad()))));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(detalle.getMonto()))));
            }
            // Añadir la tabla al documento
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }

        return out.toByteArray();
    }

    @Override
    @Transactional(readOnly = true)
    public NotaCompraDto verNotaDeCompra(Long id) {
        NotaCompra notaCompra = getNotaCompra(id);
        Long idNotaCompra = notaCompra.getId();
        List<DetalleDto> listaDeDetalles = detalleService
                .getDetalles(idNotaCompra);

        return NotaCompraDto.toDto2(notaCompra, listaDeDetalles);
    }

    private NotaCompra getNotaCompra(Long id) {
        return notaCompraRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la nota de compra con el ID " + id
                ));
    }

    @Transactional
    @Override
    public NotaCompraDto guardar(NotaCompraDetalleDto nuevaNotaCompraDetalle) {
        NotaCompra notaAGuardar = NotaCompraDetalleDto.toEntity(nuevaNotaCompraDetalle);
        Optional<Proveedor> optionalProveedor = buscarProveedor(nuevaNotaCompraDetalle);

        if (optionalProveedor.isEmpty())
            return null;

        Proveedor proveedor = optionalProveedor.get();
        notaAGuardar.setIdProveedor(proveedor);
        notaAGuardar.setFecha(LocalDate.now());

        // Calcula el monto total sumando los montos de cada detalle
        List<DetalleDto> detalles = nuevaNotaCompraDetalle.getDetalles();
        BigDecimal montoTotal = detalles.stream()
                .map(DetalleDto::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Establece el monto total en la nota de compra
        notaAGuardar.setMontoTotal(montoTotal);

        // Guarda la nota de compra en el repositorio
        NotaCompra notaCompraGuardada = notaCompraRepository.save(notaAGuardar);

        // Asigna el ID de la nota de compra guardada a cada detalle
        Long idNotaCompraGuardada = notaCompraGuardada.getId();
        actualizarIdNotaEnDetalle(detalles, idNotaCompraGuardada);

        // Inserta los detalles de la nota de compra
        detalleService.insertarDetalles(detalles);

        return NotaCompraDto.toDto(notaCompraGuardada);
    }

    private void actualizarIdNotaEnDetalle(List<DetalleDto> detalles,
                                           Long idNotaCompra) {
        for (DetalleDto detalleDto : detalles) {
            detalleDto.setIdNotaCompra(idNotaCompra);
        }
    }

    private Optional<Proveedor> buscarProveedor(NotaCompraDetalleDto notaCompra) {
        String nombreProveedor = notaCompra.getNombreProveedor();
        return proveedorRepository.findByNombreAndDeletedFalse(nombreProveedor);
    }

    @Transactional
    @Override
    public Optional<NotaCompraDto> actualizar(
            Long id,NotaCompraDto notaCompraAActualizar) {
        Optional<NotaCompra> o = notaCompraRepository.findByIdAndDeletedFalse(id);

        if (o.isEmpty())
            return Optional.empty();

        NotaCompra notaCompra = o.get();
        actualizarDatosNotaCompra(notaCompra, notaCompraAActualizar);

        NotaCompra notaCompraActualizada = notaCompraRepository.save(notaCompra);

        return Optional.of(NotaCompraDto.toDto(notaCompraActualizada));
    }

    private void actualizarDatosNotaCompra(NotaCompra notaCompra,
                                           NotaCompraDto notaCompraAActualizar) {
        // Actualizar los campos básicos
        notaCompra.setMontoTotal(notaCompraAActualizar.getMontoTotal());
        notaCompra.setFecha(notaCompraAActualizar.getFecha());
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        NotaCompra notaCompra = notaCompraRepository.
                findByIdAndDeletedFalse(id).orElseThrow(()->new RuntimeException(
                        "Nota de compra no encontrada o eliminada"));

        notaCompra.setDeleted(true);

        notaCompraRepository.save(notaCompra);
    }
}
