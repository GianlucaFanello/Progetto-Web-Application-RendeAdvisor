package it.unical.demacs.wa.rendeadvisor_be.service;

import it.unical.demacs.wa.rendeadvisor_be.dao.IRispostaDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RispostaDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class RispostaService {

    private IRispostaDAO rispostaDAO;

    public RispostaService(IRispostaDAO rispostaDAO) {
        this.rispostaDAO = rispostaDAO;
    }


    public void salvaRisposta(RispostaDTO risposta) throws SQLException {
        rispostaDAO.insertRisposta(risposta);
    }

    public List<RispostaDTO> getRisposteByRecensione(int idRecensione) throws SQLException {
        RecensioneDTO recensioneDTO = new RecensioneDTO();
        recensioneDTO.setId(String.valueOf(idRecensione));
        return rispostaDAO.getRisposteByRecensioneId(recensioneDTO);
    }
}
