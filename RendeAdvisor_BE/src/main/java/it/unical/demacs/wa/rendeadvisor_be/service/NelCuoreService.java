package it.unical.demacs.wa.rendeadvisor_be.service;

import it.unical.demacs.wa.rendeadvisor_be.dao.INelCuoreDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.NelCuoreDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;


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

    public ArrayList<String> listaPreferiti(String nomeUtente) throws SQLException {
        return nelCuoreDAO.findAllByUser(nomeUtente);
    }
}
