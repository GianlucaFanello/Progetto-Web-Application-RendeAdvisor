import {Injectable} from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AttivitaDto} from '../model/attivita.dto';
import {ApiResponseDto} from '../model/apiResponse.dto';


@Injectable({
  providedIn: 'root'
})

export class AttivitaService {

  private BASE_URL = 'http://localhost:8080/api/attivita';

  constructor(private http: HttpClient) {}

  getTutte(): Observable<ApiResponseDto<AttivitaDto[]>> {
    return this.http.get<ApiResponseDto<AttivitaDto[]>>(this.BASE_URL);
  }

  getRistoranti(): Observable<ApiResponseDto<AttivitaDto[]>> {
    return this.http.get<ApiResponseDto<AttivitaDto[]>>(this.BASE_URL + "/ristoranti");
  }

  getHotel(): Observable<ApiResponseDto<AttivitaDto[]>> {
    return this.http.get<ApiResponseDto<AttivitaDto[]>>(this.BASE_URL + "/hotel");
  }

  getDettaglio(nomeLocale: string): Observable<ApiResponseDto<AttivitaDto>> {
    return this.http.get<ApiResponseDto<AttivitaDto>>(this.BASE_URL + "/dettaglio/" + nomeLocale);
  }

  salva(attivita: AttivitaDto): Observable<ApiResponseDto<void>> {
    return this.http.post<ApiResponseDto<void>>(this.BASE_URL + "/salva", attivita);
  }

  search(query: string): Observable<ApiResponseDto<AttivitaDto[]>> {
    const params = new HttpParams().set('query', query);
    return this.http.get<ApiResponseDto<AttivitaDto[]>>(this.BASE_URL + "/search", {params});
  }
}
