package it.unical.demacs.wa.rendeadvisor_be.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unical.demacs.wa.rendeadvisor_be.dao.IUtenteDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.UtenteDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;

@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController {
    private IUtenteDAO dao;
    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);

    public UtenteController() {
        try {
            this.dao = new UtenteDAO(DBManager.getInstance().getConnection());
        } catch (SQLException e) {
            logger.error("Errore inizializzazione DAO utenti", e);
            this.dao = null; // lasciamo null e gestiamo nelle chiamate
        }
    }
    // Gestisce la creazione di un nuovo account
    @PostMapping("/registrazione")
    public ResponseEntity<String> registra(@RequestBody UtenteDTO utente) {
        if (dao == null) {
            logger.error("DAO non inizializzato: impossibile registrare utente");
            return ResponseEntity.status(500).body("Server error");
        }
        try {
            return dao.insertUtente(utente) ? ResponseEntity.ok("OK") : ResponseEntity.badRequest().body("Errore");
        } catch (SQLException e) {
            logger.error("Errore durante registrazione utente", e);
            return ResponseEntity.status(500).body("Server error");
        }
    }

    // Gestisce l'accesso degli utenti esistenti
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UtenteDTO credenziali) {
        if (dao == null) {
            logger.error("DAO non inizializzato: impossibile eseguire login");
            return ResponseEntity.status(500).body("Server error");
        }
        try {
            UtenteDTO u = dao.login(credenziali.getUsername(), credenziali.getPassword());
            return u != null ? ResponseEntity.ok(u) : ResponseEntity.status(401).body("Negato");
        } catch (SQLException e) {
            logger.error("Errore durante login utente", e);
            return ResponseEntity.status(500).body("Server error");
        }
    }
}