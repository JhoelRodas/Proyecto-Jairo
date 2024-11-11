package bo.com.jvargas.veterinaria.datos.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoDto {

    private String tipoRespaldo;
    private String rutaArchivo;
    private String nombre;
    private byte[] bytesArchivo;
    private String base64Archivo;
}
