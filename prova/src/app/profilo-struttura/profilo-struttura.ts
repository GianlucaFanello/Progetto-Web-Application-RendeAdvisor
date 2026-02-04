import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { NelCuoreService } from '../service/NelCuoreService';
import { RouterLink } from '@angular/router';
import {AttivitaService} from '../service/AttivitaService';
import {AttivitaDto} from '../model/attivita.dto';
import {AuthService} from '../service/AuthService';
import {UtenteDto} from '../model/utente.dto';

@Component({
  selector: 'app-profilo-struttura',
  templateUrl: './profilo-struttura.html',
  styleUrls: ['./profilo-struttura.css'],
})
export class ProfiloStrutturaComponent implements OnInit {

  nomeStruttura!: string;
  username!:string;
  preferito: boolean = false;
  contatore = 0;
  loading: boolean = true;

  attivita!:AttivitaDto;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private attivitaService: AttivitaService,
    private nelCuoreService: NelCuoreService,
    private authService: AuthService
  ) {}


  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      this.nomeStruttura = params.get('nomeLocale') ?? '';
      this.attivitaService.getByNome(this.nomeStruttura).subscribe({
        next: (a) => {
          this.attivita = a.data;
          this.loading = false;
        },
        error: () => {
          console.error('Errore caricamento struttura');
          this.loading = false;
        }
      });
    });

    const utente = this.authService.getUser();
    if(utente) {
      this.username = utente.username;
    }
    else {
      this.router.navigate(['/choose']);
    }

    this.aggiornaContatore();
    this.contatore = 0;
  }

  togglePreferito(): void {

    const dto = {
      nomeUtente: this.username,
      nomeStruttura: this.nomeStruttura
    };

    if (this.preferito) {
      this.preferito = false;
      this.contatore--;
      this.nelCuoreService.rimuovi(dto).subscribe();
    } else {
      this.preferito = true;
      this.contatore++;
      this.nelCuoreService.aggiungi(dto).subscribe();
    }
  }

  visualizzaRecensioni(nomeStruttura: String) {
    // @ts-ignore
    this.router.navigate(['/recensioni-struttura', encodeURIComponent(nomeStruttura)])

  }

  getImmagine() {
    if(this.attivita?.immagineBase64){
      return "data:image/*;base64," + this.attivita.immagineBase64;
    }

    return "/assets/strutturaDefault.png";
  }

  private aggiornaContatore() {

  }
}
