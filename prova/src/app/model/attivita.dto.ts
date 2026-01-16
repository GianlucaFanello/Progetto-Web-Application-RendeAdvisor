import {RecensioneDto} from './recensione.dto';


export interface AttivitaDto {

  nomeLocale:string;
  proprietario:string;
  telefono:string;
  email:string;
  descrizione:string;
  indirizzo:string;
  tipo:string;
  immagine?:string

  recensioni?: RecensioneDto[];
}
