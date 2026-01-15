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
    private final PasswordService passwordService;
    private static final Logger logger = LoggerFactory.getLogger(UtenteService.class);


    public UtenteService(IUtenteDAO dao, PasswordService passwordService) {
        this.dao = dao;
        this.passwordService = passwordService;
    }

    // Logica di registrazione
    public boolean registraUtente(UtenteDTO utente) {
        try {
            String hashed = passwordService.hashPassword(utente.getPassword());
            utente.setPassword(hashed);
            return dao.insertUtente(utente);
        } catch (SQLException e) {
            logger.error("Errore durante registrazione utente", e);
            return false;
        }
    }

    // Logica di login
    public UtenteDTO loginUtente(String email, String password) {
        try {
            String passwordHashed = dao.getPasswordByEmail(email);
            if (passwordHashed != null && passwordService.verifyPassword(password, passwordHashed)) {
                UtenteDTO utente = dao.getUtenteByEmail(email);
                return utente; // login OK
            }
            return null;
        } catch (SQLException e) {
            logger.error("Errore durante login utente", e);
            return null;
        }
    }
}
