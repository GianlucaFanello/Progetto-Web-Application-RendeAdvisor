import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecensioneService } from '../service/RecensioneService';
import { RecensioneDto } from '../model/recensione.dto';
import { FormsModule } from '@angular/forms';
import { AuthService} from '../service/AuthService';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-aggiungi-r',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './aggiungi-r.html',
  styleUrl: './aggiungi-r.css',
})
export class AggiungiR implements OnInit {
  nomeStruttura!: string;
  testoRecensione: string = '';
  voto: number = 0;
  usernameLoggato: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private recensioneService: RecensioneService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {

    const nomeParam = this.route.snapshot.paramMap.get('nomeLocale');
    this.nomeStruttura = nomeParam ? decodeURIComponent(nomeParam) : '';


    const utente = this.authService.getUser();
    if (utente) {
      this.usernameLoggato = utente.username;
    } else {
      this.usernameLoggato = 'Anonimo';
    }
  }


  setVoto(valore: number): void {
    this.voto = valore;
  }


  aggiungi(): void {

    if (this.voto === 0) {
      alert("Seleziona un numero di stelle per la valutazione!");
      return;
    }

    if (this.testoRecensione.trim() === '') {
      alert("Inserisci un testo per la recensione!");
      return;
    }

    const nuovaRecensione: RecensioneDto = {
      id: 0, // Cambiato da '' a 0
      nomeUtente: this.usernameLoggato,
      nomeLocale: this.nomeStruttura,
      testo: this.testoRecensione,
      valutazione: this.voto
    };

    this.recensioneService.salva(nuovaRecensione).subscribe({
      next: (response) => {

        alert("Recensione aggiunta con successo!");
        this.indietro();
      },
      error: (err) => {
        console.error("Errore durante il salvataggio", err);
        alert("Errore durante il salvataggio della recensione.");
      }
    });
  }

  indietro(): void {

    this.router.navigate(['/restaurants']);
  }
}
