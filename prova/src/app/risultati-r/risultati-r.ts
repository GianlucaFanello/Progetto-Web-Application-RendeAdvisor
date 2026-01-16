import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AttivitaService } from '../service/AttivitaService';
import { AttivitaDto } from '../model/attivita.dto';

@Component({
  selector: 'app-risultati-r',
  imports: [],
  templateUrl: './risultati-r.html',
  styleUrl: './risultati-r.css',
})

export class RisultatiR implements OnInit {
  searchQuery: string = '';
  results: AttivitaDto[] = [];
  loading: boolean = false;
  error: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private attivitaService: AttivitaService
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.searchQuery = params['query'] || '';
      if (this.searchQuery) {
        this.performSearch();
      }
    });
  }

  performSearch() {
    this.loading = true;
    this.error = '';

    this.attivitaService.search(this.searchQuery).subscribe({
      next: (data) => {
        this.results = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Errore durante la ricerca. Riprova più tardi.';
        this.loading = false;
        console.error('Search error:', err);
      }
    });
  }

  viewDetails(nomeLocale: string) {
    this.router.navigate(['/dettaglio', nomeLocale]);
  }

  goBack() {
    this.router.navigate(['/']);
  }
}
