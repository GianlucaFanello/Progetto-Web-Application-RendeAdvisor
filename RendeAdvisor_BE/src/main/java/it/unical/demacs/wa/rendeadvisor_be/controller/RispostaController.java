package it.unical.demacs.wa.rendeadvisor_be.controller;

import it.unical.demacs.wa.rendeadvisor_be.dao.IRispostaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RispostaDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RispostaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/risposta")
@CrossOrigin(origins = "http://localhost:4200")
public class RispostaController {
    private IRispostaDAO rispostaDAO;

    public RispostaController() throws SQLException {
        try {
            this.rispostaDAO = new RispostaDAO(DBManager.getInstance().getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Aggiungi una nuova risposta
    @PostMapping("/salva")
    public void salva(@RequestBody RispostaDTO risposta) {
        try {
            rispostaDAO.insertRisposta(risposta);
            System.out.println("Salvata risposta: " + risposta);
        } catch (SQLException e) {
            System.out.println("Qualcosa è andato storto con l'inserimento della risposta.");
        }
    }

    // Restituisce risposte di una recensione specifica
    @GetMapping("/risposte/{idRecensione}")
    public List<RispostaDTO> getByRecensione(@PathVariable int idRecensione) throws SQLException {
        RecensioneDTO dto = new RecensioneDTO();
        dto.setId(String.valueOf(idRecensione));
        return rispostaDAO.getRisposteByRecensioneId(dto);
    }
}
