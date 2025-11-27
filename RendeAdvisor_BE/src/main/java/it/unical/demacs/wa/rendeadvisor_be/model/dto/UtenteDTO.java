package it.unical.demacs.wa.rendeadvisor_be.model.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor ;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UtenteDTO {

    private String username;    // Primary key
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String descrizione;
    private byte[] immagine;  // scegliere il tipo dell'immagine


}
