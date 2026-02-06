import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CardRecensione } from '../card-recensione/card-recensione';
import { AttivitaService } from '../service/AttivitaService';
import { UtenteService } from '../service/UtenteService';
import { AttivitaDto } from '../model/attivita.dto';

@Component({
  selector: 'app-recensioni-struttura',
  standalone: true,
  imports: [CommonModule, CardRecensione],
  templateUrl: './recensioni-struttura.html',
  styleUrls: ['./recensioni-struttura.css']
})
export class RecensioniStruttura implements OnInit {
  attivita?: AttivitaDto;
  utenteLoggato: string = "";
  isProprietario: boolean = false;
  loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private attivitaService: AttivitaService,
    private utenteService: UtenteService
  ) {}

  ngOnInit(): void {
    const nomeLocale = this.route.snapshot.paramMap.get('nomeStruttura');

    if (nomeLocale) {
      this.caricaDati(decodeURIComponent(nomeLocale));
    }
  }

  caricaDati(nomeLocale: string) {

    this.attivitaService.getDettaglio(nomeLocale).subscribe({
      next: (res) => {
        this.attivita = res.data;
        console.log("ATTIVITA:", res.data);
        this.checkUtente();
      },
      error: (err) => {
        console.error("Errore caricamento attività", err);
        this.loading = false;
      }
    });
  }

  checkUtente() {

    this.utenteService.me().subscribe({
      next: (res) => {
        if (res.success && res.data) {
          this.utenteLoggato = res.data.username;
          if (this.attivita) {
            this.isProprietario = (this.attivita.proprietario === this.utenteLoggato);
          }
        }
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }
}
