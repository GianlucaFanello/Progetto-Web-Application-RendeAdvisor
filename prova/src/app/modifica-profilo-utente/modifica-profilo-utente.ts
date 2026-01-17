import {Component, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {UtenteService} from '../service/UtenteService';
import {Router} from '@angular/router';
import {UtenteDto} from '../model/utente.dto';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-modifica-profilo-utente',
  imports: [
    NgIf,
    FormsModule
  ],
  templateUrl: './modifica-profilo-utente.html',
  styleUrls: ['./modifica-profilo-utente.css'],
})
export class ModificaProfiloUtente implements OnInit{

    loading = false;
    utente!:UtenteDto;

    constructor(private utenteService: UtenteService, private router:Router) {
    }

    ngOnInit() {

      this.utenteService.me().subscribe({
        next: (res) => {
          this.utente = res.data;
        }
      });
    }
}
