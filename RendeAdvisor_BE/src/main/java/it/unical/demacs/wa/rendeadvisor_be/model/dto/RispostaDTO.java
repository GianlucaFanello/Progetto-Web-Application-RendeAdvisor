package it.unical.demacs.wa.rendeadvisor_be.model.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class RispostaDTO {

    private String idRisposta;
    private String idRecensione;
    private String utente;
    private String risposta;

    public String getIdRisposta() {
        return idRisposta;
    }

    public String getIdRecensione() {
        return idRecensione;
    }

    public String getUtente() {
        return utente;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setIdRisposta(String idRisposta) {
        this.idRisposta = idRisposta;
    }

    public void setIdRecensione(String idRecensione) {
        this.idRecensione = idRecensione;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }
}
