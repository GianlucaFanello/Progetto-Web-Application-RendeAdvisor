
package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.AttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.ApiResponse;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RispostaDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.AttivitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/attivita")
@CrossOrigin(origins = "http://localhost:4200")
public class AttivitaController {

    private AttivitaService attivitaService;

    public AttivitaController() {
        try {
            this.attivitaService = new AttivitaService(
                    new AttivitaDAO(DBManager.getInstance().getConnection())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AttivitaDTO>>> getTutte() {
        try {
            List<AttivitaDTO> lista = attivitaService.getTutte();

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Lista attività inviata", lista)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }


    @GetMapping("/ristoranti")
    public ResponseEntity<ApiResponse<List<AttivitaDTO>>> getRistoranti() {
        try {
            List<AttivitaDTO> lista = attivitaService.getRistoranti();

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Lista ristoranti inviata", lista)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
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
            attivita.setNomeLocale(nomeLocale);
            attivita.setProprietario(proprietario);
            attivita.setTelefono(telefono);
            attivita.setEmail(email);
            attivita.setDescrizione(descrizione);
            attivita.setIndirizzo(indirizzo);
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
            List<AttivitaDTO> risultati = attivitaService.search(query);

            return ResponseEntity.ok(new ApiResponse<>(true, "Risultati ricerca", risultati)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore", null));
        }
    }

    @GetMapping("/by-nome")
    public AttivitaDTO getByNome(@RequestParam String nome) throws SQLException {
        return attivitaService.findByNome(nome);
    }


}
