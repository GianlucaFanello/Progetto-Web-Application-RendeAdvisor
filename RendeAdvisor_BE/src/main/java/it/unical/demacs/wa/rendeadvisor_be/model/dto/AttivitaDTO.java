package it.unical.demacs.wa.rendeadvisor_be.model.dto;

import java.sql.SQLException;
import java.util.List;

public class AttivitaDTO {

    private String nomeLocale;
    private String proprietario;
    private String telefono;
    private String email;
    private byte[] immagine;
    private String descrizione;
    private String indirizzo;
    private String tipo;
    private IRecensione recensione;


    public AttivitaDTO() {
    }


    public AttivitaDTO(String nomeLocale, String proprietario, String telefono, String email,
                       byte[] immagine, String descrizione, String indirizzo, String tipo, IRecensione recensione) {
        this.nomeLocale = nomeLocale;
        this.proprietario = proprietario;
        this.telefono = telefono;
        this.email = email;
        this.immagine = immagine;
        this.descrizione = descrizione;
        this.indirizzo = indirizzo;
        this.tipo = tipo;
        this.recensione = recensione;
    }


    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public String getProprietario() {
        return proprietario;
    }

    public List<RecensioneDTO> getRecensioniDTO() throws SQLException {
        return recensione.getRecensioni();
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
