import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AttivitaService} from '../service/AttivitaService';
import {AttivitaDto} from '../model/attivita.dto';
import {UtenteDto} from '../model/utente.dto';
import {NgIf} from '@angular/common';
import {UtenteService} from '../service/UtenteService';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-modifica-profilo-struttura',
  imports: [
    NgIf,
    FormsModule
  ],
  templateUrl: './modifica-profilo-struttura.html',
  styleUrl: './modifica-profilo-struttura.css',
})
export class ModificaProfiloStruttura implements OnInit{

  constructor(private route:ActivatedRoute, private router:Router, private attivitaService: AttivitaService, private utenteService:UtenteService) {
  }

  loading = false;
  attivita!:AttivitaDto;
  preview: string | null = null;
  selectedFile: File | null = null;
  urlImmagine?: String;
  eliminata: string = 'false';
  username!:string;
  vecchioNomeLocale!:string;

  ngOnInit(): void {
    this.route.params.subscribe(
      params => {
        const nomeStruttura = params['struttura'];

        this.attivitaService.getByNome(nomeStruttura).subscribe(
          res => {
            this.attivita = res.data;
            this.vecchioNomeLocale = this.attivita.nomeLocale;
            this.urlImmagine = this.getImmagine();
            console.log("Attivita ", this.attivita);
          }
        );
      });

    this.utenteService.me().subscribe(
      res=> {
        this.username = res.data.username;
      }
    )
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

  annulla() {
    this.router.navigate(['/strutture-utente'], {queryParams: {username: this.username}});
  }

  eliminaImmagine() {
    this.selectedFile = null;
    this.attivita.immagineBase64 = undefined;
    this.preview = null;
    this.eliminata = 'true';
    this.urlImmagine = this.getImmagine();
  }

  salva() {
    this.loading = true;

    const formData = new FormData();

    formData.append("nomeLocale", this.attivita.nomeLocale);
    formData.append("email", this.attivita.email);
    formData.append("telefono", this.attivita.telefono);
    formData.append("indirizzo",this.attivita.indirizzo);
    formData.append("descrizione", this.attivita.descrizione);

    // Se l'utente ha selezionato una nuova immagine
    if (this.selectedFile) {
      formData.append("immagine", this.selectedFile);
    }
    formData.append("eliminata",this.eliminata);
    formData.append("vecchioNomeLocale", this.vecchioNomeLocale);

    this.attivitaService.modificaProfilo(formData).subscribe({
      next: (res) => {
        this.loading = false;
        this.router.navigate(['/profilo-struttura', this.attivita.nomeLocale]);
      },
      error: (err) => {
        this.loading = false;
        console.error(err);
        alert("Errore durante il salvataggio");
      }
    });
  }
}
