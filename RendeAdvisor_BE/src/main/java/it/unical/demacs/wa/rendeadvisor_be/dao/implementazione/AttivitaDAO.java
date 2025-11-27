package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import it.unical.demacs.wa.rendeadvisor_be.dao.IAttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttivitaDAO implements IAttivitaDAO {
    Connection connection;
    public AttivitaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertAttivita(AttivitaDTO attivita) throws Exception {
        String query = "INSERT INTO Risposte(nomeLocale, proprietario, telefono, email,  immagine, descrizione, indirizzo, tipo)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, attivita.getNomeLocale());
        ps.setString(2, attivita.getProprietario());
        ps.setString(3, attivita.getTelefono());
        ps.setString(4, attivita.getEmail());
        ps.setBytes(5, attivita.getImmagine());
        ps.setString(6, attivita.getDescrizione());
        ps.setString(7, attivita.getIndirizzo());
        ps.setString(8, attivita.getTipo());
        ps.executeUpdate();

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    @Override
    public AttivitaDTO findByPrimaryKey(String nomeLocale) throws SQLException {

        String query = "SELECT * FROM Attivita WHERE nomeLocale = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeLocale);

        ResultSet rs = ps.executeQuery();
        AttivitaDTO attivitaDTO = null;
        if (rs.next()) {

            String nome = rs.getString("nomeLocale");
            String proprietario = rs.getString("proprietario");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            byte[] immagine = rs.getBytes("immagine");
            String descrizione = rs.getString("descrizione");
            String indirizzo = rs.getString("indirizzo");
            String tipo = rs.getString("tipo");

            attivitaDTO = new AttivitaDTO(nome, proprietario, telefono, email,  immagine, descrizione,indirizzo, tipo);
        }

        return attivitaDTO;
    }

    @Override
    public ArrayList<AttivitaDTO> findAll() throws SQLException {
        String query = "SELECT * FROM Attivita";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        ArrayList<AttivitaDTO> listaAttivita = new ArrayList<>();
        while (rs.next()) {
            String nome = rs.getString("nomeLocale");
            String proprietario = rs.getString("proprietario");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            byte[] immagine = rs.getBytes("immagine");
            String descrizione = rs.getString("descrizione");
            String indirizzo = rs.getString("indirizzo");
            String tipo = rs.getString("tipo");
            AttivitaDTO attivitaDTO = new AttivitaDTO(nome, proprietario, telefono, email,  immagine, descrizione,indirizzo, tipo);
        }
        return listaAttivita;
    }

    @Override
    public ArrayList<AttivitaDTO> findByTipo(String tipo) throws  SQLException{
        String  query = "SELECT * FROM Attivita WHERE tipo = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, tipo);

        ResultSet rs = ps.executeQuery();
        ArrayList<AttivitaDTO> listaAttivitaByTipo = new ArrayList<>();
        while (rs.next()) {
            String nome = rs.getString("nomeLocale");
            String proprietario = rs.getString("proprietario");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            byte[] immagine = rs.getBytes("immagine");
            String descrizione = rs.getString("descrizione");
            String indirizzo = rs.getString("indirizzo");

            AttivitaDTO attivitaDTO = new AttivitaDTO(nome, proprietario, telefono, email,  immagine, descrizione,indirizzo, tipo);
        }

        return listaAttivitaByTipo;
    }

    @Override
    public boolean updateAttivita(AttivitaDTO attivita) throws SQLException {
        String query = "UPDATE FROM Attivita WHERE nomeLocale = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, attivita.getNomeLocale());

        return ps.executeUpdate() == 1;
    }


    @Override
    public boolean delete(String nomeLocale) throws  SQLException{
        String query = "DELETE FROM Attivita WHERE nomeLocale = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeLocale);
        return ps.executeUpdate() == 1;
    }
}
