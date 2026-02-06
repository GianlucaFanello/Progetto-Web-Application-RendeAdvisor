package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import it.unical.demacs.wa.rendeadvisor_be.dao.IRecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

@Repository
public class RecensioneDAO implements IRecensioneDAO {
    private Connection connection;

    public RecensioneDAO(Connection connection) {
        this.connection = connection;
    }

    // Esegue INSERT e recupera la chiave primaria (ID) generata dal database (SERIAL)
    @Override
    public int save(RecensioneDTO recensione) throws SQLException {
        String sql = "INSERT INTO recensioni (nomeutente, nomelocale, testo, valutazione) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, recensione.getNomeUtente());
            ps.setString(2, recensione.getNomeLocale());
            ps.setString(3, recensione.getTesto());
            ps.setFloat(4, recensione.getValutazione());

            ps.executeUpdate();

            // Recuperiamo l'ID generato
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerato = rs.getInt(1);
                    recensione.setId(String.valueOf(idGenerato)); // Aggiorniamo il DTO
                    return idGenerato;
                }
            }
        }
        return -1;
    }

    // Esegue una DELETE basata sull' id univoco della recensione
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM recensioni WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public int countRecensioniUtente(String nomeUtente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM recensioni WHERE nomeutente = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeUtente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public double getRatingStruttura(String nomeStruttura) throws SQLException {
        String query = "SELECT AVG(valutazione) FROM recensioni WHERE nomelocale = ?";

        try ( PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nomeStruttura);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0;
        }
    }


    @Override
    public ArrayList<RecensioneDTO> findByLocale(String nomeLocale) {
        String query = """
        SELECT r.*, u.immagine AS img_utente
        FROM recensioni r
        JOIN utente u ON r.nomeutente = u.username
        WHERE r.nomelocale = ?
        """;

        return executeQueryWithUserImage(query, nomeLocale);
    }

    @Override
    public ArrayList<RecensioneDTO> findByUtente(String nomeUtente) throws SQLException {
        String query = """
        SELECT r.*, u.immagine AS img_utente
        FROM recensioni r
        JOIN utente u ON r.nomeutente = u.username
        WHERE r.nomeutente = ?
        """;

        return executeQueryWithUserImage(query, nomeUtente);
    }

    private ArrayList<RecensioneDTO> executeQueryWithUserImage(String query, String param) {
        ArrayList<RecensioneDTO> recensioni = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecensioneDTO r = new RecensioneDTO();
                    r.setId(rs.getString("id"));
                    r.setNomeUtente(rs.getString("nomeutente"));
                    r.setNomeLocale(rs.getString("nomelocale"));
                    r.setTesto(rs.getString("testo"));
                    r.setValutazione(rs.getFloat("valutazione"));

                    byte[] img = rs.getBytes("img_utente");
                    if (img != null) {
                        r.setImmagineUtenteBase64(
                                Base64.getEncoder().encodeToString(img)
                        );
                    }

                    recensioni.add(r);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return recensioni;
    }

}
