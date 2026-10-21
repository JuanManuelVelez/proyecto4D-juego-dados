package mx.edu.utez.proyecto1D.controller;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1D.dto.IniciarPartidaRequestDTO;
import mx.edu.utez.proyecto1D.dto.PartidaResponseDTO;
import mx.edu.utez.proyecto1D.dto.TiroResponseDTO;
import mx.edu.utez.proyecto1D.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<PartidaResponseDTO> iniciarPartida(@RequestBody @Valid IniciarPartidaRequestDTO payload) {
        return ResponseEntity.status(201).body(partidaService.iniciarPartida(payload));
    }

    @PostMapping("/{id}/tiro")
    public ResponseEntity<TiroResponseDTO> realizarTiro(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.realizarTiro(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<PartidaResponseDTO> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.finalizarPartidaManual(id));
    }
}
