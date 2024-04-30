import { inject } from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { Observable, map } from 'rxjs';

export const guard = (): Observable<boolean> => {
  const authenticationService = inject(AuthenticationService);
  const router = inject(Router);
  return authenticationService.checkAuthenticated().pipe(
    map((result) =>{
      if(!result){
        router.navigate(['login'])
      }
      return result;
    }

    )
  );
};
