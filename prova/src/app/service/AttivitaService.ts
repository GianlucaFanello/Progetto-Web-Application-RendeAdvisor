import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AttivitaDto} from '../model/attivita.dto';


@Injectable({
  providedIn: 'root'
})

export class AttivitaService {

  private BASE_URL = 'http://localhost:8080/api/attivita';

  constructor(private http:HttpClient) {}

  getTutte(): Observable<AttivitaDto[]> {
    return this.http.get<AttivitaDto[]>(this.BASE_URL);
  }

  getRistoranti(): Observable<AttivitaDto[]> {
    return this.http.get<AttivitaDto[]>(this.BASE_URL +"/ristoranti");
  }

  getHotel(): Observable<AttivitaDto[]> {
    return this.http.get<AttivitaDto[]>(this.BASE_URL +"/hotel");
  }

  getDettaglio(nomeLocale: string): Observable<AttivitaDto> {
    return this.http.get<AttivitaDto>(this.BASE_URL + "/dettaglio/" + nomeLocale);
  }

  salva(): Observable<AttivitaDto> {
    return this.http.get<AttivitaDto>(this.BASE_URL + "/salva");
  }

}
