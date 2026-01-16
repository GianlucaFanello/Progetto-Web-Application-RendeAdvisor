import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {AttivitaService} from '../service/AttivitaService';

@Component({
  selector: 'app-ristorante',
  imports: [FormsModule],
  templateUrl: './ristorante.html',
  styleUrl: './ristorante.css',
})
export class Ristorante {
  searchText: string = '';

  constructor(
    private router: Router,
    private attivitaService: AttivitaService
  ) { }

  search() {
    if (this.searchText.trim()) {
      this.router.navigate(['/RisultatiR'], {
        queryParams: { query: this.searchText }
      });
    }
  }


}
