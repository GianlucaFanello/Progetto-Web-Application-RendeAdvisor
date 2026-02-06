import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AttivitaService } from '../service/AttivitaService';
import { AttivitaDto } from '../model/attivita.dto';
import { Router } from '@angular/router';
import {RecensioneService} from '../service/RecensioneService';

@Component({
  selector: 'app-archive',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './archive.html',
  styleUrls: ['./archive.css']
})
export class Archive implements OnInit {

  listaHotel: AttivitaDto[] = [];
  listaRistoranti: AttivitaDto[] = [];
  loading = true;

  constructor(
    private attivitaService: AttivitaService,
    private router: Router,
    private recensioneService: RecensioneService
  ) {}

  ngOnInit(): void {
    this.caricaDati();
  }

  caricaDati() {

    this.attivitaService.getHotel().subscribe({
      next: (res) => {
        this.listaHotel = res.data || [];

        this.listaHotel.forEach(
          hotel => {
            this.recensioneService.getRatingStruttura(hotel.nomeLocale).subscribe(
              res => {
                (hotel as any).rating = res.data
                this.listaHotel = [...this.listaHotel];
                console.log("Rating per", hotel.nomeLocale, "=", res.data);
              }
            );
          });
      },
      error: () => console.error('Errore caricamento hotel')
    });

    this.attivitaService.getRistoranti().subscribe({
      next: (res) => {
        this.listaRistoranti = res.data || [];

        this.listaRistoranti.forEach(
          rist => {
            this.recensioneService.getRatingStruttura(rist.nomeLocale).subscribe(
              res => {
                (rist as any).rating = res.data;
                this.listaRistoranti = [...this.listaRistoranti]
              })
          })
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }


  vaiAlDettaglio(nomeLocale: string) {
    this.router.navigate(['/profilo-struttura', nomeLocale]);
  }

  visualizzaRecensioni(nomeLocale: string) {
    this.router.navigate(['/recensioni-struttura',nomeLocale]);
  }

  getStarSrc(i:number, attivita: AttivitaDto) {
    const rating = (attivita as any).rating;

    if (rating === undefined || rating === null) {
      return '/assets/star-empty.png'; // fallback temporaneo
    }

    const full = Math.floor(rating);
    const half = rating % 1 >= 0.5;

    if (i < full) return '/assets/star-full.png';
    if (i === full && half) return '/assets/star-half.png';
    return '/assets/star-empty.png';
    }
}
