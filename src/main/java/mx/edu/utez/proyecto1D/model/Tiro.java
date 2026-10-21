package mx.edu.utez.proyecto1D.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tiro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "partida_id")
    private Partida partida;

    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;
}
