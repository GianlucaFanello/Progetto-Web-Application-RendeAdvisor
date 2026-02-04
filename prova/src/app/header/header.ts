import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {UtenteService} from '../service/UtenteService';
import {AuthService} from '../service/AuthService';
import {UtenteDto} from '../model/utente.dto';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class Header implements OnInit {

  urlImmagine?: string;
  utente?: UtenteDto;
  isLogged = false;

  constructor(
    private utenteService: UtenteService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.authService.user$.subscribe(user => {
      this.utente = user || undefined;
      this.isLogged = !!user;
      this.urlImmagine = this.getImmagine();
    });
  }

  logged() {
    if (this.isLogged) {
      this.router.navigate(['/profilo']);
    } else {
      this.router.navigate(['/choose']);
    }
  }

  getImmagine() {
    if (this.utente?.immagineBase64) {
      return "data:image/*;base64," + this.utente.immagineBase64;
    }
    return "/assets/user-profile-icon-free-vector.jpeg";
  }
}
