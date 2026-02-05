import {Component, OnInit} from '@angular/core';
import {AttivitaDto} from '../model/attivita.dto';
import {FormsModule} from '@angular/forms';
import {AttivitaService} from '../service/AttivitaService';
import {Router} from '@angular/router';
import {NgIf} from '@angular/common';
import {UtenteService} from '../service/UtenteService';

@Component({
  selector: 'app-aggiungi-struttura',
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './aggiungi-struttura.html',
  styleUrl: './aggiungi-struttura.css',
})
export class AggiungiStruttura implements OnInit{

  attivita: AttivitaDto = {
    latitudine: 0, longitudine: 0, nomeLocale: '', proprietario: '', telefono: '',
    email: '', descrizione:'', indirizzo:'', tipo:''
  };

  messaggio = '';
  preview: string | null = null;
  selectedFile: File | null = null;

  loading = false;
  urlImmagine?: string | null;

  constructor(private attivitaService:AttivitaService, private utenteService: UtenteService, private router:Router) {
  }

  ngOnInit() {

    this.urlImmagine = '/assets/strutturaDefault.png' ;

    this.utenteService.me().subscribe({
      next: (res) => {
        this.attivita.proprietario = res.data.username;
        },
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
    if (this.attivita?.immagineBase64) {
      return 'data:image/*;base64,' + this.attivita.immagineBase64;
    }

    return '/assets/strutturaDefault.png';
  }

  eliminaImmagine() {
    this.selectedFile = null;
    this.attivita.immagineBase64 = undefined;
    this.preview = null;
    this.urlImmagine = this.getImmagine();
  }

  salva() {

    if(!this.attivita.proprietario) {
      this.router.navigate(['/choose']);
      return ;
    }

    if (!this.attivita.nomeLocale?.trim() || !this.attivita.telefono?.trim() ||
      !this.attivita.email?.trim() || !this.attivita.descrizione?.trim() ||
      !this.attivita.indirizzo?.trim() || !this.attivita.tipo?.trim()) {

      this.messaggio = 'compila tutti i campi';
      return;
    }

    this.loading = true

    const formData = new FormData();
    formData.append("nomeLocale", this.attivita.nomeLocale);
    formData.append("telefono", this.attivita.telefono);
    formData.append("email", this.attivita.email);
    formData.append("descrizione", this.attivita.descrizione);
    formData.append("indirizzo", this.attivita.indirizzo);
    formData.append("tipo", this.attivita.tipo);
    formData.append("proprietario", this.attivita.proprietario);

    if(this.selectedFile) {
      formData.append("immagine", this.selectedFile);
    }

    this.attivitaService.salva(formData).subscribe({
      next: (res) => {
        this.messaggio = res.message;

        if(res.success) {
          this.router.navigate(['/']);
        }
      },
      error: (err) => {
        this.loading = false;
        console.error("Errore HTTP:", err);
      }
    });

  }

  annulla() {
    this.router.navigate(['/']);
  }
}
