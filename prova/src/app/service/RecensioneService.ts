import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecensioneDto } from '../model/recensione.dto';
import { ApiResponseDto } from '../model/apiResponse.dto';

@Injectable({
  providedIn: 'root'
})

export class RecensioneService {

  private BASE_URL = 'http://localhost:8080/api/recensioni';

  constructor(private http: HttpClient) {}

  getByLocale(nomeLocale: string): Observable<ApiResponseDto<RecensioneDto[]>> {
    return this.http.get<ApiResponseDto<RecensioneDto[]>>(
      this.BASE_URL + "/locale/" + nomeLocale
    );
  }

  getByUtente(username: string): Observable<ApiResponseDto<RecensioneDto[]>> {
    return this.http.get<ApiResponseDto<RecensioneDto[]>>(
      this.BASE_URL + "/utente/" + username
    );
  }

  salva(recensione: RecensioneDto): Observable<ApiResponseDto<number>> {
    return this.http.post<ApiResponseDto<number>>(
      this.BASE_URL + "/salva",
      recensione
    );
  }

  getCountUtente(username:string): Observable<ApiResponseDto<number>> {
    const params = {username: username}
    return this.http.get<ApiResponseDto<number>>(
      this.BASE_URL + "/n_rec",
      { params }
    );
  }

  getRatingStruttura(nomeStruttura: string): Observable<ApiResponseDto<number>> {
    const params = {nomeStruttura: nomeStruttura};
    return this.http.get<ApiResponseDto<number>>(
      this.BASE_URL + "/rating",
      { params }
    );
  }
}
