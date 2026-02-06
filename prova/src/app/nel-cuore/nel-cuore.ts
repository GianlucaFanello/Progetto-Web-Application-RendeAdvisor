import { Component, OnInit } from '@angular/core';
import { NelCuoreService } from '../service/NelCuoreService';
import { UtenteService } from '../service/UtenteService';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { NelCuoreDto } from '../model/nelCuore.dto';
import {AttivitaDto} from '../model/attivita.dto';

@Component({
  selector: 'app-nel-cuore',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './nel-cuore.html',
  styleUrls: ['./nel-cuore.css']
})
export class NelCuore implements OnInit {
  listaPreferiti: AttivitaDto[] = [];
  loading = true;
  usernameLoggato: string = "";

  constructor(
    private nelCuoreService: NelCuoreService,
    private utenteService: UtenteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.utenteService.me().subscribe({
      next: (res) => {
        if (res.success && res.data) {
          this.usernameLoggato = res.data.username;
          this.caricaPreferiti(this.usernameLoggato);
        } else {
          this.loading = false;
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
      error: (err) => {
        this.loading = false;
      }
    });
  }

  vaiAlProfilo(nomeStruttura: string): void {
    this.router.navigate(['/profilo-struttura', nomeStruttura]);
  }

  confermaRimozione(event: Event, nomeStruttura: string): void {
    event.stopPropagation();

    const conferma = confirm("Sei sicuro di voler rimuovere " + nomeStruttura + " dai preferiti?");

    if (conferma) {
      const dto: NelCuoreDto = {
        nomeUtente: this.usernameLoggato,
        nomeStruttura: nomeStruttura
      };

      this.nelCuoreService.rimuovi(dto).subscribe({
        next: (res) => {
          this.listaPreferiti = this.listaPreferiti.filter(s => s.nomeLocale !== nomeStruttura);
        },
        error: (err) => {
          alert("Impossibile rimuovere il preferito al momento.");
        }
      });
    }
  }

  getImmagine(struttura: AttivitaDto) {
    if(struttura?.immagineBase64) {
      return "data:image/*;base64," + struttura.immagineBase64;
    }

    return "assets/strutturaDefault.png";
  }
}
