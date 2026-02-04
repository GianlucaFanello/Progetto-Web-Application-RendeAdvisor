package it.unical.demacs.wa.rendeadvisor_be.model.dto;

public class UtenteDTO {

    private String username;    // Primary key
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String descrizione;
    private byte[] immagine;  // scegliere il tipo dell'immagine
    private String immagineBase64;

    public UtenteDTO(String username, String nome, String cognome, String email, String password , String descrizione, byte[] immagine) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.descrizione = descrizione;
        this.immagine = immagine;
    }

    public UtenteDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    public String getImmagineBase64() {
        return immagineBase64;
    }

    public void setImmagineBase64(String immagineBase64) {
        this.immagineBase64 = immagineBase64;
    }
}
