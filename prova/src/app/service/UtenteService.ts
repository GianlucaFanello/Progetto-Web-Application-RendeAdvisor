import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {UtenteDto} from '../model/utente.dto';
import {ApiResponseDto} from '../model/apiResponse.dto';


@Injectable({
  providedIn: 'root'
})

export class UtenteService {

  private BASE_URL = 'http://localhost:8080/api/utenti';

  constructor(private http:HttpClient) {}

  registra(utente: UtenteDto): Observable<ApiResponseDto<void>> {
    return this.http.post<ApiResponseDto<void>>(this.BASE_URL + "/registrazione",utente);
  }

  login(): Observable<UtenteDto>{
    return this.http.get<UtenteDto>(this.BASE_URL + "/login");
  }

}




