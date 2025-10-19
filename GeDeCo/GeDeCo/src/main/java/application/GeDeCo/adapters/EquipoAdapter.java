package application.GeDeCo.adapters;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import application.GeDeCo.adapters.dtos.EquipoDTO;
import application.GeDeCo.adapters.entitys.EquipoEntity;
import application.GeDeCo.adapters.jpas.CompeticionJPARepository;
import application.GeDeCo.adapters.jpas.EquipoJPARepository;
import application.GeDeCo.applications.ports.EquipoRepository;
import jakarta.transaction.Transactional;

@Component
public class EquipoAdapter implements EquipoRepository {

    @Autowired
    private CompeticionJPARepository competicionJPARepository;

    @Autowired
    private EquipoJPARepository equipoJPARepository;

    @Override
    public ResponseEntity<?> listarTodosLosEquipos() {
        return ResponseEntity
                .ok(equipoJPARepository.findAll().stream().map(EquipoDTO::toEntityaDTO).toList());
    }

    @Override
    @Transactional
    public ResponseEntity<?> registrarEquipoEnCompeticion(EquipoDTO equipoDTO) {

        // 1. Verificar si la competición existe con el competicionID y mirando en
        // Competiciones
        Long competicionID = equipoDTO.getCompeticionID();

        if (competicionJPARepository.findById(competicionID).isEmpty()) {
            return ResponseEntity.badRequest().body("Competición " + competicionID + " no encontrada");
        }

        // 2. La competicion existe por lo que buscaremos o crearemos el equipo en
        // cuestion

        Optional<EquipoEntity> equipoABuscar = equipoJPARepository.findByNombre(equipoDTO.getNombre());

        if (equipoABuscar.isPresent()) { // El equipo ya existe

            EquipoEntity equipoExistente = equipoABuscar.get();

            // 3. Verificar si el equipo ya está registrado en la competición
            if (equipoExistente.getCompeticiones().stream()
                    .anyMatch(c -> c.getId().equals(competicionID))) {

                return new ResponseEntity<>("El equipo " + equipoExistente.getNombre()
                        + " ya está registrado en la competición " + competicionID + " - "
                        + competicionJPARepository.findById(competicionID).get().getNombre() + ".",
                        HttpStatus.CONFLICT);
            } else {

                // 4. Si no está registrado, añadir la competición al equipo existente
                equipoExistente.getCompeticiones().add(competicionJPARepository.findById(competicionID).get());

                equipoJPARepository.save(equipoExistente); // Guardar los cambios en la base de datos para hacer
                                                           // persistente la relación

                return new ResponseEntity<>("Equipo " + equipoExistente.getNombre()
                        + " añadido a la competición " + competicionID + " - "
                        + competicionJPARepository.findById(competicionID).get().getNombre() + " correctamente",
                        HttpStatus.CREATED);
            }
        } else { // El equipo no existe

            // 5. Si el equipo no existe, crear uno nuevo y asignarle la competición
            EquipoEntity nuevoEquipo = new EquipoEntity();

            nuevoEquipo.setId(equipoDTO.getId());
            nuevoEquipo.setNombre(equipoDTO.getNombre());
            nuevoEquipo.getCompeticiones().add(competicionJPARepository.findById(competicionID).get());

            equipoJPARepository.save(nuevoEquipo);

            return new ResponseEntity<>("Equipo " + nuevoEquipo.getNombre()
                    + " creado y añadido a la competición " + competicionID + " - "
                    + competicionJPARepository.findById(competicionID).get().getNombre() + " correctamente",
                    HttpStatus.CREATED);
        }

    }

    @Override
    public ResponseEntity<?> consultarEquiposPorCompeticion(Long idCompeticion) {

        if (competicionJPARepository.findById(idCompeticion).isEmpty()) {
            return new ResponseEntity<>("La competición " + idCompeticion + " no existe", HttpStatus.NOT_FOUND);
        }

        if (equipoJPARepository.findEquiposByCompeticiones_Id(idCompeticion).isEmpty()) {
            return new ResponseEntity<>("No hay equipos registrados en la competición " + idCompeticion + " - "
                    + competicionJPARepository.findById(idCompeticion).get().getNombre(), HttpStatus.NOT_FOUND);
        }

        List<EquipoEntity> equipos = equipoJPARepository.findEquiposByCompeticiones_Id(idCompeticion);
        return ResponseEntity.ok(equipos);
    }
}
