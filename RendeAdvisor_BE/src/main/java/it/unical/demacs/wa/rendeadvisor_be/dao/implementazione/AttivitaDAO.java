package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.demacs.wa.rendeadvisor_be.dao.IAttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.dbManager.DBManager;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaProxy;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneDTO;
import org.springframework.stereotype.Repository;

@Repository
public class AttivitaDAO implements IAttivitaDAO {
    Connection connection;

    public AttivitaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insertAttivita(AttivitaDTO attivita) throws SQLException {
        String query = "INSERT INTO attivita(nomelocale, proprietario, telefono, email,  immagine, descrizione, indirizzo, tipo)" +
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

        return ps.executeUpdate() == 1;
    }

    @Override
    public AttivitaDTO findByPrimaryKey(String nomeLocale) throws SQLException {

        String query = "SELECT * FROM attivita WHERE nomelocale = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeLocale);

        ResultSet rs = ps.executeQuery();
        AttivitaDTO attivitaDTO = null;
        if (rs.next()) {

            String nome = rs.getString("nomelocale");
            String proprietario = rs.getString("proprietario");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            byte[] immagine = rs.getBytes("immagine");
            String descrizione = rs.getString("descrizione");
            String indirizzo = rs.getString("indirizzo");
            String tipo = rs.getString("tipo");

            attivitaDTO = new AttivitaDTO(nome, proprietario, telefono, email, immagine, descrizione, indirizzo, tipo);
            RecensioneDAO recensioneDAO = new RecensioneDAO(DBManager.getInstance().getConnection());
            List<RecensioneDTO> recensioni = recensioneDAO.findByLocale(nome);
            attivitaDTO.setRecensioni(recensioni);
        }

        return attivitaDTO;
    }

    @Override
    public ArrayList<AttivitaDTO> findAll() throws SQLException {
        String query = "SELECT * FROM attivita";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        ArrayList<AttivitaDTO> listaAttivita = new ArrayList<>();
        while (rs.next()) {
            AttivitaProxy attivitaDTO = new AttivitaProxy();
            attivitaDTO.setNomeLocale(rs.getString("nomelocale"));
            attivitaDTO.setProprietario(rs.getString("proprietario"));
            attivitaDTO.setTelefono(rs.getString("telefono"));
            attivitaDTO.setEmail(rs.getString("email"));
            attivitaDTO.setImmagine(rs.getBytes("immagine"));
            attivitaDTO.setDescrizione(rs.getString("descrizione"));
            attivitaDTO.setIndirizzo(rs.getString("indirizzo"));
            attivitaDTO.setTipo(rs.getString("tipo"));

            listaAttivita.add(attivitaDTO);
        }

        return listaAttivita;
    }

    @Override
    public ArrayList<AttivitaDTO> findByTipo(String tipo) throws  SQLException{
        String  query = "SELECT * FROM attivita WHERE tipo = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, tipo);

        ResultSet rs = ps.executeQuery();
        ArrayList<AttivitaDTO> listaAttivitaByTipo = new ArrayList<>();
        while (rs.next()) {
            AttivitaProxy attivitaDTO = new AttivitaProxy();
            attivitaDTO.setNomeLocale(rs.getString("nomelocale"));
            attivitaDTO.setProprietario(rs.getString("proprietario"));
            attivitaDTO.setTelefono(rs.getString("telefono"));
            attivitaDTO.setEmail(rs.getString("email"));
            attivitaDTO.setImmagine(rs.getBytes("immagine"));
            attivitaDTO.setDescrizione(rs.getString("descrizione"));
            attivitaDTO.setIndirizzo(rs.getString("indirizzo"));
            attivitaDTO.setTipo(rs.getString("tipo"));

            listaAttivitaByTipo.add(attivitaDTO);
        }

        return listaAttivitaByTipo;
    }

    @Override
    public boolean updateAttivita(AttivitaDTO attivita) throws SQLException {
        String query = "UPDATE attivita SET telefono=?,email=?,immagine=?,descrizione=?,indirizzo=?,tipo=? WHERE nomelocale = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, attivita.getTelefono());
        ps.setString(2, attivita.getEmail());
        ps.setBytes(3, attivita.getImmagine());
        ps.setString(4, attivita.getDescrizione());
        ps.setString(5, attivita.getIndirizzo());
        ps.setString(6, attivita.getTipo());
        ps.setString(7, attivita.getNomeLocale());

        return ps.executeUpdate() == 1;
    }


    @Override
    public boolean delete(String nomeLocale) throws  SQLException{
        String query = "DELETE FROM attivita WHERE nomelocale = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeLocale);
        return ps.executeUpdate() == 1;
    }


    @Override
    public List<AttivitaDTO> search(String query) throws SQLException {

        String sql = """ 
        SELECT * FROM attivita WHERE nome = ? """;

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, query);

        ResultSet rs = ps.executeQuery();

        List<AttivitaDTO> risultati = new ArrayList<>();

        while (rs.next()) {
            risultati.add(mapResultSetToDTO(rs));
        }

        return risultati;
    }

    private AttivitaDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        AttivitaDTO dto = new AttivitaDTO();

        dto.setNomeLocale(rs.getString("nomelocale"));
        dto.setProprietario(rs.getString("proprietario"));
        dto.setTelefono(rs.getString("telefono"));
        dto.setEmail(rs.getString("email"));
        dto.setImmagine(rs.getString("immagine").getBytes());

        dto.setDescrizione(rs.getString("descrizione"));
        dto.setIndirizzo(rs.getString("indirizzo"));
        dto.setTipo(rs.getString("tipo"));

        return dto;
    }

}
