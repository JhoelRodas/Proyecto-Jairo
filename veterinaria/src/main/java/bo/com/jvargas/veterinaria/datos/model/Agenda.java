package bo.com.jvargas.veterinaria.datos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "agenda")
public class Agenda extends AuditableEntity implements Serializable {
    @Id
@GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "estado", length = 10)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota idMascota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio idServicio;

}