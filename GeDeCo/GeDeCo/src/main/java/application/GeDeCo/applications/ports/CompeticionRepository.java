package application.GeDeCo.applications.ports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import application.GeDeCo.adapters.entitys.CompeticionEntity;

@Repository
public interface CompeticionRepository {

    ResponseEntity<?> listarTodasLasCompeticiones();

    ResponseEntity<?> registrarCompeticion(CompeticionEntity competicionEntity);

    ResponseEntity<?> generarPrimeraJornada(Long competicionID);

    ResponseEntity<?> consultarPartidosPrimeraJornada(Long competicionID);
}
