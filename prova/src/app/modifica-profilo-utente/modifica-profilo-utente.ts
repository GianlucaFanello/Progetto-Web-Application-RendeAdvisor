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
  urlImmagine?: String;
  eliminata: string = 'false';
  constructor(private utenteService: UtenteService, private router:Router) {
  }


  ngOnInit() {

    this.utenteService.me().subscribe({
      next: (res) => {
        this.utente = res.data;
        this.urlImmagine = this.getImmagine();
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

    event.target.value = null;
  }

  getImmagine() {
    if (this.utente?.immagineBase64) {
      return 'data:image/*;base64,' + this.utente.immagineBase64;
    }

    return '/assets/user-profile-icon-free-vector.jpeg';
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
    formData.append("eliminata",this.eliminata);

    this.utenteService.modificaProfilo(formData).subscribe({
      next: (res) => {
        this.loading = false;
        this.router.navigate(['/profilo']);
      },
      error: (err) => {
        this.loading = false;
        console.error(err);
        alert("Errore durante il salvataggio");
      }
    });
  }


  annulla() {
    this.router.navigate(['/profilo']);
  }

  eliminaImmagine() {
    this.selectedFile = null;
    this.utente.immagineBase64 = undefined;
    this.preview = null;
    this.eliminata = 'true';
    this.urlImmagine = this.getImmagine();
  }
}
