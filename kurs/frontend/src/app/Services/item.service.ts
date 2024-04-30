import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { Observable, catchError, count } from 'rxjs';
import { ItemWithAsset } from '../Types/ItemWithAsset';
import { ItemsCounted } from '../Types/ItemsCounted';

@Injectable({
  providedIn: 'root',
})
export class ItemService extends BaseService {
  private getApiUrl(accoundId: string) {
    return `${this.baseUrl}/account/${accoundId}/items`;
  }
  public getItems(accountId: string): Observable<ItemWithAsset[]> {
    return this.httpClient
      .get<ItemWithAsset[]>(this.getApiUrl(accountId))
      .pipe(catchError(this.handleError<ItemWithAsset[]>('getItems', [])));
  }

  public getItem(accountId: string, id: string): Observable<ItemWithAsset> {
    const url = `${this.getApiUrl(accountId)}/${id}`;
    return this.httpClient
      .get<ItemWithAsset>(url)
      .pipe(catchError(this.handleError<ItemWithAsset>('getItem', undefined)));
  }

  public postRandomItem(accountId: string): Observable<ItemWithAsset> {
    const url = `${this.getApiUrl(accountId)}/random`;
    return this.httpClient
      .post<ItemWithAsset>(url, {}, this.httpOptions)
      .pipe(
        catchError(this.handleError<ItemWithAsset>('postRandomItem', undefined))
      );
  }

  public getItemsCounted(accountId: string): Observable<ItemsCounted[]> {
    const url = `${this.getApiUrl(accountId)}/groups`;
    return this.httpClient
      .get<ItemsCounted[]>(url)
      .pipe(catchError(this.handleError<ItemsCounted[]>('getItems', [])));
  }

  public getItemsForAsset(
    accountId: string,
    assetId: string
  ): Observable<ItemWithAsset[]> {
    const url = `${this.getApiUrl(accountId)}/asset/${assetId}`;
    return this.httpClient
      .get<ItemWithAsset[]>(url)
      .pipe(catchError(this.handleError<ItemWithAsset[]>('getItems', [])));
  }

  public deleteItem(accountId: string, id: string): Observable<any> {
    const url = `${this.getApiUrl(accountId)}/${id}`;
    return this.httpClient
      .delete<any>(url, this.httpOptions)
      .pipe(catchError(this.handleError<any>('deleteItem', 'error')));
  }
}
