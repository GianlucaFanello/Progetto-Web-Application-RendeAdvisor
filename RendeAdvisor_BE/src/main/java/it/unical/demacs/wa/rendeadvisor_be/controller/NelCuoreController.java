package it.unical.demacs.wa.rendeadvisor_be.controller;


import it.unical.demacs.wa.rendeadvisor_be.dao.INelCuoreDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.NelCuoreDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.NelCuoreDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
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

    private INelCuoreDAO nelCuoreDAO;

    public NelCuoreController() {
        try{
            this.nelCuoreDAO = new NelCuoreDAO(DBManager.getInstance().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/salva")
    public ResponseEntity<String> salva(@RequestBody NelCuoreDTO nelCuoreDTO) {
        try {
            boolean rs = nelCuoreDAO.addPreferito(nelCuoreDTO.getNomeUtente(),nelCuoreDTO.getNomeStruttura());
            if(rs) {
                return ResponseEntity.ok("salvato");
            }
            return ResponseEntity.badRequest().build();

        }
        catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");

        }
    }

    @DeleteMapping("/rimuovi")
    public ResponseEntity<String> rimuovi(@RequestBody NelCuoreDTO nelCuoreDTO) {
        try {
            boolean ok = nelCuoreDAO.removePreferito(nelCuoreDTO.getNomeUtente(),nelCuoreDTO.getNomeStruttura());
            if(ok) {
                return ResponseEntity.ok("rimosso");
            }
            return ResponseEntity.badRequest().build();
        } catch (SQLException e) {
            return ResponseEntity.badRequest().body("Server error");
        }
    }

    @GetMapping("/preferito")
    public ResponseEntity<Boolean> preferito(@RequestParam String nomeUtente, @RequestParam String nomeStruttura) {
        try {
            boolean ok = nelCuoreDAO.isPreferito(nomeUtente, nomeStruttura);
            return ResponseEntity.ok(ok);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<ArrayList<String>> lista(@RequestParam String nomeUtente) {
        try {
            return ResponseEntity.ok(nelCuoreDAO.findAllByUser(nomeUtente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
