package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RispostaDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RispostaDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.RispostaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/risposta")
@CrossOrigin(origins = "http://localhost:4200")
public class RispostaController {

    private RispostaService rispostaService;

    public RispostaController() {
        try {
            this.rispostaService = new RispostaService(new RispostaDAO(DBManager.getInstance().getConnection()));
        } catch (SQLException e) {
            throw new RuntimeException("?", e);
        }
    }

    // Aggiungi una nuova risposta
    @PostMapping("/salva")
    public ResponseEntity<String> salva(@RequestBody RispostaDTO risposta) {
        try {
            rispostaService.salvaRisposta(risposta);
            return ResponseEntity.ok("ok");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore inserimento risposta");
        }
    }

    // Risposte di una recensione
    @GetMapping("/risposte/{idRecensione}")
    public ResponseEntity<List<RispostaDTO>> getByRecensione(
            @PathVariable int idRecensione) {
        try {
            return ResponseEntity.ok(
                    rispostaService.getRisposteByRecensione(idRecensione)
            );
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
