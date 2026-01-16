import { Component, OnInit } from '@angular/core';
import { NelCuoreService } from '../service/NelCuoreService';
import {RouterLink} from '@angular/router';


@Component({
  selector: 'app-profilo-struttura',
  templateUrl: './profilo-struttura.html',
  styleUrls: ['./profilo-struttura.css']
})
export class ProfiloStrutturaComponent implements OnInit {

  preferito!: boolean
  contatore = 0;

  constructor(private nelCuoreService: NelCuoreService) {}

  ngOnInit(): void {
    this.nelCuoreService.preferito("utente", "struttura").subscribe(res => {
      if (res.success) {
        this.preferito = res.data;
      }
    });
  }


  togglePreferito(): void {
    const dto = {
      nomeUtente: "utente",
      nomeStruttura: "struttura"
    };

    if (this.preferito) {
      this.nelCuoreService.rimuovi(dto).subscribe(res => {
        if (res.success) {
          this.preferito = false;
          this.contatore--;
        }
      });
    } else {
      this.nelCuoreService.salva(dto).subscribe(res => {
        if (res.success) {
          this.preferito = true;
          this.contatore++;
        }
      });
    }
  }
}


