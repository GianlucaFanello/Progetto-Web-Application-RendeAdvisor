package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import it.unical.demacs.wa.rendeadvisor_be.service.RecensioneService;
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
            throw new RuntimeException("cosa mettiamo raga?", e);
        }
    }

    @GetMapping("/locale/{nomeLocale}")
    public List<RecensioneDTO> getByLocale(@PathVariable String nomeLocale) {
        return recensioniService.getRecensioniByLocale(nomeLocale);
    }

    @PostMapping("/salva")
    public ResponseEntity<String> salva(@RequestBody RecensioneDTO recensione) {
        try {
            int id = recensioniService.salvaRecensione(recensione);
            return ResponseEntity.ok("Recensione salvata con id " + id);
        } catch (SQLException e) {
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }
}
