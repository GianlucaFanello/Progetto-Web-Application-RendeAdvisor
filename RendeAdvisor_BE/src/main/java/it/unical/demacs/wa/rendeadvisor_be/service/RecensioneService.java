package it.unical.demacs.wa.rendeadvisor_be.service;

import it.unical.demacs.wa.rendeadvisor_be.dao.IRecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RecensioneService {

    private IRecensioneDAO recensioneDAO;

    public RecensioneService(IRecensioneDAO recensioneDAO) {
        this.recensioneDAO = recensioneDAO;
    }


    public List<RecensioneDTO> getRecensioniByLocale(String nomeLocale) {
        return recensioneDAO.findByLocale(nomeLocale);
    }


    public int salvaRecensione(RecensioneDTO recensione) throws SQLException {
        return recensioneDAO.save(recensione);
    }

    public int numeroRecensioniUtente(String username) throws SQLException {
        return recensioneDAO.countRecensioniUtente(username);
    }

    public double getRatingStruttura(String nomeStruttura) throws SQLException {
        return recensioneDAO.getRatingStruttura(nomeStruttura);
    }

    public List<RecensioneDTO> getRecensioniByUtente(String username) throws SQLException {
        return recensioneDAO.findByUtente(username);
    }
}
