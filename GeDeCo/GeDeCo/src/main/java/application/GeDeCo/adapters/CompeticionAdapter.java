package application.GeDeCo.adapters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import application.GeDeCo.adapters.dtos.JornadaDTO;
import application.GeDeCo.adapters.dtos.PartidoDTO;
import application.GeDeCo.adapters.entitys.CompeticionEntity;
import application.GeDeCo.adapters.entitys.EquipoEntity;
import application.GeDeCo.adapters.entitys.PartidoEntity;
import application.GeDeCo.adapters.jpas.CompeticionJPARepository;
import application.GeDeCo.adapters.jpas.EquipoJPARepository;
import application.GeDeCo.adapters.jpas.PartidoJPARepository;
import application.GeDeCo.applications.ports.CompeticionRepository;
import jakarta.transaction.Transactional;

@Component
public class CompeticionAdapter implements CompeticionRepository {

    @Autowired
    private CompeticionJPARepository competicionJPARepository;

    @Autowired
    private EquipoJPARepository equipoJPARepository;

    @Autowired
    private PartidoJPARepository partidoJPARepository;

    // Implementación de los métodos del repositorio

    @Override
    public ResponseEntity<?> listarTodasLasCompeticiones() {
        return ResponseEntity.ok(competicionJPARepository.findAll());

    }

    @Override
    public ResponseEntity<?> registrarCompeticion(CompeticionEntity competicionEntity) {

        if (competicionJPARepository.findByNombre(competicionEntity.getNombre()).isPresent()) {
            return ResponseEntity.badRequest().body(" La competicion " + competicionEntity.getNombre() + " ya existe.");
        }

        if (competicionJPARepository.findByDeporte(competicionEntity.getDeporte()).isPresent()) {
            return ResponseEntity.badRequest().body("La competición " + competicionEntity.getNombre()
                    + " ya existe para " + competicionEntity.getDeporte() + ".");
        }

        competicionJPARepository.save(competicionEntity); // Guardar la entidad en la base de datos y la hacemos
                                                          // persistente
        return new ResponseEntity<>("Competición " + competicionEntity.getId() + " - " + competicionEntity.getNombre()
                + " registrada con éxito.", HttpStatus.CREATED);
    }

    // Lógica para generar la primera jornada de una competición
    @Override
    @Transactional
    public ResponseEntity<?> generarPrimeraJornada(Long competicionID) {

        // 1. Comprobar si la competición existe
        Optional<CompeticionEntity> competicionActiva = competicionJPARepository.findById(competicionID);

        if (!competicionActiva.isPresent()) {
            return new ResponseEntity<>("La competición " + competicionID + " no existe.", HttpStatus.NOT_FOUND);
        }

        // 2. La competicion existe y tengo que comprobar si hay equipos suficientes
        // para asignarles partidos
        List<EquipoEntity> equiposDeCompeticion = equipoJPARepository.findEquiposByCompeticiones_Id(competicionID);

        if (equiposDeCompeticion.size() < 2) {

            JornadaDTO jornadaDTO = new JornadaDTO(Collections.emptyList(),
                    equiposDeCompeticion.stream().map(EquipoEntity::getNombre).toList());

            return new ResponseEntity<>(jornadaDTO, HttpStatus.OK);
        }

        // 2.1 Hay que limpiar los partidos existentes de la primera jornada (si los
        // hay)
        partidoJPARepository.deleteByCompeticion_Id(competicionID);

        // 3. Si hay equipos suficientes, generamos la primera jornada

        // 3.1 Obtenemos los dias habiles para la competición
        List<LocalDate> diasHabiles = obtenerDiasHabiles(competicionActiva.get());

        if (diasHabiles.isEmpty()) {
            return new ResponseEntity<>("No hay días hábiles disponibles para la competición "
                    + competicionID + " - " + competicionActiva.get().getNombre() + ".", HttpStatus.NOT_FOUND);
        }

        // 3.2 Generar la tupla (Fecha + Pista + Turno) para asignar los partidos en un
        // hueco disponible

        List<HuecosDisponibles> huecosDisponibles = new ArrayList<>();

        for (LocalDate dia : diasHabiles) {
            for (int pista = 1; pista <= competicionActiva.get().getNumPistas(); pista++) {
                huecosDisponibles.add(new HuecosDisponibles(dia, pista, "Mañana"));
                huecosDisponibles.add(new HuecosDisponibles(dia, pista, "Tarde"));
            }
        }

        // 3.3 Emparejamos los equipos y los asignamos a un hueco disponible
        LinkedList<EquipoEntity> equiposAEmparejar = new LinkedList<>(equiposDeCompeticion); // Al principio seran todos
                                                                                             // los equipos de esa
                                                                                             // competicion
        List<PartidoEntity> partidosAsignados = new ArrayList<>();

        // Mientras tengamos equipos por emparejar y huecos disponibles

        Integer index = 0;

        while (equiposAEmparejar.size() >= 2 && index < huecosDisponibles.size()) {

            HuecosDisponibles huecoLibre = huecosDisponibles.get(index);

            // Sacamos los dos primeros equipos de la lista para emparejarlos
            EquipoEntity equipoLocal = equiposAEmparejar.poll(); // Sacamos el primer equipo de la lista
            EquipoEntity equipoVisitante = equiposAEmparejar.poll(); // Sacamos el segundo equipo de la lista

            // Creamos el partido y lo asignamos al hueco disponible
            PartidoEntity partido = new PartidoEntity();

            partido.setCompeticion(competicionActiva.get());
            partido.setEquipoLocal(equipoLocal);
            partido.setEquipoVisitante(equipoVisitante);
            partido.setFechaPartido(huecoLibre.fecha);
            partido.setPistaAsignada(huecoLibre.pista);
            partido.setTurno(huecoLibre.turno);

            partidosAsignados.add(partido);
            index++; // Incrementamos el índice para usar el siguiente hueco en la próxima iteración
        }

        // 4. Guardamos todos los partidos asignados en la base de datos
        partidoJPARepository.saveAll(partidosAsignados);

        // 5. Preparamos la respuesta con los partidos asignados y los equipos no
        // convocados
        List<PartidoDTO> partidosDTO = partidosAsignados.stream()
                .map(PartidoDTO::toEntityaDTO)
                .collect(Collectors.toList());

        List<String> equiposNoConvocados = equiposAEmparejar.stream()
                .map(EquipoEntity::getNombre)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new JornadaDTO(partidosDTO, equiposNoConvocados), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> consultarPartidosPrimeraJornada(Long competicionID) {

        // 1. Comprobar si la competición existe
        Optional<CompeticionEntity> competicionActiva = competicionJPARepository.findById(competicionID);

        if (!competicionActiva.isPresent()) {
            return new ResponseEntity<>("La competición " + competicionID + " no existe.", HttpStatus.NOT_FOUND);
        }

        // 2. La competicion existe, obtenemos los partidos de la primera jornada
        List<PartidoEntity> partidosDeCompeticion = partidoJPARepository.findByCompeticion_Id(competicionID);

        if (partidosDeCompeticion.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No hay partidos asignados para la primera jornada de esta competición " + competicionID
                            + " - " + competicionActiva.get().getNombre() + ".");
        }

        return ResponseEntity.ok(
                partidosDeCompeticion.stream()
                        .map(PartidoDTO::toEntityaDTO)
                        .collect(Collectors.toList()));
    }

