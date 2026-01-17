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
        List<AttivitaDTO> lista = attivitaDAO.findAll();

        for (AttivitaDTO attivita : lista) {
            if (attivita.getImmagine() != null) {
                attivita.setImmagineBase64(
                        Base64.getEncoder().encodeToString(attivita.getImmagine())
                );
                attivita.setImmagine(null); // pulizia
            }
        }
        return lista;
    }



    public List<AttivitaDTO> getRistoranti() throws SQLException {
        List<AttivitaDTO> lista = attivitaDAO.findByTipo("Ristorante");

        for (AttivitaDTO attivita : lista) {
            if (attivita.getImmagine() != null) {
                attivita.setImmagineBase64(
                        Base64.getEncoder().encodeToString(attivita.getImmagine())
                );
                attivita.setImmagine(null);
            }
        }
        return lista;
    }


    public List<AttivitaDTO> getHotel() throws SQLException {
        List<AttivitaDTO> lista = attivitaDAO.findByTipo("hotel");

        for (AttivitaDTO attivita : lista) {
            if (attivita.getImmagine() != null) {
                attivita.setImmagineBase64(
                        Base64.getEncoder().encodeToString(attivita.getImmagine())
                );
                attivita.setImmagine(null);
            }
        }

        return lista;
    }


    public AttivitaDTO getDettaglio(String nomeLocale) throws SQLException {
        AttivitaDTO attivita = attivitaDAO.findByPrimaryKey(nomeLocale);

        if (attivita != null && attivita.getImmagine() != null) {
            attivita.setImmagineBase64(
                    Base64.getEncoder().encodeToString(attivita.getImmagine())
            );
            attivita.setImmagine(null); // pulizia
        }

        return attivita;
    }

    public boolean salvaAttivita(AttivitaDTO attivita) throws SQLException {
        return attivitaDAO.insertAttivita(attivita);
    }

    public List<AttivitaDTO> search(String query) throws SQLException {
        List<AttivitaDTO> lista = attivitaDAO.search(query);

        for (AttivitaDTO attivita : lista) {
            if (attivita.getImmagine() != null) {
                attivita.setImmagineBase64(
                        Base64.getEncoder().encodeToString(attivita.getImmagine())
                );
                attivita.setImmagine(null); // pulizia
            }
        }
        return lista;
    }


}
