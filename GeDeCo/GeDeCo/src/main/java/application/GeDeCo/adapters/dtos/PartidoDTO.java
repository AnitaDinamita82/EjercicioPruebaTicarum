package application.GeDeCo.adapters.dtos;

import java.time.LocalDate;

import application.GeDeCo.adapters.entitys.PartidoEntity;

public class PartidoDTO {

    private Long competicionID;
    private String equipoLocal;
    private String equipoVisitante;
    private LocalDate fechaPartido;
    private Integer pistaAsignada;
    private String turno;

    public PartidoDTO() {
    }

    public PartidoDTO(Long competicionID, String equipoLocal, String equipoVisitante, LocalDate fechaPartido,
            Integer pistaAsignada, String turno) {
        this.competicionID = competicionID;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fechaPartido = fechaPartido;
        this.pistaAsignada = pistaAsignada;
        this.turno = turno;
    }

    public Long getCompeticionID() {
        return competicionID;
    }

    public void setCompeticionID(Long competicionID) {
        this.competicionID = competicionID;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(String equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(String equipoVisitante) {
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

    /* Metodo de conversion de Entity a DTO */
    public static PartidoDTO toEntityaDTO(PartidoEntity partidoEntity) {
        return new PartidoDTO(
                partidoEntity.getCompeticion().getId(),
                partidoEntity.getEquipoLocal().getNombre(),
                partidoEntity.getEquipoVisitante().getNombre(),
                partidoEntity.getFechaPartido(),
                partidoEntity.getPistaAsignada(),
                partidoEntity.getTurno());
    }
}
