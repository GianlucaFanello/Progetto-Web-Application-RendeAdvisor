import {Component, Input, OnInit} from '@angular/core';
import {AttivitaDto} from '../model/attivita.dto';
import {NgForOf, NgOptimizedImage} from '@angular/common';
import {NelCuoreService} from '../service/NelCuoreService';
import {RecensioneService} from '../service/RecensioneService';
import {Router} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-card-struttura',
  imports: [
    NgForOf
  ],
  templateUrl: './card-struttura.html',
  styleUrls: ['./card-struttura.css'],
})
export class CardStruttura implements OnInit{

  @Input() struttura!: AttivitaDto ;

  rating!:number;
  preferiti!:number;


  constructor(private nelCuoreService: NelCuoreService, private recensioneService: RecensioneService, private router:Router) {
  }

  ngOnInit(): void {
     this.nelCuoreService.countPreferiti(this.struttura.nomeLocale).subscribe(
       res=> {
         this.preferiti = res.data;
       }
     )
    this.recensioneService.getRatingStruttura(this.struttura.nomeLocale).subscribe(
      res=> {
        this.rating = res.data ;
      }
    )
  }

  getStarSrc(i:number) {
    const full = Math.floor(this.rating);
    const half = this.rating % 1 >= 0.5;

    if (i < full) return '/assets/star-full.png';
    if (i === full && half) return '/assets/star-half.png';
    return '/assets/star-empty.png';
  }

  getImmagine() {
    if (this.struttura?.immagineBase64) {
      return 'data:image/*;base64,' + this.struttura.immagineBase64;
    }

    return '/assets/strutturaDefault.png';
  }

  vaiAllaModifica() {
    this.router.navigate(['/modifica-struttura', this.struttura.nomeLocale]);
  }

  vaiAlProfilo() {
    const nomeEncoded = encodeURIComponent(this.struttura.nomeLocale);
    this.router.navigate(['/profilo-struttura', nomeEncoded]);
  }

}
