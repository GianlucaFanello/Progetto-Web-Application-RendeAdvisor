import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AttivitaService } from '../service/AttivitaService';
import { AttivitaDto } from '../model/attivita.dto';
import { Router } from '@angular/router';

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
    private router: Router
  ) {}

  ngOnInit(): void {
    this.caricaDati();
  }

  caricaDati() {

    this.attivitaService.getHotel().subscribe({
      next: (res) => {
        // Usa res.data se il backend risponde con ApiResponse, altrimenti adatta
        this.listaHotel = res.data || [];
      },
      error: () => console.error('Errore caricamento hotel')
    });

    this.attivitaService.getRistoranti().subscribe({
      next: (res) => {
        this.listaRistoranti = res.data || [];
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }


  vaiAlDettaglio(nomeLocale: string) {
    // Naviga verso: /profilo-struttura/HotelCentrale
    this.router.navigate(['/profilo-struttura', nomeLocale]);
  }
}
