import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {UtenteDto} from '../model/utente.dto';


@Injectable({
  providedIn: 'root'
})

export class UtenteService {

  private BASE_URL = 'http://localhost:8080/api/utenti';

  constructor(private http:HttpClient) {}

  registra(): Observable<UtenteDto> {
    return this.http.get<UtenteDto>(this.BASE_URL + "/registrazione");
  }

  login(): Observable<UtenteDto>{
    return this.http.get<UtenteDto>(this.BASE_URL + "/login");
  }

}




