package it.unical.demacs.wa.rendeadvisor_be.dao;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUtenteDAO {

        public UtenteDTO getUtenteByUsername(String username) throws SQLException;

        public UtenteDTO getUtenteByEmail(String email) throws SQLException;

        public boolean insertUtente(UtenteDTO utente) throws SQLException;

        public ArrayList<UtenteDTO> getAllUtenti() throws SQLException;

        public boolean updateUtente(UtenteDTO utente) throws SQLException;

        public boolean deleteUtente(UtenteDTO utente) throws SQLException;
}
