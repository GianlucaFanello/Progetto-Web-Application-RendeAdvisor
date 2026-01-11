package it.unical.demacs.wa.rendeadvisor_be.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unical.demacs.wa.rendeadvisor_be.dao.IRecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;

@RestController
@RequestMapping("/api/recensioni")
@CrossOrigin(origins = "http://localhost:4200")
public class RecensioniController {

    private IRecensioneDAO recensioneDAO;

    public RecensioniController() {
        try {
            this.recensioneDAO = new RecensioneDAO(DBManager.getInstance().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Restituisce recensioni di un locale specifico
    @GetMapping("/locale/{nomeLocale}")
    public List<RecensioneDTO> getByLocale(@PathVariable String nomeLocale) {
        return recensioneDAO.findByLocale(nomeLocale);
    }

    // Salva una nuova recensione
    @PostMapping("/salva")
    public ResponseEntity<String> salva(@RequestBody RecensioneDTO recensione) {
        try {
            int id = recensioneDAO.save(recensione);
            return ResponseEntity.ok("Recensione salvata con ID: " + id);
        } catch (SQLException e) {
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }
}