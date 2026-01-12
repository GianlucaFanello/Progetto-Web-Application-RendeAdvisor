import { Routes } from '@angular/router';
import { HomePage } from './home-page/home-page';
import { Ristorante } from './ristorante/ristorante';
import { Hotel } from './hotel/hotel';
import { Recensioni } from './recensioni/recensioni';
import { FaqPage } from './faq-page/faq-page';
import { ModificaProfiloUtente } from './modifica-profilo-utente/modifica-profilo-utente';
import { ProfiloImposta } from './profilo-imposta/profilo-imposta';
import { Login } from './login/login';
import { RegistraPage } from './registra-page/registra-page';
import { AggiungiStruttura } from './aggiungi-struttura/aggiungi-struttura';


export const routes: Routes = [
  {
    title: 'RendeAdvisor | Scopri il meglio intorno a te',
    path: '',
    component: HomePage,
  },
  {
    title: 'Cucina & Sapori | RendeAdvisor',
    path: 'restaurants',
    component: Ristorante
  },
  {
    title: 'Il Tuo Soggiorno | RendeAdvisor',
    path: 'hotels',
    component: Hotel
  },
  {
    title: 'Recensioni | RendeAdvisor',
    path: 'reviews',
    component: Recensioni
  },
  {
    title: 'FAQ | RendeAdvisor',
    path: 'faq',
    component: FaqPage
  },
  {
    title: 'Login | RendeAdvisor',
    path: 'login', 
    component: Login
  },
  {
    title: 'Unisciti a RendeAdvisor | RendeAdvisor',
    path: 'signup', 
    component: RegistraPage
  },
  {
    title: 'Modifica Profilo | RendeAdvisor',
    path: 'modify',
    component: ModificaProfiloUtente
  },
  {
    title: 'Cosa vuoi fare? | RendeAdvisor',
    path: 'choose', 
    component: ProfiloImposta
  },
  {
    title: 'Aggiungi una attività! | RendeAdvisor',
    path: 'addbusiness',
    component: AggiungiStruttura
  },
  { 
    path: '',
    redirectTo: '',
    pathMatch: 'full'
  },
  {
    title: 'Oops! Something goes wrong',
    path: '**',
    component: HomePage
  }
];
