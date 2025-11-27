package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import it.unical.demacs.wa.rendeadvisor_be.dao.IUtenteDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAO implements IUtenteDAO {

    Connection connection ;

    UtenteDAO(Connection con) {
        connection = con ;
    }

    // --- Implementazione metodi interface

    @Override
    public UtenteDTO getUtenteByUsername(String username) throws SQLException{

        String query = "SELECT * FROM Utente WHERE username = ?" ;

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        UtenteDTO utente = null;

        while (rs.next()) {
            utente = new UtenteDTO();
            utente.setNome(rs.getString("nome"));
            utente.setCognome(rs.getString("cognome"));
            utente.setUsername(rs.getString("username"));
            utente.setEmail(rs.getString("email"));
            utente.setDescrizione(rs.getString("descrizione"));
            utente.setImmagine(rs.getBytes("immagine"));
        }

        return utente;

    }

    @Override
    public UtenteDTO getUtenteByEmail(String email) throws SQLException {

        String query = "SELECT * FROM Utente WHERE email=?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, query);

        ResultSet rs = ps.executeQuery();

        UtenteDTO utente = null ;

        while(rs.next()) {
            utente = new UtenteDTO();
            utente.setNome(rs.getString("nome"));
            utente.setCognome(rs.getString("cognome"));
            utente.setUsername(rs.getString("username"));
            utente.setEmail(rs.getString("email"));
            utente.setDescrizione(rs.getString("descrizione"));
            utente.setImmagine(rs.getBytes("immagine"));
        }


        return utente;
    }

    @Override
    public boolean insertUtente(UtenteDTO utente) throws SQLException {
        String query = "INSERT INTO Utente(nome, cognome, username, email, descrizione, immagine)" +
                "VALUES(?,?,?;?,?,?)" ;

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, utente.getNome());
        ps.setString(2, utente.getCognome());
        ps.setString(3, utente.getUsername());
        ps.setString(4, utente.getEmail());
        ps.setString(5, utente.getDescrizione());
        ps.setBytes(6, utente.getImmagine());

        int inserito = ps.executeUpdate(query);
        return inserito == 1 ?  true : false ;
    }

    @Override
    public ArrayList<UtenteDTO> getAllUtenti() throws SQLException {

        String query = "SELECT * FROM Utente";

        PreparedStatement ps = connection.prepareStatement(query);

        ResultSet rs= ps.executeQuery();

        ArrayList<UtenteDTO> listaUtenti = new ArrayList<>();

        while (rs.next()) {
            String nome = rs.getString("nome");
            String cognome = rs.getString("cognome");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String descrizione = rs.getString("descrizione");
            byte[] img = rs.getBytes("immagine");
            String psw = rs.getNString("password");

            UtenteDTO utente = new UtenteDTO(username, nome, cognome, email, psw, descrizione, img );

            listaUtenti.add(utente);
        }
        return listaUtenti;
    }

    @Override
    public boolean updateUtente(UtenteDTO utente) throws SQLException {
        String query = "UPDATE Utente SET nome = ?, cognome = ?, email = ?, username = ?, descrizione = ?, immagine = ?" ;

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utente.getNome());
        ps.setString(2, utente.getCognome());
        ps.setString(3, utente.getEmail());
        ps.setString(4, utente.getUsername());
        ps.setString(5, utente.getDescrizione());
        ps.setBytes(6, utente.getImmagine());
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    @Override
    public boolean deleteUtente(UtenteDTO utente) throws SQLException {

        String query = "DELETE FROM Utente where username = ?" ;

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utente.getUsername());
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }
}
