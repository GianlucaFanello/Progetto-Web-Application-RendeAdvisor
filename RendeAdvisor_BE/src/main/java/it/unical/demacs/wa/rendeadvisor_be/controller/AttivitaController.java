package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.IAttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.AttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/attivita")
@CrossOrigin(origins = "http://localhost:4200")
public class AttivitaController {

    private IAttivitaDAO attivitaDAO;

    public AttivitaController() {
        try {
            this.attivitaDAO = new AttivitaDAO(DBManager.getInstance().getConnection());
        } catch (SQLException e) {
            System.err.println("Errore critico nel Controller: Impossibile connettersi al DB");
            e.printStackTrace();
        }
    }
    // Restituisce tutte le attività
    @GetMapping
    public ResponseEntity<List<AttivitaDTO>> getTutte() {
        try {
            return ResponseEntity.ok(attivitaDAO.findAll());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Restituisce solo le attività di tipo Ristorante
    @GetMapping("/ristoranti")
    public ResponseEntity<List<AttivitaDTO>> getSoloRistoranti() {
        try {
            return ResponseEntity.ok(attivitaDAO.findByTipo("Ristorante"));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Restituisce solo le attività di tipo Hotel
    @GetMapping("/hotel")
    public ResponseEntity<List<AttivitaDTO>> getSoloHotel() {
        try {
            return ResponseEntity.ok(attivitaDAO.findByTipo("Hotel"));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Restituisce i dettagli di un'attività dato il nomeLocale
    @GetMapping("/dettaglio/{nomeLocale}")
    public ResponseEntity<AttivitaDTO> getDettaglio(@PathVariable String nomeLocale) {
        try {
            AttivitaDTO attivita = attivitaDAO.findByPrimaryKey(nomeLocale);
            if (attivita != null) {
                return ResponseEntity.ok(attivita);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crea una nuova attività nel sistema
    @PostMapping("/salva")
    public ResponseEntity<String> salva(@RequestBody AttivitaDTO attivita) {
        try {
            boolean ok = attivitaDAO.insertAttivita(attivita);
            return ok ? ResponseEntity.ok("OK") : ResponseEntity.badRequest().body("Errore");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }
}