import { HttpClient, HttpParams } from '@angular/common/http';
import {Injectable, numberAttribute} from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponseDto } from '../model/apiResponse.dto';
import { NelCuoreDto } from '../model/nelCuore.dto';
import {AttivitaDto} from '../model/attivita.dto';

@Injectable({
  providedIn: 'root'
})
export class NelCuoreService {

  private BASE_URL = 'http://localhost:8080/api/nelcuore';

  constructor(private http: HttpClient) {}

  aggiungi(dto: NelCuoreDto): Observable<ApiResponseDto<void>> {
    return this.http.post<ApiResponseDto<void>>(this.BASE_URL + "/aggiungi", dto);
  }

  rimuovi(dto: NelCuoreDto): Observable<ApiResponseDto<void>> {
    return this.http.delete<ApiResponseDto<void>>(this.BASE_URL + "/rimuovi", {
      body: dto
    });
  }

  preferito(nomeUtente: string, nomeStruttura: string): Observable<ApiResponseDto<boolean>> {
    const params = new HttpParams()
      .set('nomeUtente', nomeUtente)
      .set('nomeStruttura', nomeStruttura);

    return this.http.get<ApiResponseDto<boolean>>(this.BASE_URL + "/preferito", { params });
  }

  lista(nomeUtente: string): Observable<ApiResponseDto<AttivitaDto[]>> {
    const params = new HttpParams().set('nomeUtente', nomeUtente);

    return this.http.get<ApiResponseDto<AttivitaDto[]>>(this.BASE_URL + "/lista", { params });
  }

  countPreferiti(nomeStruttura: string): Observable<ApiResponseDto<number>> {
    return this.http.get<ApiResponseDto<number>>(this.BASE_URL + "/count-preferiti", {
      params: {nomeStruttura: nomeStruttura}
    })
  }
}
