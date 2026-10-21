package mx.edu.utez.proyecto1D.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mx.edu.utez.proyecto1D.model.EstadoPartida;

@Getter @AllArgsConstructor
public class TiroResponseDTO {
    private Long id;
    private Long partidaId;
    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;
    private EstadoPartida estadoPartida;
    private Double saldoJugador;
    private String mensaje;
}
