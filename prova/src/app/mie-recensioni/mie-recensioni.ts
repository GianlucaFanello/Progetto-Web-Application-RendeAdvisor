import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { RecensioneService } from '../service/RecensioneService';
import { AuthService } from '../service/AuthService';
import { RecensioneDto } from '../model/recensione.dto';

@Component({
  selector: 'app-mie-recensioni',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mie-recensioni.html',
  styleUrls: ['./mie-recensioni.css']
})
export class MieRecensioni implements OnInit {
  listaRecensioni: RecensioneDto[] = [];
  loading: boolean = true;

  constructor(
    private recensioniService: RecensioneService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const utente = this.authService.getUser();
    if (utente) {
      this.recensioniService.getByUtente(utente.username).subscribe({
        next: (res) => {
          this.listaRecensioni = res.data || [];
          this.loading = false;
        },
        error: (err) => {
          console.error("Errore caricamento recensioni", err);
          this.loading = false;
        }
      });
    } else {

      this.router.navigate(['/login']);
    }
  }

  tornaAlProfilo() {
    this.router.navigate(['/profilo']);
  }

  vaiAlLocale(nomeLocale: string) {
    this.router.navigate(['/profilo-struttura', nomeLocale]);
  }
}
