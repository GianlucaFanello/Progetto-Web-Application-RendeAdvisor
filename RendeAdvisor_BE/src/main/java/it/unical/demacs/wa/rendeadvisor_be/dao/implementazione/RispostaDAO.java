package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.unical.demacs.wa.rendeadvisor_be.dao.IRispostaDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RispostaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;
import org.springframework.stereotype.Repository;

@Repository
public class RispostaDAO implements IRispostaDAO {

    private Connection connection;

    public RispostaDAO(Connection con) {
        this.connection = con;
    }

    @Override
    public ArrayList<RispostaDTO> getRisposteByUtente(UtenteDTO utente) throws SQLException {
        String query = "SELECT * FROM risposte WHERE utente = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utente.getUsername());

        ResultSet rs = ps.executeQuery();
        ArrayList<RispostaDTO> risposte = new ArrayList<>();

        while (rs.next()) {
            RispostaDTO risposta = new RispostaDTO();
            risposta.setIdRisposta(String.valueOf(rs.getInt("id")));
            risposta.setIdRecensione(String.valueOf(rs.getInt("id_recensione")));
            risposta.setUtente(rs.getString("utente"));
            risposta.setRisposta(rs.getString("risposta"));
            risposte.add(risposta);
        }
        return risposte;
    }

    @Override
    public ArrayList<RispostaDTO> getRisposteByRecensioneId(RecensioneDTO recensione) throws SQLException {
        String query = "SELECT * FROM risposte WHERE id_recensione = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, Integer.parseInt(recensione.getId()));

        ResultSet rs = ps.executeQuery();
        ArrayList<RispostaDTO> risposte = new ArrayList<>();

        while (rs.next()) {
            RispostaDTO risposta = new RispostaDTO();
            risposta.setIdRisposta(String.valueOf(rs.getInt("id")));
            risposta.setIdRecensione(String.valueOf(rs.getInt("id_recensione")));
            risposta.setUtente(rs.getString("utente"));
            risposta.setRisposta(rs.getString("risposta"));
            risposte.add(risposta);
        }
        return risposte;
    }

    @Override
    public boolean insertRisposta(RispostaDTO risposta) throws SQLException {
        String query = "INSERT INTO risposte (id_recensione, utente, risposta) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, Integer.parseInt(risposta.getIdRecensione()));
            ps.setString(2, risposta.getUtente());
            ps.setString(3, risposta.getRisposta());

            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    risposta.setIdRisposta(String.valueOf(keys.getInt(1)));
                }
            }
            return true;
        }
    }

    @Override
    public boolean deleteRisposta(RispostaDTO risposta) throws SQLException {
        String query = "DELETE FROM risposte WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(risposta.getIdRisposta()));
            int deleted = ps.executeUpdate();
            return deleted > 0;
        }
    }


    @Override
    public boolean updateRisposta(RispostaDTO risposta) throws SQLException {
        String query = "UPDATE risposte SET risposta = ?, utente = ?, id_recensione = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, risposta.getRisposta());
            ps.setString(2, risposta.getUtente());
            ps.setInt(3, Integer.parseInt(risposta.getIdRecensione()));
            ps.setInt(4, Integer.parseInt(risposta.getIdRisposta()));
            int updated = ps.executeUpdate();
            return updated > 0;
        }
    }
}
