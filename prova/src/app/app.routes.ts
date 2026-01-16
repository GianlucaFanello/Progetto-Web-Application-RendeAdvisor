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
import { Archive } from './archive/archive';
import { RisultatiR } from './risultati-r/risultati-r';
import {ProfiloStrutturaComponent} from './profilo-struttura/profilo-struttura';
import {ProfiloUtente} from './profilo-utente/profilo-utente';



export const routes: Routes = [
  {
    title: 'RendeAdvisor | Scopri il meglio intorno a te',
    path: '',
    component: HomePage,
  },
  {
    title: 'Profilo | RendeAdvisor',
    path: 'profilo-struttura',
    component: ProfiloStrutturaComponent
  },

  {
    title: 'Cucina & Sapori | RendeAdvisor',
    path: 'restaurants',
    component: Ristorante
  },
  {title: 'Risultati | RendeAdvisor',
   path: 'RisultatiR',
  component: RisultatiR},
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
    title: 'Archivio | RendeAdvisor',
    path: 'archive',
    component: Archive
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
    title: 'Profilo',
    path: 'profilo',
    component: ProfiloUtente
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
