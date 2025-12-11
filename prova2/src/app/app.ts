import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {TopBar} from './top-bar/top-bar';
import {HomePage} from './home-page/home-page';
import {Hotel} from './hotel/hotel';
import {TopBarHome} from './top-bar-home/top-bar-home';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TopBar, HomePage, Hotel, TopBarHome],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('prova');
}
