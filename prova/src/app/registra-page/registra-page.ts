import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {UtenteService} from '../service/UtenteService';
import {UtenteDto} from '../model/utente.dto';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-registra-page',
  imports: [FormsModule, NgIf],
  templateUrl: './registra-page.html',
  styleUrls: ['./registra-page.css'],
})
export class RegistraPage {

    utente: UtenteDto = {nome: "", cognome: "", username: "", email: "", password: ""};
    messaggio = "" ;
    loading = false;

    constructor(private utenteService: UtenteService, private router:Router) {
    }

    registra(){
      if(!this.utente.nome?.trim() || !this.utente.cognome?.trim()
        || !this.utente.username?.trim() || !this.utente.email?.trim() || !this.utente.password) {
        this.messaggio = "Compila tutti i campi";
        return;
      }

      this.loading = true;

      this.utenteService.registra(this.utente).subscribe(
        {
          next: (res) => {
            this.messaggio = res.message;
            this.router.navigate(['/login']);
          },
          error: (err) => {
            this.messaggio = "Errore nella registrazione!"
            this.loading = false;
          }
        });
    }
}
