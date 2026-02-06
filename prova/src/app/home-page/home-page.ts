import { Component, OnInit, AfterViewInit } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import { FormsModule } from '@angular/forms';
import {AttivitaService} from '../service/AttivitaService';
import {AttivitaDto} from '../model/attivita.dto';
import {NgForOf, NgIf} from '@angular/common';

declare var google: any;

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [RouterLink, FormsModule, NgForOf, NgIf],
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css']
})
export class HomePage implements OnInit, AfterViewInit {
  searchText: string = "";
  localiOrdinati: AttivitaDto[] = [];
  map: any;
  marker:any;


  constructor(
    private router: Router,
    private attivitaService: AttivitaService
  ) {}

  ngOnInit(): void {
    this.attivitaService.getVicini().subscribe(
      res => {
        this.localiOrdinati = res.data;
      }
    );
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

      this.map = new google.maps.Map(elementoMappa, {
        center: rende,
        zoom: 13,
        disableDefaultUI: false
      });

      this.marker = new google.maps.Marker({
        position: rende,
        map: this.map,
        title: "Rende"
      });
    }
  }

  getImmagine(i:number){
    if(this.localiOrdinati[i].immagineBase64){
      return "data:image/*;base64," + this.localiOrdinati[i].immagineBase64;
    }

    return "/assets/strutturaDefault.png";
  }

  setMarker(attivita: AttivitaDto) {
    if(this.marker) {
      this.marker.setMap(null);
    }
    this.marker = new google.maps.Marker(
      { position: { lat: attivita.latitudine, lng: attivita.longitudine },
        map: this.map,
        title: attivita.nomeLocale
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
