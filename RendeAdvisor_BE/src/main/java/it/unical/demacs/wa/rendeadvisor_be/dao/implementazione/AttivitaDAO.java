package it.unical.demacs.wa.rendeadvisor_be.dao.implementazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unical.demacs.wa.rendeadvisor_be.dao.IAttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.IRecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.IRecensione;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.RecensioneProxy;

public class AttivitaDAO implements IAttivitaDAO {
    Connection connection;
    private IRecensioneDAO recensioneDAO;

    public AttivitaDAO(Connection connection,  IRecensioneDAO recensioneDAO) {
        this.connection = connection;
        this.recensioneDAO = recensioneDAO;
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

            IRecensione recensioniProxy = new RecensioneProxy(recensioneDAO, nome);
            AttivitaDTO attivitaDTO = new AttivitaDTO(nome, proprietario, telefono, email, immagine, descrizione, indirizzo, tipo, recensioniProxy);
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
            String nome = rs.getString("nomelocale");
            String proprietario = rs.getString("proprietario");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            byte[] immagine = rs.getBytes("immagine");
            String descrizione = rs.getString("descrizione");
            String indirizzo = rs.getString("indirizzo");
            String tipo = rs.getString("tipo");

            IRecensione recensioniProxy = new RecensioneProxy(recensioneDAO, nome);
            AttivitaDTO attivitaDTO = new AttivitaDTO(nome, proprietario, telefono, email, immagine, descrizione, indirizzo, tipo, recensioniProxy);
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
            String nome = rs.getString("nomelocale");
            String proprietario = rs.getString("proprietario");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");
            byte[] immagine = rs.getBytes("immagine");
            String descrizione = rs.getString("descrizione");
            String indirizzo = rs.getString("indirizzo");
            IRecensione recensioniProxy = new RecensioneProxy(recensioneDAO, nome);
            AttivitaDTO attivitaDTO = new AttivitaDTO(nome, proprietario, telefono, email, immagine, descrizione, indirizzo, tipo, recensioniProxy);
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
}
