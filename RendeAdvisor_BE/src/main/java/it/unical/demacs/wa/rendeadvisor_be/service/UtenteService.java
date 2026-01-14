package it.unical.demacs.wa.rendeadvisor_be.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.unical.demacs.wa.rendeadvisor_be.dao.IUtenteDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;

@Service
public class UtenteService {

    private final IUtenteDAO dao;
    private static final Logger logger = LoggerFactory.getLogger(UtenteService.class);

    public UtenteService(IUtenteDAO dao) {
        this.dao = dao;
    }

    // Logica di registrazione
    public boolean registraUtente(UtenteDTO utente) {
        try {
            return dao.insertUtente(utente);
        } catch (SQLException e) {
            logger.error("Errore durante registrazione utente", e);
            return false;
        }
    }

    // Logica di login
    public UtenteDTO loginUtente(String username, String password) {
        try {
            return dao.login(username, password);
        } catch (SQLException e) {
            logger.error("Errore durante login utente", e);
            return null;
        }
    }
}
