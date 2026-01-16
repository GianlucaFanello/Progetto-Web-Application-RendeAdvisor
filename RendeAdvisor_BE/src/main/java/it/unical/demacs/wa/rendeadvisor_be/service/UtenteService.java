package it.unical.demacs.wa.rendeadvisor_be.service;

import java.sql.SQLException;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.LoginDTO;
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
    public UtenteDTO loginUtente(LoginDTO credenziali) {
        try {
            String passwordHashed = dao.getPasswordByEmail(credenziali.getEmail());
            if (passwordHashed != null && passwordService.verifyPassword(credenziali.getPassword(), passwordHashed)) {
                UtenteDTO utente = dao.getUtenteByEmail(credenziali.getEmail());
                return utente; // login OK
            }
            return null;
        } catch (SQLException e) {
            logger.error("Errore durante login utente", e);
            return null;
        }
    }

    public UtenteDTO findByUsername(String username) {
        try {
            return dao.getUtenteByUsername(username);
        }
        catch (SQLException e) {
            logger.error("Errore durante la ricerca dell'utente con username: {}", username, e);
            return null;
        }
    }
}
