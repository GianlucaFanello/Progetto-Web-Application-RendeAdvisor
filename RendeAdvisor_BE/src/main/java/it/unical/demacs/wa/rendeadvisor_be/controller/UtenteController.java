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
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/utenti")
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController {
    private final UtenteService utenteService;
    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/registrazione")
    public ResponseEntity<ApiResponse<Void>> registra(@RequestBody UtenteDTO utente) {
        if (!Pattern.matches(EMAIL_REGEX, utente.getEmail().trim())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Formato email non valido", null));
        }
        if (utente.getPassword() == null || utente.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "La password deve avere almeno 6 caratteri", null));
        }
        boolean ok = utenteService.registraUtente(utente);
        if (ok) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Registrazione avvenuta", null));
        } else {
            logger.error("Registrazione fallita per utente: {}", utente.getUsername());
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Errore nella registrazione", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UtenteDTO>> login(@RequestBody LoginDTO credenziali, HttpSession session) {
        UtenteDTO utente = utenteService.loginUtente(credenziali);
        if (utente != null) {
            session.setAttribute("username", utente.getUsername());
            return ResponseEntity.ok(new ApiResponse<>(true, "Accesso riuscito!", utente));
        } else {
            logger.warn("Login fallito per utente: {}", credenziali.getEmail());
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Credenziali errate", null));
        }
    }

    @GetMapping("/isLogged")
    public ResponseEntity<?> isLogged(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Utente non autenticato", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true,  "Utente Autenticato",username));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UtenteDTO>> me(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Non autenticato", null));
        }
        UtenteDTO utente = utenteService.findByUsername(username);
        return ResponseEntity.ok(new ApiResponse<>(true, "Invio dati", utente));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession httpSession) {
        httpSession.invalidate();
        return ResponseEntity.ok(new ApiResponse<>(true, "Logout effettuato", null));
    }

    @PostMapping("/modifica/salva")
    public ResponseEntity<ApiResponse<UtenteDTO>> modifica(
            HttpSession session,
            @RequestParam String username,
            @RequestParam String nome,
            @RequestParam String cognome,
            @RequestParam String email,
            @RequestParam(required = false) String descrizione,
            @RequestParam(required = false) MultipartFile immagine,
            @RequestParam String eliminata
    ) {
        try {
            // 1. Recupero username vecchio dalla sessione
            String usernameVecchio = (String) session.getAttribute("username");

            if (usernameVecchio == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, "Utente non loggato", null));
            }


            UtenteDTO utente = new UtenteDTO();
            utente.setUsername(username); // username NUOVO
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setEmail(email);
            utente.setDescrizione(descrizione);

            if (immagine != null && !immagine.isEmpty()) {
                utente.setImmagine(immagine.getBytes());
            }
            boolean elim = Objects.equals(eliminata, "true");
            UtenteDTO aggiornato = utenteService.modificaProfilo(utente, usernameVecchio, elim);

            // 5. Aggiorno la sessione con lo username nuovo
            session.setAttribute("username", username);

            return ResponseEntity.ok(new ApiResponse<>(true, "Profilo aggiornato", aggiornato));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Errore durante la modifica", null));
        }
    }
}