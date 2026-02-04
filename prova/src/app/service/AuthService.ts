import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { UtenteDto } from '../model/utente.dto';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private userSubject = new BehaviorSubject<UtenteDto | null>(null);
  user$ = this.userSubject.asObservable();

  setUser(user: UtenteDto | null) {
    this.userSubject.next(user);
  }

  getUser(): UtenteDto | null {
    return this.userSubject.value;
  }

  clearUser() {
    this.userSubject.next(null);
  }
}
