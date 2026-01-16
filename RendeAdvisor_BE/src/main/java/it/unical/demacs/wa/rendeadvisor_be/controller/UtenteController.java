package it.unical.demacs.wa.rendeadvisor_be.controller;


import it.unical.demacs.wa.rendeadvisor_be.model.dto.ApiResponse;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.LoginDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.UtenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;

@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController {
    private final UtenteService utenteService;
    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }
    // Gestisce la creazione di un nuovo account
    @PostMapping("/registrazione")
    public ResponseEntity<ApiResponse<Void>> registra(@RequestBody UtenteDTO utente) {
        boolean ok = utenteService.registraUtente(utente);
        if (ok) {
            return ResponseEntity.ok(
                    new ApiResponse<>(true,"Registrazione avvenuta")
            );
        } else {
            logger.error("Registrazione fallita per utente: {}", utente.getUsername());
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false,"Errore nella registrazione")
            );
        }
    }

    // Gestisce l'accesso degli utenti esistenti
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UtenteDTO>> login(@RequestBody LoginDTO credenziali) {
        UtenteDTO utente = utenteService.loginUtente(credenziali);
        if (utente != null) {
            return ResponseEntity.ok(new ApiResponse<UtenteDTO>(true, "Accesso riuscito!", utente)); // dati sicuri, password = null
        } else {
            logger.warn("Login fallito per utente: {}", credenziali.getEmail());
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Credenziali errate", null));
        }
    }
}