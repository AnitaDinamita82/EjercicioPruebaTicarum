package application.GeDeCo.adapters.dtos;

import java.time.LocalDate;

import application.GeDeCo.adapters.entitys.CompeticionEntity;

/* Data Transfer Object for Competicion  */
public class CompeticionDTO {

    private Long id; /* Identificador único de la competición */
    private String nombre; /* Nombre de la competición */
    private String deporte; /* Deporte de la competición */
    private LocalDate fechaInicio; /* Fecha de inicio de la competición */
    private LocalDate fechaFin; /* Fecha de fin de la competición */
    private Integer numPistas; /* Número de pistas disponibles para la competición */
    private String diasReservados; /* Días reservados para la competición */

    public CompeticionDTO() {
    }

    public CompeticionDTO(Long id, String nombre, String deporte, LocalDate fechaInicio, LocalDate fechaFin,
            Integer numPistas, String diasReservados) {
        this.id = id;
        this.nombre = nombre;
        this.deporte = deporte;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numPistas = numPistas;
        this.diasReservados = diasReservados;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getNumPistas() {
        return numPistas;
    }

    public void setNumPistas(Integer numPistas) {
        this.numPistas = numPistas;
    }

    public String getDiasReservados() {
        return diasReservados;
    }

    public void setDiasReservados(String diasReservados) {
        this.diasReservados = diasReservados;
    }

    /* Método para convertir el DTO a una entidad */
    public CompeticionEntity toDTOaEntity() {
        return new CompeticionEntity(
                this.id,
                this.nombre,
                this.deporte,
                this.fechaInicio,
                this.fechaFin,
                this.numPistas,
                this.diasReservados);
    }

}
