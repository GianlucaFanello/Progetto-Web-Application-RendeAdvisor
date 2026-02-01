import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RispostaService } from '../service/RispostaService';
import { UtenteService } from '../service/UtenteService';
import { RispostaDto } from '../model/risposta.dto';
import { CommonModule, NgIf, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-risposte-page',
  standalone: true,
  imports: [CommonModule, NgIf, NgFor, FormsModule],
  templateUrl: './risposte-page.html',
  styleUrls: ['./risposte-page.css']
})
export class RispostePage implements OnInit {
  idRecensione!: number;
  proprietarioLocale: string = "";
  nomeLocale: string = "";

  utenteLoggato: string = "";
  isProprietario: boolean = false;

  listaRisposte: RispostaDto[] = [];
  nuovaRisposta: string = "";
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private rispostaService: RispostaService,
    private utenteService: UtenteService
  ) {}

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      this.idRecensione = +params['idRecensione'];
      this.nomeLocale = params['nomeLocale'] || 'Dettaglio Recensione';
      this.proprietarioLocale = params['proprietario'] || '';

      this.verificaEIdentifica();
    });
  }

  verificaEIdentifica() {
    this.utenteService.me().subscribe({
      next: (res) => {
        this.utenteLoggato = res.data?.username || "";

        this.isProprietario = (this.utenteLoggato === this.proprietarioLocale && this.utenteLoggato !== "");
        this.caricaRisposte();
      },
      error: () => this.caricaRisposte()
    });
  }

  caricaRisposte() {
    this.rispostaService.getByRecensione(this.idRecensione).subscribe({
      next: (res) => {
        this.listaRisposte = res.data || [];
        this.loading = false;
      }
    });
  }

  invia() {
    if (!this.nuovaRisposta.trim()) return;

    const dto: RispostaDto = {
      idRecensione: this.idRecensione.toString(),
      utente: this.utenteLoggato,
      risposta: this.nuovaRisposta
    };

    this.rispostaService.salva(dto).subscribe(() => {
      this.nuovaRisposta = "";
      this.caricaRisposte();
    });
  }
}
