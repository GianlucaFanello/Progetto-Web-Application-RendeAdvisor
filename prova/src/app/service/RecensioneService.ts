import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {RecensioneDto} from '../model/recensione.dto';

@Injectable({
  providedIn: 'root'
})

export class RecensioneService{

  private BASE_URL = 'http://localhost:8080/api/recensioni';

  constructor(private http:HttpClient) {}

  getByLocale(nomeLocale: string): Observable<RecensioneDto[]> {
    return this.http.get<RecensioneDto[]>(this.BASE_URL + "/locale/" + nomeLocale);
  }

  salva(): Observable<RecensioneDto> {
    return this.http.get<RecensioneDto>(this.BASE_URL + "/salva");
  }

}
