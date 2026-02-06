package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import it.unical.demacs.wa.rendeadvisor_be.dao.INelCuoreDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Repository
public class NelCuoreDAO implements INelCuoreDAO {

    private Connection connection;

    public NelCuoreDAO(Connection connection) {
        this.connection = connection;
    }

    // Esegue una INSERT per salvare la relazione Utente-Struttura nel DB
    @Override
    public boolean addPreferito(String nomeUtente, String nomeStruttura) throws SQLException {
        String sql = "INSERT INTO nelcuore (nomeutente, nomestruttura) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeUtente);
            ps.setString(2, nomeStruttura);
            return ps.executeUpdate() == 1;
        }
    }

    // Esegue una DELETE per rimuovere la relazione specifica dal DB
    @Override
    public boolean removePreferito(String nomeUtente, String nomeStruttura) throws SQLException {
        String sql = "DELETE FROM nelcuore WHERE nomeutente = ? AND nomestruttura = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeUtente);
            ps.setString(2, nomeStruttura);
            return  ps.executeUpdate() == 1;
        }
    }

    // Esegue una SELECT che ritorna 'true' se trova almeno una riga corrispondente
    @Override
    public boolean isPreferito(String nomeUtente, String nomeStruttura) {
        String sql = "SELECT 1 FROM nelcuore WHERE nomeutente = ? AND nomestruttura = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeUtente);
            ps.setString(2, nomeStruttura);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Se c'è un risultato, vuol dire che è preferito
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Esegue una SELECT per ottenere tutti i nomi delle strutture salvate da quell'utente
    @Override
    public ArrayList<AttivitaDTO> findAllByUser(String nomeUtente) {
        ArrayList<AttivitaDTO> preferiti = new ArrayList<>();
        String sql = "SELECT a.nomelocale, a.immagine FROM nelcuore n JOIN attivita a ON n.nomestruttura = a.nomelocale WHERE n.nomeutente = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomeUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AttivitaDTO attivita =  new AttivitaDTO();
                    attivita.setNomeLocale(rs.getString("nomelocale"));
                    attivita.setImmagine(rs.getBytes("immagine"));
                    preferiti.add(attivita);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return preferiti;
    }

    @Override
    public Integer countPreferitiLocale(String nomeStruttura) {
        String query =  "SELECT COUNT(*) FROM nelcuore WHERE nomestruttura = ?";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nomeStruttura);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
