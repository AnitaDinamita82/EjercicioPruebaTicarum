package application.GeDeCo.adapters.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "competiciones")
public class CompeticionEntity implements java.io.Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre", unique = true, nullable = false, length = 100)
    private String nombre;

    @Column(name = "deporte", unique = true, nullable = false, length = 50)
    private String deporte;

    @Column(name = "fecha_inicio", nullable = false)
    private java.time.LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private java.time.LocalDate fechaFin;

    @Column(name = "num_pistas", nullable = false)
    private Integer numPistas;

    @Column(name = "dias_reservados", nullable = false)
    private String diasReservados;

    public CompeticionEntity() {
    }

    public CompeticionEntity(Long id, String nombre, String deporte, java.time.LocalDate fechaInicio,
            java.time.LocalDate fechaFin, Integer numPistas, String diasReservados) {
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

    public java.time.LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(java.time.LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public java.time.LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(java.time.LocalDate fechaFin) {
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

}
