package it.unical.demacs.wa.rendeadvisor_be.service;

import it.unical.demacs.wa.rendeadvisor_be.dao.INelCuoreDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.NelCuoreDTO;
import org.springframework.stereotype.Service;
import tools.jackson.core.ObjectReadContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;


@Service
public class NelCuoreService {

    private INelCuoreDAO nelCuoreDAO;

    public NelCuoreService(INelCuoreDAO nelCuoreDAO) {
        this.nelCuoreDAO = nelCuoreDAO;
    }

    public boolean aggiungiPreferito(NelCuoreDTO dto) throws SQLException {
        return nelCuoreDAO.addPreferito(
                dto.getNomeUtente(),
                dto.getNomeStruttura()
        );
    }

    public boolean rimuoviPreferito(NelCuoreDTO dto) throws SQLException {
        return nelCuoreDAO.removePreferito(
                dto.getNomeUtente(),
                dto.getNomeStruttura()
        );
    }

    public boolean isPreferito(String nomeUtente, String nomeStruttura) throws SQLException {
        return nelCuoreDAO.isPreferito(nomeUtente, nomeStruttura);
    }

    public ArrayList<AttivitaDTO> listaPreferiti(String nomeUtente) throws SQLException {
        ArrayList<AttivitaDTO> attivita = nelCuoreDAO.findAllByUser(nomeUtente);

        for(AttivitaDTO a: attivita){
            if(a.getImmagine() != null ) {
                a.setImmagineBase64(Base64.getEncoder().encodeToString(a.getImmagine()));
                a.setImmagine(null);
            }
        }

        return attivita;
    }

    public Integer countPreferitiLocale(String nomeStruttura) {
        return nelCuoreDAO.countPreferitiLocale(nomeStruttura);
    }
}
