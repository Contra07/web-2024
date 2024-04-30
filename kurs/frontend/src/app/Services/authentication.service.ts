import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { Observable, catchError, map, of } from 'rxjs';
import { AuthResp } from '../Types/AuthResp';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService extends BaseService {
  private apiUrl = `${this.baseUrl}/login`;

  public checkAuthenticated(): Observable<boolean> {
    return this.httpClient.get<boolean>(this.apiUrl).pipe(
      catchError(() => {
        return of(false);
      })
    );
  }

  public Authenticate(
    username: string,
    password: string
  ): Observable<AuthResp> {
    return this.httpClient
      .post<AuthResp>(this.apiUrl, { username: username, password: password })
      .pipe(
        catchError((result) => {
          return of({ id: '' } as AuthResp);
        })
      );
  }

  public getAuthorizationToken(): string | null {
    return localStorage.getItem('token');
  }

  public setAuthorizationToken(token: string): void {
    localStorage.setItem('token', token);
  }

  public clearAuthorizationToken() {
    localStorage.removeItem('token');
    localStorage.clear();
    console.log("aaaaaaaaaaaaaaaaaaa")
  }
}
