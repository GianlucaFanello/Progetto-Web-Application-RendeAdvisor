package it.unical.demacs.wa.rendeadvisor_be.model.dto;

import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RecensioneDAO;

import java.sql.SQLException;
import java.util.List;

public class RecensioneProxy implements IRecensione {
    private RecensioneDAO recensioneDAO;
    private String nomeAttivita;
    private List<RecensioneDTO> listaRecensioni;

    public RecensioneProxy(RecensioneDAO dao, String nomeAttivita) {
        this.recensioneDAO = dao;
        this.nomeAttivita = nomeAttivita;
    }

    @Override
    public List<RecensioneDTO> getRecensioni() throws SQLException {
        if (listaRecensioni == null) {
            listaRecensioni = recensioneDAO.findByLocale(nomeAttivita);
        }
        return listaRecensioni;
    }
}
