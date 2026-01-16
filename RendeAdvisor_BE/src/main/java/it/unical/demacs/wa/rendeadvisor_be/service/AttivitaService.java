package it.unical.demacs.wa.rendeadvisor_be.service;

import it.unical.demacs.wa.rendeadvisor_be.dao.IAttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class AttivitaService {

    private IAttivitaDAO attivitaDAO;

    public AttivitaService(IAttivitaDAO attivitaDAO) {
        this.attivitaDAO = attivitaDAO;
    }


    public List<AttivitaDTO> getTutte() throws SQLException {
        return attivitaDAO.findAll();
    }


    public List<AttivitaDTO> getRistoranti() throws SQLException {
        return attivitaDAO.findByTipo("Ristorante");
    }


    public List<AttivitaDTO> getHotel() throws SQLException {
        return attivitaDAO.findByTipo("Hotel");
    }


    public AttivitaDTO getDettaglio(String nomeLocale) throws SQLException {
        return attivitaDAO.findByPrimaryKey(nomeLocale);
    }

    public boolean salvaAttivita(AttivitaDTO attivita) throws SQLException {
        return attivitaDAO.insertAttivita(attivita);
    }

    public List<AttivitaDTO> search(String query) throws SQLException {
        return attivitaDAO.search(query);
    }

}
