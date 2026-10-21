# proyecto1D – Juego de dados

Requisitos: Java 17, Maven, MySQL (la base `casino_db` se crea sola).
Ajusta usuario/contraseña en `src/main/resources/application.properties`.

## Endpoints
| Método | Ruta | Descripción |
|---|---|---|
| POST | /api/jugadores | Crear jugador `{"nombre":"Ana","saldo":100}` |
| POST | /api/jugadores/{id}/recargar | Recargar saldo `{"monto":50}` |
| GET  | /api/jugadores/{id}/partidas | Historial de partidas |
| POST | /api/partidas/iniciar | `{"jugadorId":1,"apuesta":10}` |
| POST | /api/partidas/{id}/tiro | Realizar tiro |
| PUT  | /api/partidas/{id}/finalizar | Finalizar partida manualmente |
