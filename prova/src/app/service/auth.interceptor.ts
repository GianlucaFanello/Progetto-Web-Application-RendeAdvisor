import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './AuthService';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return next(req).pipe(
    catchError(err => {

      // Ignora i 401 di /me
      if (req.url.includes('/me')) {
        return throwError(() => err);
      }

      // Gestisci gli altri 401
      if (err.status === 401) {
        auth.clearUser();
        router.navigate(['/login']);
      }

      return throwError(() => err);
    })
  );
};

