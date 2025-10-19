package application.GeDeCo.adapters.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.GeDeCo.adapters.dtos.CompeticionDTO;
import application.GeDeCo.adapters.entitys.CompeticionEntity;
import application.GeDeCo.applications.services.CompeticionService;

@RestController
@RequestMapping("/competiciones")
public class CompeticionController {

    @Autowired
    private CompeticionService competicionService;

    /* Endpoint para obtener todas las competiciones */
    @GetMapping("/listarTodas")
    public ResponseEntity<?> listarTodasLasCompeticiones() {
        return competicionService.listarTodasLasCompeticiones();
    }

    /* Endpoint para consultar los partidos de la primera jornada */
    @GetMapping("/jornada/{competicionID}")
    public ResponseEntity<?> consultarPartidosPrimeraJornada(@PathVariable Long competicionID) {
        return competicionService.consultarPartidosPrimeraJornada(competicionID);
    }

    /* Endpoint para registrar una nueva competicion */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCompeticion(@RequestBody CompeticionDTO competicionDTO) {

        CompeticionEntity competicionEntity = competicionDTO.toDTOaEntity();
        return competicionService.registrarCompeticion(competicionEntity);
    }

    /* Endpoint para generar la primera jornada de una competicion */
    @PostMapping("/jornada/{competicionID}")
    public ResponseEntity<?> generarPrimeraJornada(@PathVariable Long competicionID) {
        return competicionService.generarPrimeraJornada(competicionID);
    }

}