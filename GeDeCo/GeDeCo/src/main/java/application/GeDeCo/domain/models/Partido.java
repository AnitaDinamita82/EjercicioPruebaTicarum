package application.GeDeCo.domain.models;

import java.time.LocalDate;

/* Clase que representa un partido */
public class Partido {

    private Long id; /* Identificador único del partido */
    private Competicion competicion; /* Competición a la que pertenece el partido */
    private Equipo equipoLocal; /* Equipo que juega comolocal */
    private Equipo equipoVisitante; /* Equipo que juega como visitante */
    private LocalDate fechaPartido; /* El dia que se juega el partido, debe de ser una fecha reservada */
    private Integer pistaAsignada; /* Pista asignada para el partido */
    private String turno; /* Turno del partido (mañana o tarde) */

    public Partido() {
    }

    public Partido(Long id, Competicion competicion, Equipo equipoLocal, Equipo equipoVisitante,
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

    public Competicion getCompeticion() {
        return competicion;
    }

    public void setCompeticion(Competicion competicion) {
        this.competicion = competicion;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
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

}