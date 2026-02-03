import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {AttivitaService} from '../service/AttivitaService';
import {AttivitaDto} from '../model/attivita.dto';
import {ApiResponseDto} from '../model/apiResponse.dto';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-hotel',
  imports: [FormsModule,CommonModule],
  templateUrl: './hotel.html',
  styleUrl: './hotel.css',
})
export class Hotel implements OnInit{

  listaHotel: AttivitaDto[] = [];
  searchText: string = '';

  constructor(
    private router: Router,
    private attivitaService: AttivitaService
  ) {}

  ngOnInit() {
    this.caricaHotel();
  }

  caricaHotel() {

    this.attivitaService.getHotel().subscribe({
      next: (response: ApiResponseDto<AttivitaDto[]>) => {
        if (response.success) {
          this.listaHotel = response.data;
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
}



