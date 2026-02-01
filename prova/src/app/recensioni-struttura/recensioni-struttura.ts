import { Component } from '@angular/core';
import {CardRecensione} from '../card-recensione/card-recensione';
import {ActivatedRoute} from '@angular/router';
import {AttivitaService} from '../service/AttivitaService';

@Component({
  selector: 'app-recensioni-struttura',
  imports: [
    CardRecensione
  ],
  templateUrl: './recensioni-struttura.html',
  styleUrl: './recensioni-struttura.css',
})
export class RecensioniStruttura {
  nomeStruttura!: string;


  constructor(
    private route: ActivatedRoute,
    private attivitaService: AttivitaService,
  ) {}




  ngOnInit(): void {

    this.nomeStruttura = decodeURIComponent(
      this.route.snapshot.paramMap.get('nomeStruttura')!
    );
  }


}



