package application.GeDeCo.adapters.dtos;

import application.GeDeCo.adapters.entitys.EquipoEntity;

/* Clase DTO (Data Transfer Object) para representar un equipo */
public class EquipoDTO {

    private Long id;
    private String nombre;
    private Long competicionID; // ID de la competición asociada

    public EquipoDTO() {
    }

    public EquipoDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public EquipoDTO(Long id, String nombre, Long competicionID) {
        this.id = id;
        this.nombre = nombre;
        this.competicionID = competicionID;
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

    public Long getCompeticionID() {
        return competicionID;
    }

    public void setCompeticionID(Long competicionID) {
        this.competicionID = competicionID;
    }

    public static EquipoDTO toEntityaDTO(EquipoEntity equipoEntity) {
        return new EquipoDTO(
                equipoEntity.getId(),
                equipoEntity.getNombre());
    }
}
