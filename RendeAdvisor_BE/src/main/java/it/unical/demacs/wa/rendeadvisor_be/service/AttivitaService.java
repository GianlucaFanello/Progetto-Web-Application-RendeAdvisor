package it.unical.demacs.wa.rendeadvisor_be.service;

import it.unical.demacs.wa.rendeadvisor_be.dao.IAttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.AttivitaDAO;
import it.unical.demacs.wa.rendeadvisor_be.dao.implementazione.RecensioneDAO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaDTO;
import it.unical.demacs.wa.rendeadvisor_be.model.dto.AttivitaProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
@Service
public class AttivitaService {

    private final IAttivitaDAO attivitaDAO;
    private final GeocodingService geocodingService;
    private final RecensioneDAO recensioneDAO;

    // Unico costruttore: Spring inietterà entrambi automaticamente
    // Nel file AttivitaService.java
    @Autowired
    public AttivitaService(IAttivitaDAO attivitaDAO, GeocodingService geocodingService,  RecensioneDAO recensioneDAO) {
        this.attivitaDAO = attivitaDAO;
        this.geocodingService = geocodingService;
        this.recensioneDAO = recensioneDAO;
    }

    public boolean salvaAttivita(AttivitaDTO attivita) throws SQLException {
        double[] coords = geocodingService.getCoordinates(attivita.getIndirizzo());
        if (coords != null) {
            attivita.setLatitudine(coords[0]);
            attivita.setLongitudine(coords[1]);
        }

        return attivitaDAO.insertAttivita(attivita);
    }


    public List<AttivitaDTO> getTutte() throws SQLException {
        return attivitaDAO.findAll();
    }


    public List<AttivitaDTO> getRistoranti() throws SQLException {
        List<AttivitaDTO> attivita = attivitaDAO.findByTipo("ristorante");

        for(AttivitaDTO a: attivita){
            if(a.getImmagine() != null){
                a.setImmagineBase64(Base64.getEncoder().encodeToString(a.getImmagine()));
                a.setImmagine(null);
            }
        }

        return attivita;
    }


    public List<AttivitaDTO> getHotel() throws SQLException {
        List<AttivitaDTO> attivita = attivitaDAO.findByTipo("hotel");

        for(AttivitaDTO a: attivita){
            if(a.getImmagine() != null){
                a.setImmagineBase64(Base64.getEncoder().encodeToString(a.getImmagine()));
                a.setImmagine(null);
            }
        }

        return attivita;
    }


    public AttivitaDTO getDettaglio(String nomeLocale) throws SQLException {

        AttivitaDTO base = attivitaDAO.findByPrimaryKey(nomeLocale);

        AttivitaProxy proxy = new AttivitaProxy(recensioneDAO);

        proxy.setNomeLocale(base.getNomeLocale());
        proxy.setProprietario(base.getProprietario());
        proxy.setTelefono(base.getTelefono());
        proxy.setEmail(base.getEmail());
        proxy.setDescrizione(base.getDescrizione());
        proxy.setIndirizzo(base.getIndirizzo());
        proxy.setTipo(base.getTipo());
        proxy.setLatitudine(base.getLatitudine());
        proxy.setLongitudine(base.getLongitudine());

        proxy.getRecensioni(); // lazy loading

        return proxy;
    }



    public List<AttivitaDTO> search(String query) throws SQLException {
        List<AttivitaDTO> attivita = attivitaDAO.search(query);

        for(AttivitaDTO a: attivita){
            if(a.getImmagine() != null){
                a.setImmagineBase64(Base64.getEncoder().encodeToString(a.getImmagine()));
                a.setImmagine(null);
            }
        }
        return attivita;
    }

    public AttivitaDTO findByNome(String nome) throws SQLException {
        AttivitaDTO attivita = attivitaDAO.findByNome(nome);

        if(attivita.getImmagine() != null){
            attivita.setImmagineBase64(Base64.getEncoder().encodeToString(attivita.getImmagine()));
            attivita.setImmagine(null);
        }

        return attivita;
    }

    public List<AttivitaDTO> listaAttivitaByProprietario(String username) throws SQLException {

        List<AttivitaDTO> attivita = attivitaDAO.listaAttivitaByProprietario(username);

        for(AttivitaDTO a: attivita) {
            if (a.getImmagine() != null) {
                a.setImmagineBase64(Base64.getEncoder().encodeToString(a.getImmagine()));
                a.setImmagine(null);
            }
        }

        return attivita ;
    }

    public AttivitaDTO modificaProfilo(AttivitaDTO attivita, String vecchioNomeLocale, boolean elim) throws SQLException {

        AttivitaDTO vecchio = attivitaDAO.findByNome(vecchioNomeLocale);

        if (elim) {
            attivita.setImmagine(null);
        } else if (attivita.getImmagine() == null && vecchio.getImmagine() != null) {
            attivita.setImmagine(vecchio.getImmagine());
        }

        String nuovoInd = attivita.getIndirizzo();
        String vecchioInd = vecchio.getIndirizzo();

        boolean indirizzoCambiato = nuovoInd != null && !nuovoInd.isBlank() && !nuovoInd.equals(vecchioInd);

        if (indirizzoCambiato) {

            double[] coords = geocodingService.getCoordinates(nuovoInd);

            if (coords != null) {
                attivita.setLatitudine(coords[0]);
                attivita.setLongitudine(coords[1]);
            } else {
                attivita.setLatitudine(null);
                attivita.setLongitudine(null);
            }

        } else {
            // indirizzo non cambiato → vecchie coordinate
            attivita.setLatitudine(vecchio.getLatitudine());
            attivita.setLongitudine(vecchio.getLongitudine());
        }

        if (attivitaDAO.updateAttivita(attivita, vecchioNomeLocale)) {
            return attivita;
        }

        return null;
    }


    public List<AttivitaDTO> getVicini() throws SQLException {
        List<AttivitaDTO> locali = attivitaDAO.findAll()
                .stream()
                .filter(a -> a.getLatitudine() != null && a.getLongitudine() != null && a.getLatitudine() != 0.0 && a.getLongitudine() != 0.0)
                .toList();

        attivitaDAO.findAll().forEach(a -> { System.out.println(a.getNomeLocale() + " lat=" + a.getLatitudine() + " lng=" + a.getLongitudine()); });
        List<AttivitaDTO> ordinati = geocodingService.ordinaPerDistanzaDalCentro(locali);



        for(AttivitaDTO a: ordinati){

            if(a.getImmagine() != null){
                a.setImmagineBase64(Base64.getEncoder().encodeToString(a.getImmagine()));
                a.setImmagine(null);
            }
        }

        return ordinati.subList(0, Math.min(5, ordinati.size()));

    }

}
