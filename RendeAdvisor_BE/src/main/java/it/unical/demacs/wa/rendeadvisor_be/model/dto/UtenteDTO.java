package it.unical.demacs.wa.rendeadvisor_be.model.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor ;

@Getter
@Setter
@NoArgsConstructor
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
}
