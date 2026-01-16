import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {UtenteService} from '../service/UtenteService';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class Header {

  constructor(private utenteService:UtenteService, private router:Router) {
  }

  logged() {
    console.log("CLICK PROFILO → controllo login");

    this.utenteService.isLogged().subscribe({
      next: () => {
        console.log("LOGGATO → vado al profilo");
        this.router.navigate(['/profilo']);
      },
      error: () => {
        console.log("NON LOGGATO → vado a choose");
        this.router.navigate(['/choose']);
      }
    });
  }


}
