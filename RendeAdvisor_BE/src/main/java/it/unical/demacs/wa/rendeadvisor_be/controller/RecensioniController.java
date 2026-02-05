package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.ApiResponse;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.RecensioneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/recensioni")
@CrossOrigin(origins = "http://localhost:4200")
public class RecensioniController {

    private RecensioneService recensioniService;

    public RecensioniController() {
        try {
            this.recensioniService = new RecensioneService(new RecensioneDAO(DBManager.getInstance().getConnection()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/locale/{nomeLocale}")
    public ResponseEntity<ApiResponse<List<RecensioneDTO>>> getByLocale(@PathVariable String nomeLocale) {
        try {
            List<RecensioneDTO> recensioni = recensioniService.getRecensioniByLocale(nomeLocale);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Recensioni trovate", recensioni)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }

    @PostMapping("/salva")
    public ResponseEntity<ApiResponse<Integer>> salva(@RequestBody RecensioneDTO recensione) {
        try {
            int id = recensioniService.salvaRecensione(recensione);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Recensione salvata con successo", id)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore durante il salvataggio", null));
        }
    }

    @GetMapping("/n_rec")
    public ResponseEntity<ApiResponse<Integer>> getCountRecensioniByUsername(@RequestParam String username) {
        try {
            int count = recensioniService.numeroRecensioniUtente(username);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Controllo numero recensioni riuscito!", count)
            );
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }

    @GetMapping("/rating")
    public ResponseEntity<ApiResponse<Double>> getRatingByUsername(@RequestParam String nomeStruttura) {
        try{
            double rating = recensioniService.getRatingStruttura(nomeStruttura);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Rating restituito con successo!", rating)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }


    @GetMapping("/utente/{username}")
    public ResponseEntity<ApiResponse<List<RecensioneDTO>>> getByUtente(@PathVariable String username) {
        try {
            List<RecensioneDTO> recensioni = recensioniService.getRecensioniByUtente(username);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Recensioni utente trovate", recensioni)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }
}
