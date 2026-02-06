import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { NelCuoreService } from '../service/NelCuoreService';
import { RouterLink } from '@angular/router';
import {AttivitaService} from '../service/AttivitaService';
import {AttivitaDto} from '../model/attivita.dto';
import {AuthService} from '../service/AuthService';
import {UtenteDto} from '../model/utente.dto';
import {RecensioneService} from '../service/RecensioneService';

@Component({
  selector: 'app-profilo-struttura',
  templateUrl: './profilo-struttura.html',
  styleUrls: ['./profilo-struttura.css'],
  standalone: true
})
export class ProfiloStrutturaComponent implements OnInit {

  username!:string;
  preferito!: boolean;
  contatore = 0;
  loading: boolean = true;
  rating!: number;

  attivita!:AttivitaDto;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private attivitaService: AttivitaService,
    private nelCuoreService: NelCuoreService,
    private authService: AuthService,
    private recensioneService: RecensioneService
  ) {}


  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      const nomeStruttura = params.get('nomeLocale') ?? '';
      const nomeEncoded = decodeURIComponent(nomeStruttura);

      this.attivitaService.getByNome(nomeEncoded).subscribe({
        next: a => {
          this.attivita = a.data;

          this.aggiornaContatore();

          this.loading = false;
        },
        error: () => {
          console.error('Errore caricamento struttura');
          this.loading = false;
        }
      });

      this.recensioneService.getRatingStruttura(nomeEncoded).subscribe(
        res => {
          this.rating = res.data;
        }
      )

      this.authService.user$.subscribe(utente => {

        if (!utente) {
          this.preferito = false;
          return;
        }

        this.username = utente.username;

        // 5. Carico il preferito SOLO se loggato
        this.nelCuoreService.preferito(this.username, nomeStruttura)
          .subscribe(res => this.preferito = res.data);

      });

    });
  }



  togglePreferito(): void {

    const dto = {
      nomeUtente: this.username,
      nomeStruttura: this.attivita.nomeLocale
    };

    if (this.preferito) {
      this.preferito = false;
      this.nelCuoreService.rimuovi(dto).subscribe(
        res => {
          this.aggiornaContatore();
        });

    } else {
      this.preferito = true;
      this.nelCuoreService.aggiungi(dto).subscribe(
        res => {
          this.aggiornaContatore();
        });
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
    if (!this.attivita) return;

    this.nelCuoreService.countPreferiti(this.attivita.nomeLocale).subscribe(
      res => this.contatore = res.data
    );
  }

}
