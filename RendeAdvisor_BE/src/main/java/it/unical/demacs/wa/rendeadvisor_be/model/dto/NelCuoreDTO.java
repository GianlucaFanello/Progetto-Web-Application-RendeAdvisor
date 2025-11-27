package it.unical.demacs.wa.rendeadvisor_be.model.dto;

public class NelCuoreDTO {

    private String nomeUtente;
    private String nomeStruttura;

    public NelCuoreDTO() {}

    public NelCuoreDTO(String nomeUtente, String nomeStruttura) {
        this.nomeUtente = nomeUtente;
        this.nomeStruttura = nomeStruttura;
    }

    public String getNomeUtente() { return nomeUtente; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }

    public String getNomeStruttura() { return nomeStruttura; }
    public void setNomeStruttura(String nomeStruttura) { this.nomeStruttura = nomeStruttura; }

}
