import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RispostaService } from '../service/RispostaService';
import { UtenteService } from '../service/UtenteService';
import { RispostaDto } from '../model/risposta.dto';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {RecensioneDto} from '../model/recensione.dto';
import {UtenteDto} from '../model/utente.dto';
import {AttivitaService} from '../service/AttivitaService';
import {AttivitaDto} from '../model/attivita.dto';

@Component({
  selector: 'app-risposte-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './risposte-page.html',
  styleUrls: ['./risposte-page.css']
})
export class RispostePage implements OnInit {
  proprietarioLocale: string = "";
  nomeLocale: string = "";
  utenteLoggato:UtenteDto | null = null;
  isProprietario: boolean = false;
  listaRisposte: RispostaDto[] = [];
  nuovaRisposta: string = "";
  loading = true;

  locale!: AttivitaDto;

  recensione!: RecensioneDto;

  constructor(
    private route: ActivatedRoute,
    private rispostaService: RispostaService,
    private utenteService: UtenteService,
    private attivitaService: AttivitaService
  ) {}

  ngOnInit(): void {
    const nav = history.state;

    if (nav.recensione) {
      this.recensione = nav.recensione; // <-- ECCO QUI
      this.nomeLocale = nav.nomeLocale;
      this.proprietarioLocale = nav.proprietario;

      this.attivitaService.getByNome(this.nomeLocale).subscribe(
        res => {
          this.locale = res.data;
        })
    }

    this.verificaEIdentifica();
  }


  verificaEIdentifica() {
    this.utenteService.me().subscribe({
      next: (res) => {
        this.utenteLoggato = res.data || null;
        this.isProprietario = ( this.utenteLoggato !== null && this.utenteLoggato?.username === this.proprietarioLocale);
        this.caricaRisposte();
      },
      error: () => this.caricaRisposte()
    });
  }

  caricaRisposte() {
    this.rispostaService.getByRecensione(this.recensione.id).subscribe({
      next: (res) => {
        this.listaRisposte = res.data || [];
        this.loading = false;
      }
    });
  }

  invia() {
    if (!this.nuovaRisposta.trim()) return;
    const dto: RispostaDto = {
      idRecensione: this.recensione.id.toString(),
      utente: this.utenteLoggato!.username,
      risposta: this.nuovaRisposta
    };
    this.rispostaService.salva(dto).subscribe(() => {
      this.nuovaRisposta = "";
      this.caricaRisposte();
    });
  }

  getImmagineProprietario() {
    if(this.utenteLoggato?.immagineBase64){
      return "data:image/*;base64," + this.utenteLoggato.immagineBase64;
    }
    return "assets/user-profile-free-vector.jpeg";
  }

  getImmagineLocale() {
    if(this.locale?.immagineBase64) {
      return "data:image/*;base64," + this.locale.immagineBase64;
    }
    return "assets/strutturaDefault.png";
  }

}
