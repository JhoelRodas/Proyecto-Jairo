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
public class DetalleProductoId implements java.io.Serializable {
    private static final long serialVersionUID = -7891166526364725972L;
    @Column(name = "id_recibo", nullable = false)
    private Integer idRecibo;

    @Column(name = "id_producto", nullable = false)
    private Short idProducto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DetalleProductoId entity = (DetalleProductoId) o;
        return Objects.equals(this.idRecibo, entity.idRecibo) &&
                Objects.equals(this.idProducto, entity.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecibo, idProducto);
    }

}