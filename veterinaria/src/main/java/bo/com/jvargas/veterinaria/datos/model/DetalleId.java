package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class DetalleId implements java.io.Serializable {
    private static final long serialVersionUID = 9020823462110261831L;
    @Column(name = "id_producto", nullable = false)
    private Short idProducto;

    @Column(name = "id_nota_compra", nullable = false)
    private Integer idNotaCompra;

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