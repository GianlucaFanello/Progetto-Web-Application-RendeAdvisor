import { Component, OnInit } from '@angular/core';
import { NelCuoreService } from '../service/NelCuoreService';
import {RouterLink} from '@angular/router';


@Component({
  selector: 'app-profilo-struttura',
  templateUrl: './profilo-struttura.html',
  styleUrls: ['./profilo-struttura.css']
})
export class ProfiloStrutturaComponent implements OnInit {

  preferito!: boolean;
  contatore = 0;

  constructor(private nelCuoreService: NelCuoreService) {}

  ngOnInit(): void {
    this.nelCuoreService.preferito().subscribe((result: boolean) => {
      this.preferito = result;
    });


  }

  togglePreferito(): void {
    if (this.preferito) {
      this.nelCuoreService.rimuovi().subscribe(() => {
        this.preferito = false;
        this.contatore--;
      });
    } else {
      this.nelCuoreService.salva().subscribe(() => {
        this.preferito = true;
        this.contatore++;
      });
    }
  }
}


