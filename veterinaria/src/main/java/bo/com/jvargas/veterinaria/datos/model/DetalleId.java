package bo.com.jvargas.veterinaria.datos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetalleId implements java.io.Serializable {
    private static final long serialVersionUID = 9020823462110261831L;
    @Column(name = "id_producto", nullable = false)
    private Long idProducto;

    @Column(name = "id_nota_compra", nullable = false)
    private Long idNotaCompra;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DetalleId entity = (DetalleId) o;
        return Objects.equals(this.idProducto, entity.idProducto) &&
                Objects.equals(this.idNotaCompra, entity.idNotaCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, idNotaCompra);
    }

}