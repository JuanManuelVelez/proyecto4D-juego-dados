package mx.edu.utez.proyecto1D.service;

import mx.edu.utez.proyecto1D.dto.IniciarPartidaRequestDTO;
import mx.edu.utez.proyecto1D.dto.PartidaResponseDTO;
import mx.edu.utez.proyecto1D.dto.TiroResponseDTO;
import mx.edu.utez.proyecto1D.exception.RecursoNoEncontradoException;
import mx.edu.utez.proyecto1D.exception.ReglaNegocioException;
import mx.edu.utez.proyecto1D.model.EstadoPartida;
import mx.edu.utez.proyecto1D.model.Jugador;
import mx.edu.utez.proyecto1D.model.Partida;
import mx.edu.utez.proyecto1D.model.Tiro;
import mx.edu.utez.proyecto1D.repository.JugadorRepository;
import mx.edu.utez.proyecto1D.repository.PartidaRepository;
import mx.edu.utez.proyecto1D.repository.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final TiroRepository tiroRepository;
    private final JugadorService jugadorService;
    private final Random random = new Random();

    public PartidaService(PartidaRepository partidaRepository,
                          JugadorRepository jugadorRepository,
                          TiroRepository tiroRepository,
                          JugadorService jugadorService) {
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
        this.tiroRepository = tiroRepository;
        this.jugadorService = jugadorService;
    }

    @Transactional
    public PartidaResponseDTO iniciarPartida(IniciarPartidaRequestDTO dto) {
        Jugador jugador = jugadorService.buscar(dto.getJugadorId());

        if (!jugador.isActivo()) {
            throw new ReglaNegocioException("El jugador no está activo");
        }
        if (jugador.getSaldo() < dto.getApuesta()) {
            throw new ReglaNegocioException("Saldo insuficiente para realizar la apuesta");
        }

        jugador.setSaldo(jugador.getSaldo() - dto.getApuesta());
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        partida.setJugador(jugador);
        partida.setApuesta(dto.getApuesta());

        return toDTO(partidaRepository.save(partida));
    }

    @Transactional
    public TiroResponseDTO realizarTiro(Long partidaId) {
        Partida partida = buscar(partidaId);

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new ReglaNegocioException("La partida no está en juego (estado: " + partida.getEstado() + ")");
        }

        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        boolean gana = suma == 7 || suma == 11;
        boolean pierde = suma == 2 || suma == 3 || suma == 12;
        String mensaje;

        Jugador jugador = partida.getJugador();

        if (gana) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            jugador.setSaldo(jugador.getSaldo() + partida.getApuesta() * 2);
            jugadorRepository.save(jugador);
            partidaRepository.save(partida);
            mensaje = "¡Ganaste! Se te abonó el doble de tu apuesta";
        } else if (pierde) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
            mensaje = "Perdiste la partida";
        } else {
            mensaje = "La partida continúa, vuelve a tirar";
        }

        // El tiro se guarda como último paso
        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(gana);
        tiro = tiroRepository.save(tiro);

        return new TiroResponseDTO(tiro.getId(), partida.getId(), dado1, dado2, suma,
                gana, partida.getEstado(), jugador.getSaldo(), mensaje);
    }

    @Transactional
    public PartidaResponseDTO finalizarPartidaManual(Long partidaId) {
        Partida partida = buscar(partidaId);

        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new ReglaNegocioException("La partida ya está finalizada");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return toDTO(partidaRepository.save(partida));
    }

    public List<PartidaResponseDTO> obtenerHistorialPartida(Long jugadorId) {
        jugadorService.buscar(jugadorId); // valida que el jugador exista
        return partidaRepository.findByJugadorIdOrderByFechaDesc(jugadorId)
                .stream().map(this::toDTO).toList();
    }

    private Partida buscar(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La partida con id " + id + " no existe"));
    }

    private PartidaResponseDTO toDTO(Partida p) {
        return new PartidaResponseDTO(p.getId(), p.getFecha(), p.getEstado(),
                p.getJugador().getId(), p.getApuesta());
    }
}
