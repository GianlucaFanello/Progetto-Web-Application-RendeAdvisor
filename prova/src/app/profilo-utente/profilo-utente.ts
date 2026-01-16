import { Component } from '@angular/core';
import {UtenteDto} from '../model/utente.dto';
import {UtenteService} from '../service/UtenteService';
import {Router} from '@angular/router';
import {RecensioneService} from '../service/RecensioneService';

@Component({
  standalone: true,
  selector: 'app-profilo-utente',
  imports: [],
  templateUrl: './profilo-utente.html',
  styleUrls: ['./profilo-utente.css'],
})
export class ProfiloUtente {

  utente!: UtenteDto;
  n_rec!: number;

  constructor(private utenteService: UtenteService,private recensioniService:RecensioneService, private router: Router) {
  }

  ngOnInit(): void{
    this.utenteService.me().subscribe({
      next: (res) => {
        this.utente = res.data

        this.recensioniService.getCountUtente(this.utente.username).subscribe(
          {
            next: (countRes) => {
              this.n_rec = countRes.data;
            }
          });
      },
      error: (err) => {
        console.log(err.error.messaggio);
      }
    });

  };


  logout() {
    this.utenteService.logout().subscribe({
      next: (res) => {
        if (res.success)
          this.router.navigate(['/login'])
      }
    });
  }

}