    /* METODOS AUXILIARES */

    private List<LocalDate> obtenerDiasHabiles(CompeticionEntity competicionActiva) {

        List<LocalDate> diasHabiles = new ArrayList<>();

        // Obtener los días reservados de la competición
        String listaDiasReservados = competicionActiva.getDiasReservados();

        if (listaDiasReservados == null || listaDiasReservados.isEmpty()) {
            return diasHabiles; // Retorna una lista vacía si no hay días reservados
        }

        // Dividimos la cadena "listaDiasReservados" en un array de días
        String[] arrayDeDias = listaDiasReservados.split(",");
        Set<String> diasValidos = new HashSet<>(); // Conjunto para almacenar días válidos

        for (String dia : arrayDeDias) {
            dia = dia.trim().toUpperCase(); // Eliminar espacios y convertir a mayúsculas
            diasValidos.add(dia);
        }

        // Generar la lista de días validos dentro del rango de fechas de la competición
        LocalDate fechaInicio = competicionActiva.getFechaInicio();
        LocalDate fechaFin = competicionActiva.getFechaFin();

        // Iterar desde la fecha de inicio hasta la fecha de fin añadiendo a dias
        // habiles
        // los dias válidos para ese rango
        while (!fechaInicio.isAfter(fechaFin)) {

            DayOfWeek diaDeLaSemana = fechaInicio.getDayOfWeek();
            String nombreDiaSpain = parseDiaToSpanish(diaDeLaSemana);

            if (diasValidos.contains(nombreDiaSpain)) {
                diasHabiles.add(fechaInicio);
            }
            fechaInicio = fechaInicio.plusDays(1);
        }

        return diasHabiles;
    }

    private String parseDiaToSpanish(DayOfWeek diaDeLaSemana) {
        switch (diaDeLaSemana) {
            case MONDAY:
                return "LUNES";
            case TUESDAY:
                return "MARTES";
            case WEDNESDAY:
                return "MIERCOLES";
            case THURSDAY:
                return "JUEVES";
            case FRIDAY:
                return "VIERNES";
            case SATURDAY:
                return "SABADO";
            case SUNDAY:
                return "DOMINGO";
            default:
                return "";
        }
    }

    // Clase auxiliar para representar un hueco disponible
    private static class HuecosDisponibles {
        private LocalDate fecha;
        private Integer pista;
        private String turno;

        public HuecosDisponibles(LocalDate fecha, Integer pista, String turno) {
            this.fecha = fecha;
            this.pista = pista;
            this.turno = turno;
        }
    }
}
