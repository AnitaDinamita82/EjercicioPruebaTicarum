package application.GeDeCo.applications.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import application.GeDeCo.adapters.dtos.EquipoDTO;

import application.GeDeCo.applications.ports.EquipoRepository;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    /* Método para registrar un nuevo equipo */
    public ResponseEntity<?> registrarEquipoEnCompeticion(EquipoDTO equipoDTO) {
        return equipoRepository.registrarEquipoEnCompeticion(equipoDTO);
    }

    public ResponseEntity<?> listarTodosLosEquipos() {
        return equipoRepository.listarTodosLosEquipos();
    }

    public ResponseEntity<?> consultarEquiposPorCompeticion(Long idCompeticion) {
        return equipoRepository.consultarEquiposPorCompeticion(idCompeticion);
    }
}
