import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { AssetIn } from '../Types/AssetIn';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AssetInService extends BaseService {
  private getApiUrl(recipeId: string) {
    return `${this.baseUrl}/recipes/${recipeId}/assets`;
  }

  public getAssetIns(recipeId: string): Observable<AssetIn[]> {
    return this.httpClient
      .get<AssetIn[]>(this.getApiUrl(recipeId))
      .pipe(catchError(this.handleError<AssetIn[]>('getAssetIns', [])));
  }

  public getAssetIn(recipeId: string, id: string): Observable<AssetIn> {
    const url = `${this.getApiUrl(recipeId)}/${id}`;
    return this.httpClient
      .get<AssetIn>(url)
      .pipe(catchError(this.handleError<AssetIn>('getAssetIns', undefined)));
  }

  public getEmptyAsset(column: number, row: number): AssetIn {
    return {
      asset: {
        id: '',
        name: '',
        picturePath: '',
      },
      recipeId: '',
      quantity: 0,
      column: column,
      row: row
    } as AssetIn;
  }
}
