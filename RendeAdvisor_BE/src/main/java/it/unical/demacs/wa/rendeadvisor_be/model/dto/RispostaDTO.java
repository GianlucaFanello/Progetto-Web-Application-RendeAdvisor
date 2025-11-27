package it.unical.demacs.wa.rendeadvisor_be.model.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class RispostaDTO {

    private String idRisposta;
    private String idRecensione;
    private String utente;

}
