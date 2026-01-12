package it.unical.demacs.wa.rendeadvisor_be.dao;

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
    ArrayList<String> findAllByUser(String nomeUtente);
}
