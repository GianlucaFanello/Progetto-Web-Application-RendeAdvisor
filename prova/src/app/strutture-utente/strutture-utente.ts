import {Component, OnInit} from '@angular/core';
import {CardStruttura} from '../card-struttura/card-struttura';
import {NgForOf, NgIf} from '@angular/common';
import {AttivitaDto} from '../model/attivita.dto';
import {ActivatedRoute, Router} from '@angular/router';
import {AttivitaService} from '../service/AttivitaService';
import {AuthService} from '../service/AuthService';


@Component({
  standalone: true ,
  selector: 'strutture-utente',
  imports: [
    CardStruttura,
    NgIf,
    NgForOf
  ],
  templateUrl: './strutture-utente.html',
  styleUrls: ['./strutture-utente.css']
})

export class StruttureUtente implements  OnInit{

  presenti:boolean = true;
  username!:string;
  strutture: AttivitaDto[] = [] ;

  constructor(private router: Router, private route:ActivatedRoute, private attivitaService: AttivitaService, private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.user$.subscribe(user => {
      if (!user) return;

      this.username = user.username;
      console.log("USERNAME", this.username);

      this.attivitaService.getByProprietario(this.username).subscribe(res => {
        this.strutture = res.data;
        this.presenti = this.strutture.length > 0;
      });
    });
  }

}
