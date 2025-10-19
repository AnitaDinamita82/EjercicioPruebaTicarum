package application.GeDeCo.adapters.entitys;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "partidos")
public class PartidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; /* Identificador único del partido */

    @Column(name = "fecha_partido", nullable = false)
    private LocalDate fechaPartido; /* El dia que se juega el partido, debe de ser una fecha reservada */

    @Column(name = "pista_asignada", nullable = false)
    private Integer pistaAsignada; /* Pista asignada para el partido */

    @Column(name = "turno", nullable = false)
    private String turno; /* Turno del partido (mañana o tarde) */

    @ManyToOne
    @JoinColumn(name = "competicion_id", nullable = false)
    private CompeticionEntity competicion; /* Competición a la que pertenece el partido */

    @ManyToOne
    @JoinColumn(name = "equipo_local_id", nullable = false)
    private EquipoEntity equipoLocal; /* Equipo que juega como local */

    @ManyToOne
    @JoinColumn(name = "equipo_visitante_id", nullable = false)
    private EquipoEntity equipoVisitante; /* Equipo que juega como visitante */

    public PartidoEntity() {
    }

    public PartidoEntity(Long id, CompeticionEntity competicion, EquipoEntity equipoLocal, EquipoEntity equipoVisitante,
            LocalDate fechaPartido, Integer pistaAsignada, String turno) {
        this.id = id;
        this.competicion = competicion;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fechaPartido = fechaPartido;
        this.pistaAsignada = pistaAsignada;
        this.turno = turno;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(LocalDate fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public Integer getPistaAsignada() {
        return pistaAsignada;
    }

    public void setPistaAsignada(Integer pistaAsignada) {
        this.pistaAsignada = pistaAsignada;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public CompeticionEntity getCompeticion() {
        return competicion;
    }

    public void setCompeticion(CompeticionEntity competicion) {
        this.competicion = competicion;
    }

    public EquipoEntity getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(EquipoEntity equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public EquipoEntity getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(EquipoEntity equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

}
