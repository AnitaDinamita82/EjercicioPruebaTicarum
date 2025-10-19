package application.GeDeCo.domain.models;

/* Clase que representa un equipo */
public class Equipo {

    private Long id; /* Identificador único del equipo */
    private String nombre; /* Nombre del equipo */

    public Equipo() {
    }

    public Equipo(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;

    }
    /* Getters y Setters */

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

}
