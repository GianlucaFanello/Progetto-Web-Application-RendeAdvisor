import {Component, OnInit} from '@angular/core';
import {UtenteDto} from '../model/utente.dto';
import {UtenteService} from '../service/UtenteService';
import {RecensioneService} from '../service/RecensioneService';
import {AuthService} from '../service/AuthService';
import {Router} from '@angular/router';
import {RecensioneDto} from '../model/recensione.dto';
import {CommonModule} from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-profilo-utente',
  imports: [CommonModule],
  templateUrl: './profilo-utente.html',
  styleUrls: ['./profilo-utente.css'],
})
export class ProfiloUtente implements OnInit {

  utente!: UtenteDto;
  urlImmagine?: string;
  n_rec!: number;

  listaRecensioni: RecensioneDto[] = [];
  mostraRecensioni: boolean = false;
  isLoggingOut: boolean = false;

  constructor(
    private utenteService: UtenteService,
    private recensioniService: RecensioneService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const cached = this.authService.getUser();
    if (cached) {
      this.utente = cached;
      this.urlImmagine = this.setFoto();
      this.caricaRecensioni();
      return;
    }

    this.utenteService.me().subscribe({
      next: (res) => {
        this.utente = res.data;
        this.authService.setUser(res.data);
        this.urlImmagine = this.setFoto();
        this.caricaRecensioni();
      },
      error: (err) => {
        if (err.status === 401) {
          this.authService.clearUser();
          this.router.navigate(['/choose']);
        }
      }
    });
  }

  setFoto(): string {
    if (this.utente && this.utente.immagineBase64) {
      return 'data:image/png;base64,' + this.utente.immagineBase64;
    }
    return '/assets/user-profile-icon-free-vector.jpeg';
  }

  caricaRecensioni() {
    this.recensioniService.getCountUtente(this.utente.username).subscribe({
      next: (countRes) => {
        this.n_rec = countRes.data;
      }
    });
  }

  toggleRecensioni() {
    if (this.mostraRecensioni) {
      this.mostraRecensioni = false;
    } else {
      this.recensioniService.getByUtente(this.utente.username).subscribe({
        next: (res) => {
          this.listaRecensioni = res.data || [];
          this.mostraRecensioni = true;
        }
      });
    }
  }


  vaiAMieRecensioni() {
    this.router.navigate(['/mie-recensioni']);
  }

  modifica() {
    this.router.navigate(['/modify']);
  }

  vaiANelCuore() {
    this.router.navigate(['/nel-cuore']);
  }

  visualizzaStrutture() {
    this.router.navigate(['/strutture-utente']);
  }

  logout() {
    this.isLoggingOut = true;
    setTimeout(() => {
      this.utenteService.logout().subscribe({
        next: (res) => {
          if (res.success) {
            this.authService.clearUser();
            this.router.navigate(['/']);
          }
        },
        error: () => {
          this.authService.clearUser();
          this.router.navigate(['/']);
        }
      });
    }, 800);
  }
}
