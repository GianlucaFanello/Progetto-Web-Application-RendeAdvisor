import { Component, OnInit, AfterViewInit } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { FormsModule } from '@angular/forms';
import {AttivitaService} from '../service/AttivitaService';

declare var google: any;

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css']
})
export class HomePage implements OnInit, AfterViewInit {
  searchText: string = "";
  localiOrdinati = [];


  constructor(
    private router: Router,
    private attivitaService: AttivitaService
  ) {}

  ngOnInit(): void {
    this.caricaERiordinaRistoranti();
  }
  ngAfterViewInit(): void {
    this.caricaScriptMappa();
  }

  caricaScriptMappa() {
    if (typeof google !== 'undefined' && google.maps) {
      this.mostraMappa();
      return;
    }

    if (document.getElementById('google-maps-script')) {
      setTimeout(() => this.mostraMappa(), 500);
      return;
    }

    const script = document.createElement('script');
    script.id = 'google-maps-script';
    script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyAfaX4yY-9HFApHUu2Rw_KFXNEjY5rVIOM';
    script.async = true;
    script.defer = true;
    script.onload = () => this.mostraMappa();
    document.body.appendChild(script);
  }

  mostraMappa() {
    const elementoMappa = document.getElementById('mappa-google');
    if (elementoMappa) {
      const rende = { lat: 39.330, lng: 16.183 };

      const map = new google.maps.Map(elementoMappa, {
        center: rende,
        zoom: 13,
        disableDefaultUI: false
      });

      new google.maps.Marker({
        position: rende,
        map: map,
        title: "Rende"
      });
    }
  }
  caricaERiordinaRistoranti() {
    this.attivitaService.getTutte().subscribe(response => {
      // @ts-ignore
      this.localiOrdinati = response.data.map((r: any) => {
        return {
          ...r,
          distanza: calcolaDistanza(CENTRO_RENDE.lat, CENTRO_RENDE.lng, r.latitudine, r.longitudine)
        };
      }).sort((a: any, b: any) => a.distanza - b.distanza);
    });
  }

  search() {
    if (this.searchText.trim()) {
      this.router.navigate(['/RisultatiR'], {
        queryParams: { query: this.searchText }
      });
    }
  }
}
