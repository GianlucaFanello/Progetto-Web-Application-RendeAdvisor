import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NelCuoreService } from '../service/NelCuoreService';
import { RouterLink } from '@angular/router';
import {AttivitaService} from '../service/AttivitaService';

@Component({
  selector: 'app-profilo-struttura',
  templateUrl: './profilo-struttura.html',
  styleUrls: ['./profilo-struttura.css'],
})
export class ProfiloStrutturaComponent implements OnInit {

  nomeStruttura!: string;
  preferito: boolean = false;
  contatore = 0;
  email!: string;
  indirizzo!: string;
  telefono!: string;
  proprietario!: string;
  descrizione!: string;
  loading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private attivitaService: AttivitaService,
    private nelCuoreService: NelCuoreService
  ) {}


  ngOnInit(): void {

    this.nomeStruttura = decodeURIComponent(
      this.route.snapshot.paramMap.get('nomeLocale')!
    );

    this.attivitaService.getByNome(this.nomeStruttura).subscribe({
      next: (attivita) => {
        this.email = attivita.email;
        this.indirizzo = attivita.indirizzo;
        this.telefono = attivita.telefono;
        this.proprietario = attivita.proprietario;
        this.descrizione = attivita.descrizione;
        this.loading = false;
      },
      error: () => {
        console.error('Errore caricamento struttura');
        this.loading = false;
      }
    });

    this.contatore = 10;
  }
  togglePreferito(): void {

    const dto = {
      nomeUtente: 'utente',           // TODO: utente reale
      nomeStruttura: this.nomeStruttura
    };

    if (this.preferito) {
      this.preferito = false;
      this.contatore--;
      // this.nelCuoreService.rimuovi(dto).subscribe();
    } else {
      this.preferito = true;
      this.contatore++;
      // this.nelCuoreService.aggiungi(dto).subscribe();
    }
  }
}
