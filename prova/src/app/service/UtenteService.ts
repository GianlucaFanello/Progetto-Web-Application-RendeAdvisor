import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UtenteDto } from '../model/utente.dto';
import { ApiResponseDto } from '../model/apiResponse.dto';
import { LoginDto } from '../model/login.dto';

@Injectable({
  providedIn: 'root'
})
export class UtenteService {

  private BASE_URL = 'http://localhost:8080/api/utenti';

  constructor(private http: HttpClient) {}

  registra(utente: UtenteDto): Observable<ApiResponseDto<void>> {
    return this.http.post<ApiResponseDto<void>>(
      this.BASE_URL + "/registrazione",
      utente
    );
  }

  login(credenziali: LoginDto): Observable<ApiResponseDto<UtenteDto>> {
    return this.http.post<ApiResponseDto<UtenteDto>>(
      this.BASE_URL + "/login",
      credenziali
    );
  }

  isLogged(): Observable<ApiResponseDto<boolean>> {
    return this.http.get<ApiResponseDto<boolean>>(
      this.BASE_URL + "/isLogged"
    );
  }

  me(): Observable<ApiResponseDto<UtenteDto>> {
    return this.http.get<ApiResponseDto<UtenteDto>>(
      this.BASE_URL + "/me"
    );
  }

  logout(): Observable<ApiResponseDto<void>> {
    return this.http.post<ApiResponseDto<void>>(
      this.BASE_URL + "/logout",
      {}
    );
  }

  modificaProfilo(formData: FormData) {
    return this.http.post<ApiResponseDto<UtenteDto>>( this.BASE_URL + "/modifica/salva" , formData );
  }
}




