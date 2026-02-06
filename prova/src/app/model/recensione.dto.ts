export interface RecensioneDto {
  id: number;
  nomeUtente: string;
  nomeLocale: string;
  testo: string;
  valutazione: number;

  immagineUtenteBase64?:String;
}
