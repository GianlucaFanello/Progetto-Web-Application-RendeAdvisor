
import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { AttivitaService } from '../service/AttivitaService';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css'],
})

export class HomePage {
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
