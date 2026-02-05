package it.unical.demacs.wa.rendeadvisor_be.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.SQLException;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

public class AttivitaDTO {

    private String nomeLocale;
    private String proprietario;
    private String telefono;
    private String email;
    private byte[] immagine;
    private String immagineBase64;
    private String descrizione;
    private String indirizzo;
    private String tipo;
    private Double latitudine;
    private Double longitudine;

    private List<RecensioneDTO> recensioni;

    public AttivitaDTO(String nomeLocale, String proprietario, String telefono, String email,
                       byte[] immagine, String descrizione, String indirizzo, String tipo, double latitudine,double longitudine) {
        this.nomeLocale = nomeLocale;
        this.proprietario = proprietario;
        this.telefono = telefono;
        this.email = email;
        this.immagine = immagine;
        this.descrizione = descrizione;
        this.indirizzo = indirizzo;
        this.tipo = tipo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

}
