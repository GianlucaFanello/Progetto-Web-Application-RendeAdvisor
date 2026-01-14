package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.NelCuoreDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.NelCuoreDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.NelCuoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

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
    public ResponseEntity<String> salva(@RequestBody NelCuoreDTO nelCuoreDTO) {
        try {
            boolean ok = nelCuoreService.aggiungiPreferito(nelCuoreDTO);
            return ok
                    ? ResponseEntity.ok("salvato")
                    : ResponseEntity.badRequest().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }

    @DeleteMapping("/rimuovi")
    public ResponseEntity<String> rimuovi(@RequestBody NelCuoreDTO nelCuoreDTO) {
        try {
            boolean ok = nelCuoreService.rimuoviPreferito(nelCuoreDTO);
            return ok
                    ? ResponseEntity.ok("rimosso")
                    : ResponseEntity.badRequest().build();
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }

    @GetMapping("/preferito")
    public ResponseEntity<Boolean> preferito(
            @RequestParam String nomeUtente,
            @RequestParam String nomeStruttura) {
        try {
            return ResponseEntity.ok(
                    nelCuoreService.isPreferito(nomeUtente, nomeStruttura)
            );
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<ArrayList<String>> lista(@RequestParam String nomeUtente) {
        try {
            return ResponseEntity.ok(
                    nelCuoreService.listaPreferiti(nomeUtente)
            );
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
