import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {AttivitaService} from '../service/AttivitaService';

@Component({
  selector: 'app-hotel',
  imports: [FormsModule],
  templateUrl: './hotel.html',
  styleUrl: './hotel.css',
})
export class Hotel {

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
