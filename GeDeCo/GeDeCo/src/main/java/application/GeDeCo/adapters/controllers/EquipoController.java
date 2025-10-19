package application.GeDeCo.adapters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.GeDeCo.adapters.dtos.EquipoDTO;
import application.GeDeCo.applications.services.EquipoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    // ---- GET ---- //

    /* Endpoint para listar todos los equipos */
    @GetMapping("listarTodos")
    public ResponseEntity<?> listarTodosLosEquipos() {
        return equipoService.listarTodosLosEquipos();
    }

    /* Endpoint para consultar equipos por competición */
    @GetMapping("consultarPorCompeticion/{idCompeticion}")
    public ResponseEntity<?> consultarEquiposPorCompeticion(@PathVariable Long idCompeticion) {
        return equipoService.consultarEquiposPorCompeticion(idCompeticion);
    }

    // ---- POST ---- //

    /* Endpoint para registrar un nuevo equipo en una competicion */
    @PostMapping("registrar")
    public ResponseEntity<?> registrarEquipoEnCompeticion(@RequestBody EquipoDTO equipoDTO) {

        return equipoService.registrarEquipoEnCompeticion(equipoDTO);
    }
}
