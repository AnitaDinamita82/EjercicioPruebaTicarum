package application.GeDeCo.applications.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import application.GeDeCo.adapters.entitys.CompeticionEntity;
import application.GeDeCo.applications.ports.CompeticionRepository;

@Service
public class CompeticionService {

    @Autowired
    private CompeticionRepository competicionRepository;

    /* Método para listar todas las competiciones */
    public ResponseEntity<?> listarTodasLasCompeticiones() {
        return competicionRepository.listarTodasLasCompeticiones();
    }

    /* Método para registrar una nueva competicion */
    public ResponseEntity<?> registrarCompeticion(CompeticionEntity competicionEntity) {
        return competicionRepository.registrarCompeticion(competicionEntity);
    }

    /* Método para generar la primera jornada de una competicion */
    public ResponseEntity<?> generarPrimeraJornada(Long competicionID) {
        return competicionRepository.generarPrimeraJornada(competicionID);
    }

    /* Método para consultar los partidos de la primera jornada */
    public ResponseEntity<?> consultarPartidosPrimeraJornada(Long competicionID) {
        return competicionRepository.consultarPartidosPrimeraJornada(competicionID);
    }

}
