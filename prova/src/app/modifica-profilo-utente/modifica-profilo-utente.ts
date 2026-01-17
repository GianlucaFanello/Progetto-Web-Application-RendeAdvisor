import {Component, OnInit} from '@angular/core';
import {NgIf} from '@angular/common';
import {UtenteService} from '../service/UtenteService';
import {Router} from '@angular/router';
import {UtenteDto} from '../model/utente.dto';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-modifica-profilo-utente',
  imports: [
    NgIf,
    FormsModule
  ],
  templateUrl: './modifica-profilo-utente.html',
  styleUrls: ['./modifica-profilo-utente.css'],
})
export class ModificaProfiloUtente implements OnInit{

  loading = false;
  utente!:UtenteDto;
  preview: string | null = null;
  selectedFile: File | null = null;

  constructor(private utenteService: UtenteService) {
  }


  ngOnInit() {

    this.utenteService.me().subscribe({
      next: (res) => {
        this.utente = res.data;
      }
    });
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];

    if (!file) return;

    this.selectedFile = file;

    const reader = new FileReader();
    reader.onload = () => {
      this.preview = reader.result as string;
    };
    reader.readAsDataURL(file);
  }

  salva() {
    this.loading = true;

    const formData = new FormData();

    formData.append("username", this.utente.username);
    formData.append("nome", this.utente.nome);
    formData.append("cognome", this.utente.cognome);
    formData.append("email", this.utente.email);
    formData.append("descrizione", this.utente.descrizione || "");

    // Se l'utente ha selezionato una nuova immagine
    if (this.selectedFile) {
      formData.append("immagine", this.selectedFile);
    }

    this.utenteService.modificaProfilo(formData).subscribe({
      next: (res) => {
        this.loading = false;
        alert("Profilo aggiornato");
      },
      error: (err) => {
        this.loading = false;
        console.error(err);
        alert("Errore durante il salvataggio");
      }
    });
  }






}
