import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { Account } from './Account';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AccountService extends BaseService {
  private apiUrl = `${this.baseUrl}/account`;

  public getAccount(id: string): Observable<Account> {
    const url = `${this.apiUrl}/${id}`;
    return this.httpClient
      .get<Account>(url)
      .pipe(catchError(this.handleError<Account>('getAccount', undefined)));
  }

  public getAccountByName(username: string): Observable<Account> {
    let params = { name: username };
    return this.httpClient
      .get<Account>(this.apiUrl, { params: params })
      .pipe(catchError(this.handleError<Account>('getAccount', undefined)));

  }

  public getIdToken(): string | null {
    return localStorage.getItem('id');
  }

  public setIdToken(token: string): void {
    localStorage.setItem('id', token);
  }

  public clearIdToken() {
    localStorage.removeItem('id');
  }
}
