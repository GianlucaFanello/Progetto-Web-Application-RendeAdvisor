package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.ApiResponse;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.LoginDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                    new ApiResponse<>(true,"Registrazione avvenuta", null)
            );
        } else {
            logger.error("Registrazione fallita per utente: {}", utente.getUsername());
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false,"Errore nella registrazione", null)
            );
        }
    }

    // Gestisce l'accesso degli utenti esistenti
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UtenteDTO>> login(@RequestBody LoginDTO credenziali,  HttpSession session) {
        UtenteDTO utente = utenteService.loginUtente(credenziali);
        if (utente != null) {
            session.setAttribute("username", utente.getUsername());
            return ResponseEntity.ok(new ApiResponse<>(true, "Accesso riuscito!", utente)); // dati sicuri, password = null
        } else {
            logger.warn("Login fallito per utente: {}", credenziali.getEmail());
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Credenziali errate", null));
        }
    }


    @GetMapping("/isLogged")
    public ResponseEntity<?> isLogged(HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Utente non autenticato",null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true,  "Accesso riuscito",null));
    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UtenteDTO>> me(HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return ResponseEntity.status(401).body(new ApiResponse<>(false,"Non autenticato",null));
        }
        UtenteDTO utente = utenteService.findByUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(true, "Invio dati", utente));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession httpSession) {
        httpSession.invalidate();
        return ResponseEntity.ok(new ApiResponse<>(true,"Logout effettuato",null));
    }

}