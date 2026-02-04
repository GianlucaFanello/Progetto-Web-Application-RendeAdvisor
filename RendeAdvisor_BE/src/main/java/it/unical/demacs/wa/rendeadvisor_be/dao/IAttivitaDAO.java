package it.unical.demacs.wa.rendeadvisor_be.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;

public interface IAttivitaDAO {

    boolean insertAttivita(AttivitaDTO attivita) throws SQLException;

    AttivitaDTO findByNome(String nome) throws SQLException;

    // Cerca un'attività per nome
    AttivitaDTO findByPrimaryKey(String nomeLocale) throws SQLException;

    // Restituisce tutte le attività
    ArrayList<AttivitaDTO> findAll() throws SQLException;

    // Restituisce solo Ristorante o solo Hotel
    ArrayList<AttivitaDTO> findByTipo(String tipo) throws SQLException;

    //Aggiorna i dati (descrizione, foto, ecc.)
    boolean updateAttivita(AttivitaDTO attivita, String vecchioNome) throws SQLException;

    //Rimuove un'attività dal sistema
    boolean delete(String nomeLocale) throws SQLException;

    List<AttivitaDTO> search(String query) throws SQLException;

    List<AttivitaDTO> listaAttivitaByProprietario(String username) throws SQLException;
}
