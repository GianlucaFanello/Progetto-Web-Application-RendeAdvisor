package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unical.demacs.wa.rendeadvisor_be.dao.IUtenteDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.UtenteDTO;
import org.springframework.stereotype.Repository;

@Repository
public class UtenteDAO implements IUtenteDAO {

    Connection connection ;

    public UtenteDAO(Connection con) {
        this.connection = con ;
    }


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

        String query = "SELECT * FROM utente WHERE email = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, email);

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
        // Inserisce nome, cognome, username, email, password, descrizione e immagine
        String query = "INSERT INTO utente(nome, cognome, username, email, password) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getUsername());
            ps.setString(4, utente.getEmail());
            ps.setString(5, utente.getPassword()); // Qui andrebbe BCrypt in futuro
            return ps.executeUpdate() > 0;
        }
    }



    @Override
    public UtenteDTO login(String username, String password) throws SQLException {
        // Controlla se esiste un utente con username e password corrispondenti
        String query = "SELECT * FROM utente WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public String getPasswordByEmail(String email) throws SQLException {
        String sql = "SELECT password FROM utente WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password"); // hash salvato
            } else {
                return null;
            }
        }
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
        String query = "UPDATE utente SET nome = ?, cognome = ?, email = ?, username = ?, descrizione = ?, immagine = ? WHERE username = ?" ;

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utente.getNome());
        ps.setString(2, utente.getCognome());
        ps.setString(3, utente.getEmail());
        ps.setString(4, utente.getUsername());
        ps.setString(5, utente.getDescrizione());
        ps.setBytes(6, utente.getImmagine());
        ps.setString(7, utente.getUsername());
        int updated = ps.executeUpdate();

        return updated > 0;
    }

    @Override
    public boolean deleteUtente(UtenteDTO utente) throws SQLException {

        String query = "DELETE FROM utente where username = ?" ;

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, utente.getUsername());
        int deleted = ps.executeUpdate();

        return deleted > 0;
    }

    // Metodo per trasformare una riga del db in un oggetto Java
    private UtenteDTO mapRow(ResultSet rs) throws SQLException {
        return new UtenteDTO(
                rs.getString("username"), rs.getString("nome"),
                rs.getString("cognome"), rs.getString("email"),
                null, // Non restituiamo la password per sicurezza
                rs.getString("descrizione"), rs.getBytes("immagine")
        );
    }


}
