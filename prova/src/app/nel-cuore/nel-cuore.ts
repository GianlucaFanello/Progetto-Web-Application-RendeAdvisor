import { Component, OnInit } from '@angular/core';
import { NelCuoreService } from '../service/NelCuoreService';
import { UtenteService } from '../service/UtenteService';
import { NgIf, NgFor, CommonModule } from '@angular/common';

@Component({
  selector: 'app-nel-cuore',
  standalone: true,
  imports: [CommonModule, NgIf, NgFor],
  templateUrl: './nel-cuore.html',
  styleUrls: ['./nel-cuore.css']
})
export class NelCuore implements OnInit {
  listaPreferiti: string[] = [];
  loading = true;

  constructor(
    private nelCuoreService: NelCuoreService,
    private utenteService: UtenteService
  ) {}

  ngOnInit(): void {

    this.utenteService.me().subscribe({
      next: (res) => {
        if (res.success && res.data) {
          // 2. Se loggato, carica la sua lista preferiti
          this.caricaPreferiti(res.data.username);
        }
      },
      error: () => this.loading = false
    });
  }

  caricaPreferiti(username: string): void {
    this.nelCuoreService.lista(username).subscribe({
      next: (res) => {
        this.listaPreferiti = res.data || [];
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }
}
