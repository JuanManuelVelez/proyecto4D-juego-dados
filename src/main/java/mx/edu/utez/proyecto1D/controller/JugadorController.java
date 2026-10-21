package mx.edu.utez.proyecto1D.controller;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1D.dto.*;
import mx.edu.utez.proyecto1D.service.JugadorService;
import mx.edu.utez.proyecto1D.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;
    private final PartidaService partidaService;

    public JugadorController(JugadorService jugadorService, PartidaService partidaService) {
        this.jugadorService = jugadorService;
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<JugadorResponseDTO> crear(@RequestBody @Valid JugadorRequestDTO payload) {
        return ResponseEntity.status(201).body(jugadorService.crear(payload));
    }

    @PostMapping("/{id}/recargar")
    public ResponseEntity<JugadorResponseDTO> recargarSaldo(@PathVariable Long id,
                                                            @RequestBody @Valid RecargaRequestDTO payload) {
        return ResponseEntity.ok(jugadorService.recargarSaldo(id, payload.getMonto()));
    }

    @GetMapping("/{id}/partidas")
    public ResponseEntity<List<PartidaResponseDTO>> historial(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.obtenerHistorialPartida(id));
    }
}
