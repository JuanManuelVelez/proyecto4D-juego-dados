package mx.edu.utez.proyecto1D.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class JugadorResponseDTO {
    private Long id;
    private String nombre;
    private Double saldo;
    private boolean activo;
}
