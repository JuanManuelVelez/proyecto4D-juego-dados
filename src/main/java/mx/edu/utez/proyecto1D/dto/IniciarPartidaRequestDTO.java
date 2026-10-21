package mx.edu.utez.proyecto1D.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class IniciarPartidaRequestDTO {
    @NotNull(message = "El id del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @Positive(message = "La apuesta debe ser mayor a 0")
    private Double apuesta;
}
