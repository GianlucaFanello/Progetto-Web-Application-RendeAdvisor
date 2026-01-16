import { Component, OnInit } from '@angular/core';
import { NelCuoreService } from '../service/NelCuoreService';
import {RouterLink} from '@angular/router';


@Component({
  selector: 'app-profilo-struttura',
  templateUrl: './profilo-struttura.html',
  styleUrls: ['./profilo-struttura.css']
})
export class ProfiloStrutturaComponent implements OnInit{

  preferito: boolean = false;
  contatore = 0;

  constructor(private nelCuoreService: NelCuoreService) {}


  ngOnInit(): void {
    this.preferito = false;
    this.contatore = 10;
  }


  togglePreferito(): void {
    const dto = {
      nomeUtente: "utente",
      nomeStruttura: "struttura"
    };

    if (this.preferito) {
          this.preferito = false;
          this.contatore--;
    } else {
          this.preferito = true;
          this.contatore++;
    }
  }
}


