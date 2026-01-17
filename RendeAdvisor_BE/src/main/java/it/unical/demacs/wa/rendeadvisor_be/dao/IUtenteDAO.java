package it.unical.demacs.wa.rendeadvisor_be.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;

public interface IUtenteDAO {

    // Cerca un utente tramite il suo username
    UtenteDTO getUtenteByUsername(String username) throws SQLException;

    // Cerca un utente tramite la sua email
    UtenteDTO getUtenteByEmail(String email) throws SQLException;

    // Inserisce un nuovo utente nel database
    boolean insertUtente(UtenteDTO utente) throws SQLException;

    // Restituisce la lista di tutti gli utenti registrati
    ArrayList<UtenteDTO> getAllUtenti() throws SQLException;

    // Aggiorna i dati di un utente esistente
    boolean updateUtente(UtenteDTO utente, String usernameVecchio) throws SQLException;

    // Elimina un utente dal sistema
    boolean deleteUtente(UtenteDTO utente) throws SQLException;

    // Verifica le credenziali per l'accesso
    UtenteDTO login(String username, String password) throws SQLException;

    public String getPasswordByEmail(String email) throws SQLException;

}
