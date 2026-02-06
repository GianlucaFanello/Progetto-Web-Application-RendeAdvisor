
package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.AttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.ApiResponse;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RispostaDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.AttivitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/attivita")
@CrossOrigin(origins = "http://localhost:4200")
public class AttivitaController {

    private final AttivitaService attivitaService;


    @Autowired
    public AttivitaController(AttivitaService attivitaService) {
        this.attivitaService = attivitaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AttivitaDTO>>> getTutte() {
        try {
            List<AttivitaDTO> lista = attivitaService.getTutte();
            // Passa true, un messaggio e la lista
            return ResponseEntity.ok(new ApiResponse<>(true, "Lista attività recuperata", lista));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Errore database: " + e.getMessage()));
        }
    }

    @GetMapping("/vicini")
    public ResponseEntity<ApiResponse<List<AttivitaDTO>>> getVicini() {
        try {
            List<AttivitaDTO> lista = attivitaService.getVicini();
            return ResponseEntity.ok(new ApiResponse<>(true, "Locali vicini recuperati", lista));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Errore nel recupero dei locali vicini"));
        }
    }

    @GetMapping("/ristoranti")
    public ResponseEntity<ApiResponse<List<AttivitaDTO>>> getRistoranti() {
        try {
            List<AttivitaDTO> lista = attivitaService.getRistoranti();
            return ResponseEntity.ok(new ApiResponse<>(true, "Ristoranti recuperati", lista));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Errore nel recupero ristoranti"));
        }
    }

    @GetMapping("/hotel")
    public ResponseEntity<ApiResponse<List<AttivitaDTO>>> getHotel() {
        try {
            List<AttivitaDTO> lista = attivitaService.getHotel();

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Lista inviata", lista)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }

    @GetMapping("/dettaglio/{nomeLocale}")
    public ResponseEntity<ApiResponse<AttivitaDTO>> getDettaglio(@PathVariable String nomeLocale) {
        try {
            AttivitaDTO attivita = attivitaService.getDettaglio(nomeLocale);

            if (attivita == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Attività non trovata", null));
            }

            return ResponseEntity.ok(new ApiResponse<>(true, "Attività trovata", attivita));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }


    @PostMapping("/salva")
    public ResponseEntity<ApiResponse<Void>> salva(@RequestParam String nomeLocale,
                                                   @RequestParam String proprietario,
                                                   @RequestParam String telefono,
                                                   @RequestParam String email,
                                                   @RequestParam String descrizione,
                                                   @RequestParam String indirizzo,
                                                   @RequestParam String tipo,
                                                   @RequestParam(required = false) MultipartFile immagine) throws IOException {
        try {
            AttivitaDTO attivita = new AttivitaDTO();
            attivita.setNomeLocale(nomeLocale.trim());
            attivita.setProprietario(proprietario.trim());
            attivita.setTelefono(telefono.trim());
            attivita.setEmail(email.trim());
            attivita.setDescrizione(descrizione.trim());
            attivita.setIndirizzo(indirizzo.trim());
            attivita.setTipo(tipo);

            if (immagine != null && !immagine.isEmpty()) {
                attivita.setImmagine(immagine.getBytes());
            }

            boolean ok = attivitaService.salvaAttivita(attivita);

            if (ok) {
                return ResponseEntity.ok(
                        new ApiResponse<>(true, "Attività salvata con successo", null)
                );
            }

            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Errore nel salvataggio", null)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse<>(false, "Errore interno al server", null)
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<AttivitaDTO>>> search(@RequestParam String query) {

        try {
            List<AttivitaDTO> risultati = attivitaService.search(query.trim().toLowerCase());

            return ResponseEntity.ok(new ApiResponse<>(true, "Risultati ricerca", risultati)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore", null));
        }
    }

    @GetMapping("/by-nome")
    public ResponseEntity<ApiResponse<AttivitaDTO>> getByNome(@RequestParam String nome) throws SQLException {
        try {
            AttivitaDTO attivita = attivitaService.findByNome(nome);
            if(attivita != null){
                return ResponseEntity.ok(
                        new ApiResponse(true, "Locale Trovato", attivita) );
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Locale non trovato", null));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore", null));
        }

    }

    @GetMapping("/by-proprietario")
    public ResponseEntity<ApiResponse<List<AttivitaDTO>>> getByProprietario(@RequestParam String username) throws SQLException {
        try {
            List<AttivitaDTO> attivita = attivitaService.listaAttivitaByProprietario(username);

            return ResponseEntity.ok(new ApiResponse<>(true, "Risultati ricerca", attivita)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore", null));
        }
    }

    @PostMapping("modifica/salva")
    public ResponseEntity<ApiResponse<String>> modificaProfilo(@RequestParam String nomeLocale,
                                                               @RequestParam String email,
                                                               @RequestParam String telefono,
                                                               @RequestParam String indirizzo,
                                                               @RequestParam String descrizione,
                                                               @RequestParam(required = false) MultipartFile immagine,
                                                               @RequestParam String eliminata,
                                                               @RequestParam String vecchioNomeLocale) throws IOException {
        try {
            AttivitaDTO attivita= new AttivitaDTO();
            attivita.setNomeLocale(nomeLocale.trim());
            attivita.setEmail(email.trim());
            attivita.setTelefono(telefono.trim());
            attivita.setIndirizzo(indirizzo.trim());
            attivita.setDescrizione(descrizione.trim());

            if(immagine != null && !immagine.isEmpty()) {
                attivita.setImmagine(immagine.getBytes());
            }

            boolean elim = Objects.equals(eliminata, "true");
            AttivitaDTO aggiornato = attivitaService.modificaProfilo(attivita, vecchioNomeLocale.trim(), elim);

            if(aggiornato != null){
                return ResponseEntity.ok(new ApiResponse<>(true, "Profilo aggiornato", aggiornato.getNomeLocale()));
            }
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "Profilo non modificato", null));


        } catch (IOException | SQLException e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Errore durante la modifica", null));
        }
    }
}
