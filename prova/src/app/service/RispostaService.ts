import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {RispostaDto} from '../model/risposta.dto';

@Injectable({
  providedIn: 'root'
})

export class RispostaService {

  private BASE_URL = 'http://localhost:8080/api/risposta';

  constructor(private http:HttpClient) {}

  salva(): Observable<RispostaDto> {
    return this.http.get<RispostaDto>(this.BASE_URL + "/salva");
  }

  getByRecensione(idRecensione: string): Observable<RispostaDto[]> {
    return this.http.get<RispostaDto[]>(this.BASE_URL +"/risposte/" + idRecensione)
  }

}
