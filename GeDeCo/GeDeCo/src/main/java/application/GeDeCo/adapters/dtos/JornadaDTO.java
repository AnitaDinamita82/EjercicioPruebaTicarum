package application.GeDeCo.adapters.dtos;

import java.util.List;

public class JornadaDTO {

    private List<PartidoDTO> partidosEnJuego; // Lista de partidos en la jornada
    private List<String> equiposNoConvocados; // Lista de equipos no convocados para la jornada, es decir que no
                                              // pudieron ser asignados a ningun partido

    public JornadaDTO() {
    }

    public JornadaDTO(List<PartidoDTO> partidosEnJuego, List<String> equiposNoConvocados) {
        this.partidosEnJuego = partidosEnJuego;
        this.equiposNoConvocados = equiposNoConvocados;
    }

    public List<PartidoDTO> getPartidosEnJuego() {
        return partidosEnJuego;
    }

    public void setPartidosEnJuego(List<PartidoDTO> partidosEnJuego) {
        this.partidosEnJuego = partidosEnJuego;
    }

    public List<String> getEquiposNoConvocados() {
        return equiposNoConvocados;
    }

    public void setEquiposNoConvocados(List<String> equiposNoConvocados) {
        this.equiposNoConvocados = equiposNoConvocados;
    }

}
