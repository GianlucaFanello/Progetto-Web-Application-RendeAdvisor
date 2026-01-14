import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {NelCuoreDto} from '../model/nelCuore.dto';


@Injectable({
  providedIn: 'root'
})


export class NelCuoreService{

  private BASE_URL = 'http://localhost:8080/api/nelcuore';

  constructor(private http:HttpClient) {}

  salva(): Observable<NelCuoreDto> {
    return this.http.get<NelCuoreDto>(this.BASE_URL + "/salva");
  }

  rimuovi():Observable<NelCuoreDto> {
    return  this.http.get<NelCuoreDto>(this.BASE_URL + "/rimuovi");
  }

  preferito():Observable<boolean>{
    return this.http.get<boolean>(this.BASE_URL + "/preferito");
  }

  lista():Observable<string[]> {
    return this.http.get<string[]>(this.BASE_URL + "/lista");
  }

}
