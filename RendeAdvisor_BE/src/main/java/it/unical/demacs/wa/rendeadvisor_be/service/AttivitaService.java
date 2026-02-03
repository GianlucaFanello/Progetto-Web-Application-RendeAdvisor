package it.unical.demacs.wa.rendeadvisor_be.service;

import it.unical.demacs.wa.rendeadvisor_be.dao.IAttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Base64;
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
        return attivitaDAO.findByTipo("ristorante");
    }


    public List<AttivitaDTO> getHotel() throws SQLException {
        return attivitaDAO.findByTipo("hotel");
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

    public AttivitaDTO findByNome(String nome) throws SQLException {
        return attivitaDAO.findByNome(nome);
    }

    public List<AttivitaDTO> listaAttivitaByProprietario(String username) throws SQLException {

        List<AttivitaDTO> attivita = attivitaDAO.listaAttivitaByProprietario(username);

        for(AttivitaDTO a: attivita) {
            if (a.getImmagine() != null) {
                a.setImmagineBase64(Base64.getEncoder().encodeToString(a.getImmagine()));
                a.setImmagine(null);
            }
        }

        return attivita ;
    }

}
