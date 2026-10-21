package mx.edu.utez.proyecto1D.repository;

import mx.edu.utez.proyecto1D.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByJugadorIdOrderByFechaDesc(Long jugadorId);
}
