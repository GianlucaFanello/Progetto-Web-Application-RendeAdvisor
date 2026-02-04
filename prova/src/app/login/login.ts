import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {LoginDto} from '../model/login.dto';
import {FormsModule} from '@angular/forms';
import {UtenteService} from '../service/UtenteService';
import {NgIf} from '@angular/common';
import {AuthService} from '../service/AuthService';

@Component({
  selector: 'app-login',
  imports: [RouterLink, FormsModule, NgIf],
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
})
export class Login {

  credenziali: LoginDto = {email: '', password: ''}
  messaggio = '' ;
  loading = false ;

  constructor(private utenteService: UtenteService, private router:Router, private authService: AuthService) {
  }

  login() {

    if(!this.credenziali.email?.trim() || !this.credenziali.password?.trim() ) {
      this.messaggio = 'Compila tutti i campi'
      return;
    }

    this.loading = true;

    this.utenteService.login(this.credenziali).subscribe({
      next: (res) => {
        this.messaggio = res.message;

        this.utenteService.me().subscribe(meRes => {
          this.authService.setUser(meRes.data); }
        );

        this.router.navigate(['/']);
      },
      error: (err) =>{
        this.loading = false ;
        this.messaggio = err.error.message;
      }
    });
  }
}
