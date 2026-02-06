package it.unical.demacs.wa.rendeadvisor_be.dao;

import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface INelCuoreDAO {

    //Aggiunge la struttura dai preferiti
    boolean addPreferito(String nomeUtente, String nomeStruttura) throws SQLException;

    //Rimuove la struttura dai preferiti
    boolean removePreferito(String nomeUtente, String nomeStruttura) throws SQLException;

    //cuore pieno/vuoto
    boolean isPreferito(String nomeUtente, String nomeStruttura);

    // Per la pagina Profilo "Nel Cuore"
    ArrayList<AttivitaDTO> findAllByUser(String nomeUtente);

    Integer countPreferitiLocale(String nomeStruttura);
}
