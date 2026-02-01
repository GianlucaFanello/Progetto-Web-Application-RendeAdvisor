import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecensioneService } from '../service/RecensioneService';
import { RecensioneDto } from '../model/recensione.dto';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-card-recensione',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-recensione.html',
  styleUrls: ['./card-recensione.css'],
})
export class CardRecensione implements OnInit {
  nomeStruttura!: string;
  recensioni: RecensioneDto[] = [];
  loading: boolean = true;
  rating!: string;
  testo!: string;

  constructor(
    private route: ActivatedRoute,
    private recensioneService: RecensioneService
  ) {}

  ngOnInit(): void {
    const param = this.route.snapshot.paramMap.get('nomeStruttura');
    if (param) {
      this.nomeStruttura = decodeURIComponent(param);
      this.caricaRecensioni();
    }
  }

  private caricaRecensioni(): void {
    this.recensioneService.getByLocale(this.nomeStruttura).subscribe({
      next: (response) => {
        if (response && response.dto) {
          this.recensioni = response.dto;
        }
        this.loading = false;
      },
      error: (err) => {
        console.error('Errore caricamento recensioni', err);
        this.loading = false;
      }
    });
  }
}
