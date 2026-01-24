import { Component } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AttivitaService} from '../service/AttivitaService';
import {NelCuoreService} from '../service/NelCuoreService';

@Component({
  selector: 'app-aggiungi-r',
  imports: [],
  templateUrl: './aggiungi-r.html',
  styleUrl: './aggiungi-r.css',
})
export class AggiungiR {
  nomeStruttura!: string;

  constructor(
    private route: ActivatedRoute,
    private attivitaService: AttivitaService,
  ) {}


  ngOnInit(): void {

    this.nomeStruttura = decodeURIComponent(
      this.route.snapshot.paramMap.get('nomeLocale')!
    );
  }

}
