import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-ristorante',
  imports: [RouterLink, FormsModule],
  templateUrl: './ristorante.html',
  styleUrl: './ristorante.css',
})
export class Ristorante {

}
