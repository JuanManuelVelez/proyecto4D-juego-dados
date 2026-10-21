package mx.edu.utez.proyecto1D.service;

import mx.edu.utez.proyecto1D.dto.JugadorRequestDTO;
import mx.edu.utez.proyecto1D.dto.JugadorResponseDTO;
import mx.edu.utez.proyecto1D.exception.RecursoNoEncontradoException;
import mx.edu.utez.proyecto1D.model.Jugador;
import mx.edu.utez.proyecto1D.repository.JugadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional
    public JugadorResponseDTO crear(JugadorRequestDTO dto) {
        Jugador j = new Jugador();
        j.setNombre(dto.getNombre());
        j.setSaldo(dto.getSaldo());
        j.setActivo(true);
        return toDTO(jugadorRepository.save(j));
    }

    // recargarSaldo
    @Transactional
    public JugadorResponseDTO recargarSaldo(Long jugadorId, Double monto) {
        Jugador j = buscar(jugadorId);
        j.setSaldo(j.getSaldo() + monto);
        return toDTO(jugadorRepository.save(j));
    }

    public Jugador buscar(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El jugador con id " + id + " no existe"));
    }

    private JugadorResponseDTO toDTO(Jugador j) {
        return new JugadorResponseDTO(j.getId(), j.getNombre(), j.getSaldo(), j.isActivo());
    }
}
