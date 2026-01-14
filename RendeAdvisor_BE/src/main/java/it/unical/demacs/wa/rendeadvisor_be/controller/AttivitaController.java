
package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.AttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.AttivitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<AttivitaDTO>> getTutte() {
        try {
            return ResponseEntity.ok(attivitaService.getTutte());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ristoranti")
    public ResponseEntity<List<AttivitaDTO>> getRistoranti() {
        try {
            return ResponseEntity.ok(attivitaService.getRistoranti());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/hotel")
    public ResponseEntity<List<AttivitaDTO>> getHotel() {
        try {
            return ResponseEntity.ok(attivitaService.getHotel());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/dettaglio/{nomeLocale}")
    public ResponseEntity<AttivitaDTO> getDettaglio(@PathVariable String nomeLocale) {
        try {
            AttivitaDTO attivita = attivitaService.getDettaglio(nomeLocale);
            return attivita != null
                    ? ResponseEntity.ok(attivita)
                    : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/salva")
    public ResponseEntity<String> salva(@RequestBody AttivitaDTO attivita) {
        try {
            boolean ok = attivitaService.salvaAttivita(attivita);
            return ok
                    ? ResponseEntity.ok("OK")
                    : ResponseEntity.badRequest().body("Errore");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }
}
