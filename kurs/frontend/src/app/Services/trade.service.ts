import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { ItemToTradeWithAsset } from '../Types/ItemToTradeWithAsset';
import { Observable, catchError } from 'rxjs';
import { ItemWithAsset } from '../Types/ItemWithAsset';
import { Asset } from '../Types/Asset';
import { ItemToTrade } from '../Types/ItemToTrade';

@Injectable({
  providedIn: 'root',
})
export class TradeService extends BaseService {
  private apiUrl: string = `${this.baseUrl}/trade`;

  public postTrade(
    accountId: string,
    items: ItemToTradeWithAsset[]
  ): Observable<ItemWithAsset> {
    const url = `${this.apiUrl}/${accountId}`;
    return this.httpClient
      .post<ItemWithAsset>(url, items, this.httpOptions)
      .pipe(
        catchError(this.handleError<ItemWithAsset>('postTrade', undefined))
      );
  }

  public postCheckTrade(items: ItemToTradeWithAsset[]): Observable<Asset> {
    let itemsTT: ItemToTrade[] = [];
    items.forEach(
      (item) => {itemsTT.push({id: item.item.id, column: item.column, row: item.row, quantity: item.quantity})}
    )
    return this.httpClient
      .post<Asset>(this.apiUrl, itemsTT, this.httpOptions)
      .pipe(catchError(this.handleError<Asset>('postCheckTrade', undefined)));
  }

  public getEmptyItemToTrade(
    accountId: string,
    column: number,
    row: number
  ): ItemToTradeWithAsset {
    {
      return {
        item: {
          asset: {
            id: '',
            name: '',
            picturePath: '',
          },
          id: '',
          accountId: accountId,
          quantity: 0,
          createdOn: new Date(),
        },
        column: column,
        row: row,
        id: '',
        quantity: 0,
      } as ItemToTradeWithAsset;
    }
  }
}
