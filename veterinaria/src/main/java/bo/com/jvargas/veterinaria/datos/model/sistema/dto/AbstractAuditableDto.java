package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.AuditableEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractAuditableDto implements Serializable, Cloneable {
    protected Date createdDate;
    protected Date modifiedDate;
    protected String createdBy;
    protected String modifiedBy;
    protected Long version;
    protected boolean deleted;

    public AbstractAuditableDto(AuditableEntity entity) {
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.createdBy = entity.getCreatedBy();
        this.modifiedBy = entity.getModifiedBy();
        this.version = entity.getVersion();
        this.deleted = entity.isDeleted();
    }
}
