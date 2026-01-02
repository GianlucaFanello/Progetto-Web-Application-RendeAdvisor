package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import it.unical.demacs.wa.rendeadvisor_be.dao.IRispostaDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RispostaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
    RICONTROLLARE TUTTI I NOMI DELLE VARIABILI NEL DB E CONTROLLARE CHE SIANO PRESENTI TUTTI I VALORI
*/

public class RispostaDAO implements IRispostaDAO {

    Connection connection ;

    public RispostaDAO(Connection con) throws SQLException {
        connection = con;
    }

    @Override
    public ArrayList<RispostaDTO> getRisposteByUtente(UtenteDTO utente) throws SQLException {
        String query = "SELECT * FROM Risposte WHERE utente = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utente.getUsername());

        ResultSet rs = ps.executeQuery();
        ArrayList<RispostaDTO> risposte = new ArrayList<>();


        /*
            CONTROLLARE I VALORI PRESENTI NEL DB PER LA RISPOSTA

            Completare i nomi delle colonne da prendere per le risposte
        */

        while (rs.next()) {
            RispostaDTO  risposta = new RispostaDTO();

            risposta.setIdRisposta(rs.getString(""));
            risposta.setIdRecensione(rs.getString(""));
            risposta.setUtente(rs.getString(""));

            risposte.add(risposta);
        }
        return risposte ;
    }

    @Override
    public ArrayList<RispostaDTO> getRisposteByRecensioneId(RecensioneDTO recensione) throws SQLException {
        String query = "SELECT * FROM Risposte WHERE recensione = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, recensione.getId());

        ResultSet rs = ps.executeQuery();
        ArrayList<RispostaDTO> risposte = new ArrayList<>();

        while (rs.next()) {
            RispostaDTO risposta = new RispostaDTO();

            risposta.setIdRisposta(rs.getString(""));
            risposta.setIdRecensione(rs.getString(""));
            risposta.setUtente(rs.getString(""));

            risposte.add(risposta);
        }
        return risposte;
    }

    @Override
    public boolean insertRisposta(RispostaDTO risposta) throws SQLException {
        String  query = "INSERT INTO Risposte VALUES (id, recensione, utente) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, risposta.getIdRisposta());
        ps.setString(2, risposta.getIdRecensione());
        ps.setString(3, risposta.getUtente());

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    @Override
    public boolean deleteRisposta(RispostaDTO risposta) throws SQLException {

        String query = "DELETE FROM Risposte WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, risposta.getIdRisposta());
        ps.executeUpdate();

        ResultSet rs = ps.executeQuery();
        return rs.next();
    }


    @Override
    public boolean updateRisposta(RispostaDTO risposta) throws SQLException {

        String query = "UPDATE Risposte SET utente = ?, recensione = ? WHERE id = ? ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, risposta.getUtente());
        ps.setString(2, risposta.getIdRecensione());
        ps.setString(3, risposta.getIdRisposta());

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }
}
