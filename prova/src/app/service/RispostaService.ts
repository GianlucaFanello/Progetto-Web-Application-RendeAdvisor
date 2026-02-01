import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RispostaDto } from '../model/risposta.dto';
import { ApiResponseDto } from '../model/apiResponse.dto';

@Injectable({ providedIn: 'root' })
export class RispostaService {
  private BASE_URL = 'http://localhost:8080/api/risposta';

  constructor(private http: HttpClient) {}


  salva(risposta: RispostaDto): Observable<ApiResponseDto<void>> {
    return this.http.post<ApiResponseDto<void>>(`${this.BASE_URL}/salva`, risposta);
  }


  getByRecensione(idRecensione: number): Observable<ApiResponseDto<RispostaDto[]>> {
    return this.http.get<ApiResponseDto<RispostaDto[]>>(`${this.BASE_URL}/risposte/${idRecensione}`);
  }
}
