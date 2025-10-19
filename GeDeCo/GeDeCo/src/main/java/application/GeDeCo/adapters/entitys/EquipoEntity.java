package application.GeDeCo.adapters.entitys;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipos")
public class EquipoEntity implements java.io.Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre", unique = true, nullable = false, length = 100)
    private String nombre;

    /* Relación Muchos a Muchos con Competicion */
    @ManyToMany
    @JoinTable(name = "competicion_equipos", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "equipo_id"), inverseJoinColumns = @JoinColumn(name = "competicion_id"))
    private Set<CompeticionEntity> competiciones = new HashSet<>();

    public EquipoEntity() {
    }

    public EquipoEntity(Long id, String nombre) {
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

    public Set<CompeticionEntity> getCompeticiones() {
        return competiciones;
    }

    public void setCompeticiones(Set<CompeticionEntity> competiciones) {
        this.competiciones = competiciones;
    }
}
