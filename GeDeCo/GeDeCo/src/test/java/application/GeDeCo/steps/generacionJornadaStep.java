package application.GeDeCo.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import application.GeDeCo.adapters.dtos.JornadaDTO;
import application.GeDeCo.adapters.entitys.CompeticionEntity;
import application.GeDeCo.adapters.entitys.EquipoEntity;
import application.GeDeCo.adapters.jpas.CompeticionJPARepository;
import application.GeDeCo.adapters.jpas.EquipoJPARepository;
import application.GeDeCo.applications.services.CompeticionService;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.transaction.Transactional;

@Transactional
public class generacionJornadaStep {

    private ResponseEntity<?> response;

    @Autowired
    private CompeticionJPARepository competicionJPARepository;

    @Autowired
    private EquipoJPARepository equipoJPARepository;

    @Autowired
    private CompeticionService competicionService;

    // Limpieza de la base de datos antes de cada escenario
    @Before
    public void setUp() {

    }

    // Metodos auxiliares //
    private void verificar_equipos_no_convocados(int numEquiposInscritos) {

        JornadaDTO jornadaDTO = (JornadaDTO) response.getBody();

        if (jornadaDTO == null) {
            throw new AssertionError("No se ha llegado a dar de alta la jornada");
        }

        // Obtengo la lista de equipos
        List<?> equiposNoConvocados = jornadaDTO.getEquiposNoConvocados();

        assertEquals(numEquiposInscritos, equiposNoConvocados.size(),
                "Algo falló con los cruces el numero de equipos no convocados no coinciden");

        // Imprime la lista de equipos no convocados en la consola
        System.out.println("Lista de equipos no convocados:");
        equiposNoConvocados.forEach(System.out::println);
    }
    // -------------- //

    @Given("existe una Competicion con ID {long}, nombre {string}, deporte {string}, fecha inicio {localDate}, fecha fin {localDate}, pistas {int} y dias reservados {string}")
    public void existe_una_competicion_con_id_nombre_deporte_fecha_inicio_fecha_fin_pistas_y_dias_reservados(
            Long idCompeticion, String nombre, String deporte, LocalDate fechaInicio, LocalDate fechaFin, int numPistas,
            String diasReservados) {

        // Obtenemos la competicion
        CompeticionEntity competicionExistente = competicionJPARepository.findById(idCompeticion).orElse(null);

        // La competicion debe de existir para que el paso se valido
        if (competicionExistente == null) { // Verificar que los atributos coinciden
            throw new AssertionError(
                    "Fallo en @Given: La Competicion con ID " + idCompeticion + " NO está registrada en el sistema.");
        }

        // Verificamos el resto de datos recibidos
        assertEquals(nombre, competicionExistente.getNombre(), "El nombre de la competición no coincide");
        assertEquals(deporte, competicionExistente.getDeporte(), "El deporte de la competición no coincide");
        assertEquals(fechaInicio, competicionExistente.getFechaInicio(),
                "La fecha de inicio de la competición no coincide");
        assertEquals(fechaFin, competicionExistente.getFechaFin(), "La fecha de fin de la competición no coincide");
        assertEquals(numPistas, competicionExistente.getNumPistas(),
                "El número de pistas de la competición no coincide");
        assertEquals(diasReservados, competicionExistente.getDiasReservados(),
                "Los días reservados de la competición no coinciden");
    }

    @Given("la competicion con ID {long} tiene {int} equipos inscritos")
    public void la_competicion_con_id_tiene_equipos_inscritos(Long idCompeticion, int numEquiposInscritos) {

        CompeticionEntity competicionExistente = competicionJPARepository.findById(idCompeticion).orElse(null);

        if (competicionExistente == null) {
            throw new AssertionError("Fallo en @Given: La Competicion con ID " + idCompeticion
                    + " NO existe para verificar los equipos.");
        }

        // Verificar el número de equipos inscritos
        List<EquipoEntity> equiposInscritos = equipoJPARepository.findEquiposByCompeticiones_Id(idCompeticion);

        // El test falla si el conteo no es el esperado
        assertEquals(numEquiposInscritos, equiposInscritos.size(),
                "ERROR: Se esperaban " + numEquiposInscritos + " equipos, pero se encontraron "
                        + equiposInscritos.size());
    }

    @When("intento generar la primera jornada para la competicion con ID {long}")
    public void intento_generar_la_primera_jornada_para_dicha_competicion(Long idCompeticion) {
        this.response = competicionService.generarPrimeraJornada(idCompeticion);
    }

    @Then("debo de obtener una lista de partidosEnJuego")
    public void debo_de_obtener_una_lista_de_partidos_en_juego() {

        JornadaDTO jornadaDTO = (JornadaDTO) response.getBody();

        if (jornadaDTO == null) {
            throw new AssertionError("No hay jornada generada");
        }

        List<?> partidosEnJuego = jornadaDTO.getPartidosEnJuego();

        assertTrue(!partidosEnJuego.isEmpty(), "La lista de partidos en juego no debe estar vacia");

        // Imprime la lista de partidos en juego en la consola
        System.out.println("Lista de partidos en juego:");
        partidosEnJuego.forEach(System.out::println);
    }

    @Then("debo de obtener una lista de partidosEnJuego vacia")
    public void debo_de_obtener_una_lista_de_partidosEnJuego_vacia() {

        JornadaDTO jornadaDTO = (JornadaDTO) response.getBody();

        if (jornadaDTO == null) {
            throw new AssertionError("No hay jornada generada");
        }

        List<?> partidosEnJuego = jornadaDTO.getPartidosEnJuego();

        assertTrue(partidosEnJuego.isEmpty(),
                "No hay suficientes equipos para la jornada, por ello la lista está vacia");

        // Imprime la lista de partidos en juego en la consola
        System.out.println("Lista de partidos en juego:");
        partidosEnJuego.forEach(System.out::println);
    }

    @Then("debo de obtener una respuesta de error con el mensaje {string}")
    public void debo_de_obtener_una_respuesta_de_error_con_el_mensaje(String mensajeEsperado) {

        String mensajeRecibido = (String) response.getBody();

        if (mensajeRecibido == null) {
            throw new AssertionError("Se esperaba un mensaje de error.");
        }

        assertEquals(mensajeEsperado, mensajeRecibido,
                "El mensaje de error no coincide con el esperado.");

        System.out.println(mensajeRecibido);
    }

    @And("y la lista de equiposNoConvocados debe de estar vacia")
    public void y_la_lista_de_equipos_no_convocados_debe_de_estar_vacia() {

        verificar_equipos_no_convocados(0);
    }

    @And("y la lista de equiposNoConvocados debe de tener {int} equipo")
    public void y_la_lista_de_equipos_no_convocados_debe_de_tener_1_equipo(int numEquiposInscritos) {

        verificar_equipos_no_convocados(numEquiposInscritos);
    }

    @And("el codigo de estado de la respuesta debe de ser {int}")
    public void el_codigo_de_estado_de_la_respuesta_debe_de_ser(int codigoEstado) {
        assertEquals(HttpStatus.valueOf(codigoEstado), response.getStatusCode());
    }
}
