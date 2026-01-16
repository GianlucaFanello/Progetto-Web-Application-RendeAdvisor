package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.NelCuoreDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.ApiResponse;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.NelCuoreDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.NelCuoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/nelcuore")
@CrossOrigin(origins = "http://localhost:4200")
public class NelCuoreController {

    private NelCuoreService nelCuoreService;

    public NelCuoreController() {
        try {
            this.nelCuoreService = new NelCuoreService(
                    new NelCuoreDAO(DBManager.getInstance().getConnection())
            );
        } catch (SQLException e) {
            throw new RuntimeException("Errore inizializzazione NelCuoreService", e);
        }
    }

    @PostMapping("/salva")
    public ResponseEntity<ApiResponse<Void>> salva(@RequestBody NelCuoreDTO nelCuoreDTO) {
        try {
            boolean ok = nelCuoreService.aggiungiPreferito(nelCuoreDTO);

            if (ok) {
                return ResponseEntity.ok(
                        new ApiResponse<>(true, "Preferito salvato con successo", null)
                );
            }

            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Errore nel salvataggio", null)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }

    @DeleteMapping("/rimuovi")
    public ResponseEntity<ApiResponse<Void>> rimuovi(@RequestBody NelCuoreDTO nelCuoreDTO) {
        try {
            boolean ok = nelCuoreService.rimuoviPreferito(nelCuoreDTO);

            if (ok) {
                return ResponseEntity.ok(
                        new ApiResponse<>(true, "Preferito rimosso con successo", null)
                );
            }

            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "Errore nella rimozione", null)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }


    @GetMapping("/preferito")
    public ResponseEntity<ApiResponse<Boolean>> preferito(
            @RequestParam String nomeUtente,
            @RequestParam String nomeStruttura) {
        try {
            boolean isPreferito = nelCuoreService.isPreferito(nomeUtente, nomeStruttura);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Stato preferito recuperato", isPreferito)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }


    @GetMapping("/lista")
    public ResponseEntity<ApiResponse<List<String>>> lista(@RequestParam String nomeUtente) {
        try {
            List<String> lista = nelCuoreService.listaPreferiti(nomeUtente);

            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Lista preferiti inviata", lista)
            );

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Errore interno al server", null));
        }
    }

}
