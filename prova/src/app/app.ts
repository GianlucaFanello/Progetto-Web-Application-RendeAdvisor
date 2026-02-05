import {Component, OnInit, signal} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './header/header';
import { Footer } from './footer/footer';
import {UtenteService} from './service/UtenteService';
import {AuthService} from './service/AuthService';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header, Footer],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit{

  constructor(
    private utenteService: UtenteService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.utenteService.me().subscribe({
      next: res => {
        this.authService.setUser(res.data)
      },
      error: () => {
        this.authService.clearUser()
      }
    });
  }


}
