package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Categoria;
import lombok.*;

import javax.persistence.Entity;

/**
 * @author GERSON
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoriaDto {
    private Long id;
    private String nombre;
    private Long idEstante;
    private String estante;

    public static CategoriaDto toDto (Categoria categoria) {
        return CategoriaDto.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .estante(categoria.getIdEstante().getNombre())
                .build();
    }

    public static Categoria toEntity(CategoriaDto categoriaDto) {
        return Categoria.builder()
                .id(categoriaDto.getId())
                .nombre(categoriaDto.getNombre())
                .idEstante(null)
                .build();
    }
}
