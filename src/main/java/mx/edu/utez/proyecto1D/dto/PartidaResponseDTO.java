package mx.edu.utez.proyecto1D.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mx.edu.utez.proyecto1D.model.EstadoPartida;

import java.time.LocalDateTime;

@Getter @AllArgsConstructor
public class PartidaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private EstadoPartida estado;
    private Long jugadorId;
    private Double apuesta;
}
