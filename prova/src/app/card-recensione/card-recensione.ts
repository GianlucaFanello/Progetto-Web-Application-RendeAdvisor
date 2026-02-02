import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RecensioneDto } from '../model/recensione.dto';

@Component({
  selector: 'app-card-recensione',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-recensione.html',
  styleUrls: ['./card-recensione.css']
})
export class CardRecensione {
  @Input() listaRecensioni: RecensioneDto[] = [];
  @Input() nomeLocale: string = "";
  @Input() proprietario: string = "";
  @Input() isProprietario: boolean = false;

  constructor(private router: Router) {}

  vaiARisposte(recensione: RecensioneDto) {
    this.router.navigate(['/risposte-page'], {
      queryParams: {
        idRecensione: recensione.id,
        nomeLocale: this.nomeLocale,
        proprietario: this.proprietario,
        autoreRec: recensione.nomeUtente,
        testoRec: recensione.testo,
        votoRec: recensione.valutazione
      }
    });
  }
}
