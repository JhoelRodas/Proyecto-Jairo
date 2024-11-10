package bo.com.jvargas.veterinaria.datos.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "historial_clinico")
public class HistorialClinico
        extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "fc")
    private Integer fc;

    @Column(name = "estado_fc", length = 15)
    private String estadoFc;

    @Column(name = "fr")
    private Integer fr;

    @Column(name = "estado_fr", length = 8)
    private String estadoFr;

    @Column(name = "mucosa", length = 10)
    private String mucosa;

    @Column(name = "apetito", length = 15)
    private String apetito;

    @Column(name = "hidratacion", length = 10)
    private String hidratacion;

    @Column(name = "estado_general", length = 10)
    private String estadoGeneral;

    @OneToOne(mappedBy = "idHistorial", fetch = FetchType.LAZY)
    private Mascota mascota;
}