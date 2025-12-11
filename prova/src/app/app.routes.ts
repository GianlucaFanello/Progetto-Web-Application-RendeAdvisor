import { Routes } from '@angular/router';
import { HomePage } from './home-page/home-page';
import {Ristorante} from './ristorante/ristorante';
import {Hotel} from './hotel/hotel';
import {Recensioni} from './recensioni/recensioni';
import {FaqPage} from './faq-page/faq-page';
import {ModificaProfiloUtente} from './modifica-profilo-utente/modifica-profilo-utente';
import {ProfiloImposta} from './profilo-imposta/profilo-imposta';
import {Login} from './login/login';
import {RegistraPage} from './registra-page/registra-page';
import {AggiungiStruttura} from './aggiungi-struttura/aggiungi-struttura';


export const routes: Routes = [
  {path: '', component: HomePage},
  {path: 'ristorante', component: Ristorante},
  {path: 'hotel', component: Hotel},
  {path: 'Recensioni', component: Recensioni},
  {path: 'Home', component: HomePage},
  {path: 'Faq', component: FaqPage},
  {path: 'modificaProfiloUtente', component: ModificaProfiloUtente},
  {path: 'profiloImposta', component: ProfiloImposta},
  {path: 'login', component: Login},
  {path: 'Registra', component: RegistraPage},
  {path: 'aggiungiStruttura', component: AggiungiStruttura}
];
