package it.unical.demacs.wa.rendeadvisor_be.dao;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RispostaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IRispostaDAO {

    public ArrayList<RispostaDTO> getRisposteByUtente(UtenteDTO utente) throws SQLException;

    public ArrayList<RispostaDTO> getRisposteByRecensioneId(RecensioneDTO recensione) throws SQLException;

    public boolean insertRisposta(RispostaDTO risposta) throws SQLException;

    public boolean deleteRisposta(RispostaDTO risposta) throws SQLException;

    public boolean updateRisposta(RispostaDTO risposta) throws SQLException;
}
