import {RecensioneDto} from './recensione.dto';


export interface AttivitaDto {
  nomeLocale:string;
  proprietario:string;
  telefono:string;
  email:string;
  descrizione:string;
  indirizzo:string;
  tipo:string;
  immagineBase64?:string;
  latitudine?: number;
  longitudine?: number;

  recensioni?: RecensioneDto[];
}
