package it.unical.demacs.wa.rendeadvisor_be.dao;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IRecensioneDAO {
    // Salva la recensione nel DB e restituisce l'ID generato automaticamente
    int save(RecensioneDTO recensione) throws SQLException;

    // Trova tutte le recensioni associate a un determinato locale
    ArrayList<RecensioneDTO> findByLocale(String nomeLocale);

    // Trova tutte le recensioni scritte da un determinato utente
    ArrayList<RecensioneDTO> findByUtente(String nomeUtente) throws SQLException;

    // Cancella una recensione specifica tramite il suo ID
    void delete(int id) throws SQLException;

    int countRecensioniUtente(String username) throws SQLException;

    double getRatingStruttura(String nomeStruttura) throws SQLException;
}
