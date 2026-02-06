package it.unical.demacs.wa.rendeadvisor_be.service;

import java.sql.SQLException;
import java.util.Base64;

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
    public boolean registraUtente(UtenteDTO utenteDto) {
        try {
            UtenteDTO utente =  new UtenteDTO();
            utente.setEmail(utenteDto.getEmail().trim());
            utente.setNome(utenteDto.getNome().trim());
            utente.setCognome(utenteDto.getCognome().trim());
            utente.setUsername(utenteDto.getUsername().trim());
            utente.setImmagine(utenteDto.getImmagine());

            String hashed = passwordService.hashPassword(utenteDto.getPassword());
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
            String email = credenziali.getEmail().trim();

            String passwordHashed = dao.getPasswordByEmail(email);
            if (passwordHashed != null && passwordService.verifyPassword(credenziali.getPassword(), passwordHashed)) {
                UtenteDTO utente = dao.getUtenteByEmail(email);
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
            UtenteDTO utente = dao.getUtenteByUsername(username);
            if (utente.getImmagine() != null) {
                utente.setImmagineBase64(Base64.getEncoder().encodeToString(utente.getImmagine()));
                utente.setImmagine(null);
            }
            return utente ;
        }
        catch (SQLException e) {
            logger.error("Errore durante la ricerca dell'utente con username: {}", username, e);
            return null;
        }
    }

    public UtenteDTO modificaProfilo(UtenteDTO utenteDto, String usernameVecchio, boolean eliminata) {
        try {

            UtenteDTO vecchioUtente = dao.getUtenteByUsername(usernameVecchio.trim());

            UtenteDTO utente =  new UtenteDTO();
            utente.setEmail(utenteDto.getEmail().trim());
            utente.setNome(utenteDto.getNome().trim());
            utente.setCognome(utenteDto.getCognome().trim());
            utente.setUsername(utenteDto.getUsername().trim());
            utente.setImmagine(utenteDto.getImmagine());
            utente.setDescrizione(utenteDto.getDescrizione().trim());

            if(eliminata){
                utente.setImmagine(null);
            }
            else if(utente.getImmagine() == null && vecchioUtente.getImmagine() != null) {
                utente.setImmagine(vecchioUtente.getImmagine());
            }

            if (dao.updateUtente(utente, usernameVecchio.trim())){
                if (utente.getImmagine() != null) {
                    String base64 = Base64.getEncoder().encodeToString(utente.getImmagine());
                    utente.setImmagineBase64(base64);
                }
                else {
                    utente.setImmagineBase64(null);
                }
                return utente;
            };
            return null;
        } catch (SQLException e) {
            logger.error("Errore durante modifica profilo", e);
            return null;
        }
    }
}
