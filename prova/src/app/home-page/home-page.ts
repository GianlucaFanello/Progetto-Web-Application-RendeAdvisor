import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css'], // <-- anche "styleUrls", non "styleUrl"
})
export class HomePage { }
