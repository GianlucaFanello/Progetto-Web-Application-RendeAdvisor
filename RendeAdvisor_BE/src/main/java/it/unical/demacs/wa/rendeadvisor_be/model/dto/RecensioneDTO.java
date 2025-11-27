package it.unical.demacs.wa.rendeadvisor_be.model.dto;

public class RecensioneDTO {

    private String id;
    private String nomeUtente;
    private String nomeLocale;
    private String testo;
    private float valutazione;


    public RecensioneDTO() {
    }


    public RecensioneDTO(String id, String nomeUtente, String nomeLocale, String testo, float valutazione) {
        this.id = id;
        this.nomeUtente = nomeUtente;
        this.nomeLocale = nomeLocale;
        this.testo = testo;
        this.valutazione = valutazione;
    }

    // 3. Getter e Setter standard
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public float getValutazione() {
        return valutazione;
    }

    public void setValutazione(float valutazione) {
        this.valutazione = valutazione;
    }
}
