package bo.com.jvargas.veterinaria.datos.model;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "atencion")
public class Atencion extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    @Column(name = "anamnesis", nullable = false, length = 100)
    private String anamnesis;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota idMascota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_historial")
    private HistorialClinico idHistorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTH_USER")
    private AuthUser idUsuario;
}