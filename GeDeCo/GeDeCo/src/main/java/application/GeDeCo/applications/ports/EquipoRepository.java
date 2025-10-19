package application.GeDeCo.applications.ports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import application.GeDeCo.adapters.dtos.EquipoDTO;

@Repository
public interface EquipoRepository {

    ResponseEntity<?> registrarEquipoEnCompeticion(EquipoDTO equipoDTO);

    ResponseEntity<?> listarTodosLosEquipos();

    ResponseEntity<?> consultarEquiposPorCompeticion(Long idCompeticion);

}
