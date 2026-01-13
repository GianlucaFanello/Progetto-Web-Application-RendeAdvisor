import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-archive',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './archive.html',
  styleUrls: ['./archive.css'],
})
export class Archive {
  hotels = [
    { name: 'Hotel Centrale', address: 'Via Roma 1', img: '../../assets/camera.jpg' },
    { name: 'B&B Rende', address: 'Corso Umberto 12', img: '../../assets/camera.jpg' },
  ];

  restaurants = [
    { name: 'La Griglia', address: 'Via Garibaldi 3', img: '../../assets/laGriglia.jpg' },
    { name: 'Mindujo', address: 'Piazza Municipio', img: '../../assets/MINDUJO.jpg' },
  ];
}
