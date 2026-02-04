import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecensioneService } from '../service/RecensioneService';
import { RecensioneDto } from '../model/recensione.dto';
import { FormsModule } from '@angular/forms';
import { AuthService} from '../service/AuthService';

@Component({
  selector: 'app-aggiungi-r',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './aggiungi-r.html',
  styleUrl: './aggiungi-r.css',
})
export class AggiungiR implements OnInit {
  nomeStruttura!: string;
  testoRecensione: string = '';
  voto: number = 5;
  usernameLoggato: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private recensioneService: RecensioneService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.nomeStruttura = decodeURIComponent(
      this.route.snapshot.paramMap.get('nomeLocale')!
    );
    const utente = this.authService.getUser();
    if (utente) {
      this.usernameLoggato = utente.username;
    } else {
      this.usernameLoggato = 'Anonimo';
    }  }

  aggiungi(): void {
    const nuovaRecensione: RecensioneDto = {
      id: '',
      nomeUtente: this.usernameLoggato,
      nomeLocale: this.nomeStruttura,
      testo: this.testoRecensione,
      valutazione: this.voto
    };

    if (this.testoRecensione.trim() === '') {
      alert("Inserisci un testo per la recensione!");
      return;
    }

    this.recensioneService.salva(nuovaRecensione).subscribe({
      next: (response) => {
        if (response.success) {
          alert("Recensione aggiunta con successo!");
          this.indietro();
        }
      },
      error: (err) => {
        console.error("Errore durante il salvataggio", err);
      }
    });
  }

  indietro(): void {
    this.router.navigate(['/ristorante']); 
  }
}
