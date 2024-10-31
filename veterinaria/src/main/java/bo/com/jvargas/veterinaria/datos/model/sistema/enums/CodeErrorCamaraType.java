package bo.com.jvargas.veterinaria.datos.model.sistema.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :8/15/22
 * @Project onboarding-source
 * @Package :bo.com.jvargas.veterinaria.datos.model.sistema.enums
 * @Copyright :MC4
 */
@AllArgsConstructor
@Getter
public enum CodeErrorCamaraType {
    X02("X02", "Destinatario no disponible, Intente más tarde"), // Mensaje 1B
    X99("X99", "Error en recepción de respuesta"),// Mensaje 5B
    X05("X05", "XML no cumple el formato de transacción síncrona"); // Mensaje 1B
    private String code;
    private String descripcion;
}
