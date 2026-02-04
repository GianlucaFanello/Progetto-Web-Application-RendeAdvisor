import {Component, OnInit} from '@angular/core';
import {UtenteDto} from '../model/utente.dto';
import {UtenteService} from '../service/UtenteService';
import {RecensioneService} from '../service/RecensioneService';
import {AuthService} from '../service/AuthService';
import {Router} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-profilo-utente',
  imports: [],
  templateUrl: './profilo-utente.html',
  styleUrls: ['./profilo-utente.css'],
})
export class ProfiloUtente implements OnInit {

  utente!: UtenteDto;
  urlImmagine?: string;
  n_rec!: number;

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
      this.urlImmagine = this.getImmagine();
      this.caricaRecensioni();
      return;
    }


    this.utenteService.me().subscribe({
      next: (res) => {
        this.utente = res.data;

        this.authService.setUser(res.data);

        this.urlImmagine = this.getImmagine();
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

  caricaRecensioni() {
    this.recensioniService.getCountUtente(this.utente.username).subscribe({
      next: (countRes) => {
        this.n_rec = countRes.data;
      },
      error: () => {
        console.log("Errore nel caricamento delle recensioni");
      }
    });
  }

  modifica() {
    this.router.navigate(['/modify']);
  }

  vaiANelCuore() {
    this.router.navigate(['/nel-cuore']);
  }

  logout() {
    this.utenteService.logout().subscribe({
      next: (res) => {
        if (res.success) {
          this.authService.clearUser();
          this.router.navigate(['/login']);
        }
      }
    });
  }

  getImmagine() {
    if (this.utente?.immagineBase64) {
      return 'data:image/*;base64,' + this.utente.immagineBase64;
    }
    return '/assets/user-profile-icon-free-vector.jpeg';
  }

  visualizzaStrutture() {
    this.router.navigate(['/strutture-utente'], {
      queryParams: { username: this.utente.username }
    });
  }
}
