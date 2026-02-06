import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {AttivitaService} from '../service/AttivitaService';
import {AttivitaDto} from '../model/attivita.dto';
import {ApiResponseDto} from '../model/apiResponse.dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ristorante',
  imports: [FormsModule, CommonModule],
  templateUrl: './ristorante.html',
  styleUrl: './ristorante.css',
  standalone: true
})
export class Ristorante implements OnInit {
  searchText: string = '';

  listaRistoranti: AttivitaDto[] = [];

  constructor(
    private router: Router,
    private attivitaService: AttivitaService
  ) {}

  ngOnInit() {
    this.caricaR();
  }

  caricaR() {

    this.attivitaService.getRistoranti().subscribe({
      next: (response: ApiResponseDto<AttivitaDto[]>) => {
        if (response.success) {
          this.listaRistoranti = response.data;
        }
      },
      error: (err) => console.error("Errore nel recupero dati", err)
    });
  }
  search() {
    if (this.searchText.trim()) {
      this.router.navigate(['/RisultatiR'], {
        queryParams: { query: this.searchText }
      });
    }
  }

  getImmagine(ristorante: AttivitaDto) {
    if(ristorante?.immagineBase64) {
      return "data:image/*;base64," + ristorante.immagineBase64 ;
    }

    return "/assets/strutturaDefault.png";
  }

  vaiAlProfilo(r: AttivitaDto) {
    const nomeEncoded = encodeURIComponent(r.nomeLocale);
    this.router.navigate(['/profilo-struttura', nomeEncoded]);
  }

}
