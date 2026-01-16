import {Component, OnInit} from '@angular/core';
import {AttivitaDto} from '../model/attivita.dto';
import {FormsModule} from '@angular/forms';
import {AttivitaService} from '../service/AttivitaService';
import {Router} from '@angular/router';
import {NgIf} from '@angular/common';
import {UtenteService} from '../service/UtenteService';

@Component({
  selector: 'app-aggiungi-struttura',
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './aggiungi-struttura.html',
  styleUrl: './aggiungi-struttura.css',
})
export class AggiungiStruttura implements OnInit{

  attivita: AttivitaDto = {nomeLocale: '', proprietario: '', telefono: '',
    email: '', descrizione:'', indirizzo:'', tipo:''
  };

  messaggio = '';

  loading = false;

  constructor(private attivitaService:AttivitaService, private utenteService: UtenteService, private router:Router) {
  }

  ngOnInit() {
    this.utenteService.me().subscribe({
      next: (res) => {
        this.attivita.proprietario = res.data.username;
        },
      error: () => {
        this.messaggio = 'Registrati o Accedi';
      }
    });
  }

  salva() {

    if(!this.attivita.proprietario) {
      this.messaggio = 'Registrati o Accedi';
      return ;
    }

    if (!this.attivita.nomeLocale?.trim() || !this.attivita.telefono?.trim() ||
      !this.attivita.email?.trim() || !this.attivita.descrizione?.trim() ||
      !this.attivita.indirizzo?.trim() || !this.attivita.tipo?.trim()) {

      this.messaggio = 'compila tutti i campi';
      return;
    }

    this.loading = true

    this.attivitaService.salva(this.attivita).subscribe({
      next: (res) => {
        this.messaggio = res.message;

        if(res.success) {
          this.router.navigate(['/']);
        }
      },
      error: (err) => {
        console.error("Errore HTTP:", err);
      }
    });

  }
}
