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
        String query = "INSERT INTO attivita(nomelocale, proprietario, telefono, email,  immagine, descrizione, indirizzo, tipo, latitudine, longitudine)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, attivita.getNomeLocale());
        ps.setString(2, attivita.getProprietario());
        ps.setString(3, attivita.getTelefono());
        ps.setString(4, attivita.getEmail());
        ps.setBytes(5, attivita.getImmagine());
        ps.setString(6, attivita.getDescrizione());
        ps.setString(7, attivita.getIndirizzo());
        ps.setString(8, attivita.getTipo());
        if (attivita.getLatitudine() != null)
            ps.setDouble(9, attivita.getLatitudine());
        else
            ps.setNull(9, java.sql.Types.DOUBLE);

        if (attivita.getLongitudine() != null)
            ps.setDouble(10, attivita.getLongitudine());
        else
            ps.setNull(10, java.sql.Types.DOUBLE);


        return ps.executeUpdate() == 1;
    }

    @Override
    public AttivitaDTO findByNome(String nome) throws SQLException {
        String sql = "SELECT * FROM attivita WHERE nomelocale = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, nome);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return mapResultSetToDTO(rs);
        }

        return null;
    }


    @Override
    public AttivitaDTO findByPrimaryKey(String nomeLocale) throws SQLException {

        String query = "SELECT * FROM attivita WHERE nomelocale = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nomeLocale);

        ResultSet rs = ps.executeQuery();
        AttivitaDTO attivita = null;
        if (rs.next()) {
            attivita = new AttivitaDTO();

            String nome = rs.getString("nomelocale");
            String proprietario = rs.getString("proprietario");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            byte[] immagine = rs.getBytes("immagine");
            String descrizione = rs.getString("descrizione");
            String indirizzo = rs.getString("indirizzo");
            String tipo = rs.getString("tipo");
            double latitudine = rs.getDouble("latitudine");
            double longitudine = rs.getDouble("longitudine");

            attivita.setNomeLocale(nomeLocale);
            attivita.setProprietario(proprietario);
            attivita.setTelefono(telefono);
            attivita.setEmail(email);
            attivita.setImmagine(immagine);
            attivita.setDescrizione(descrizione);
            attivita.setIndirizzo(indirizzo);
            attivita.setTipo(tipo);
            attivita.setLatitudine(latitudine);
            attivita.setLongitudine(longitudine);

        }

        return attivita;
    }

    @Override
    public ArrayList<AttivitaDTO> findAll() throws SQLException {
        String query = "SELECT * FROM attivita";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        ArrayList<AttivitaDTO> listaAttivita = new ArrayList<>();
        while (rs.next()) {
            AttivitaDTO attivitaDTO = new AttivitaDTO();
            attivitaDTO.setNomeLocale(rs.getString("nomelocale"));
            attivitaDTO.setProprietario(rs.getString("proprietario"));
            attivitaDTO.setTelefono(rs.getString("telefono"));
            attivitaDTO.setEmail(rs.getString("email"));
            attivitaDTO.setImmagine(rs.getBytes("immagine"));
            attivitaDTO.setDescrizione(rs.getString("descrizione"));
            attivitaDTO.setIndirizzo(rs.getString("indirizzo"));
            attivitaDTO.setTipo(rs.getString("tipo"));
            attivitaDTO.setLatitudine(rs.getDouble("latitudine"));
            attivitaDTO.setLongitudine(rs.getDouble("longitudine"));

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
            AttivitaDTO attivitaDTO = new AttivitaDTO();
            attivitaDTO.setNomeLocale(rs.getString("nomelocale"));
            attivitaDTO.setProprietario(rs.getString("proprietario"));
            attivitaDTO.setTelefono(rs.getString("telefono"));
            attivitaDTO.setEmail(rs.getString("email"));
            attivitaDTO.setImmagine(rs.getBytes("immagine"));
            attivitaDTO.setDescrizione(rs.getString("descrizione"));
            attivitaDTO.setIndirizzo(rs.getString("indirizzo"));
            attivitaDTO.setTipo(rs.getString("tipo"));
            attivitaDTO.setLatitudine(rs.getDouble("latitudine"));
            attivitaDTO.setLongitudine(rs.getDouble("longitudine"));

            listaAttivitaByTipo.add(attivitaDTO);
        }

        return listaAttivitaByTipo;
    }

    @Override
    public boolean updateAttivita(AttivitaDTO attivita, String vecchioNome) throws SQLException {
        String query = "UPDATE attivita SET nomelocale=?, telefono=?,email=?,immagine=?,descrizione=?,indirizzo=?, latitudine=?, longitudine=? WHERE nomelocale = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, attivita.getNomeLocale());
        ps.setString(2, attivita.getTelefono());
        ps.setString(3, attivita.getEmail());
        ps.setBytes(4, attivita.getImmagine());
        ps.setString(5, attivita.getDescrizione());
        ps.setString(6, attivita.getIndirizzo());
        ps.setDouble(7, attivita.getLatitudine());
        ps.setDouble(8, attivita.getLongitudine());
        ps.setString(9, vecchioNome);


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

        String sql = "SELECT * FROM attivita WHERE LOWER(nomelocale) LIKE ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + query.toLowerCase() + "%");

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
        dto.setImmagine(rs.getBytes("immagine"));
        dto.setImmagine(rs.getBytes("immagine"));
        dto.setDescrizione(rs.getString("descrizione"));
        dto.setIndirizzo(rs.getString("indirizzo"));
        dto.setTipo(rs.getString("tipo"));
        dto.setLatitudine(rs.getDouble("latitudine"));
        dto.setLongitudine(rs.getDouble("longitudine"));

        return dto;
    }

    public List<AttivitaDTO> listaAttivitaByProprietario(String username) throws SQLException {
        String query = "SELECT nomelocale,indirizzo,tipo,immagine FROM attivita WHERE proprietario=?";

        try(PreparedStatement ps = connection.prepareStatement(query);){
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            List<AttivitaDTO> attivita = new ArrayList<>();
            while(rs.next()){
                AttivitaDTO dto = new AttivitaDTO();
                dto.setNomeLocale(rs.getString("nomelocale"));
                dto.setIndirizzo(rs.getString("indirizzo"));
                dto.setImmagine(rs.getBytes("immagine"));
                dto.setTipo(rs.getString("tipo"));

                attivita.add(dto);
            }

            return attivita;
        }
    }

}
