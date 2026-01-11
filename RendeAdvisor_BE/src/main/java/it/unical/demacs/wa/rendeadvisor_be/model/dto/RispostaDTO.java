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

}
