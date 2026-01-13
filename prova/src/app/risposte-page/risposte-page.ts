import { Component } from '@angular/core';
import { CardRecensione } from '../card-recensione/card-recensione';

@Component({
  selector: 'app-risposte-page',
  standalone: true,
  imports: [CardRecensione],
  templateUrl: './risposte-page.html',
  styleUrls: ['./risposte-page.css'], // ← corretto
})
export class RispostePage {}
