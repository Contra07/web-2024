import { Injectable } from '@angular/core';
import { BaseService } from './base.service';
import { Observable, catchError } from 'rxjs';
import { RecipeWithResult } from '../Types/RecipeWithResult';
import { AssetIn } from '../Types/AssetIn';
import { Asset } from '../Types/Asset';

@Injectable({
  providedIn: 'root',
})
export class RecipeService extends BaseService {
  private apiUrl: string = `${this.baseUrl}/recipes`;

  public getRecipes(): Observable<RecipeWithResult[]> {
    return this.httpClient
      .get<RecipeWithResult[]>(this.apiUrl)
      .pipe(catchError(this.handleError<RecipeWithResult[]>('getRecipes', [])));
  }

  public getRecipe(id: string): Observable<RecipeWithResult> {
    const url = `${this.apiUrl}/${id}`;
    return this.httpClient
      .get<RecipeWithResult>(url)
      .pipe(
        catchError(this.handleError<RecipeWithResult>('getRecipe', undefined))
      );
  }

  public postRecipe(
    assetIns: AssetIn[],
    assetOut: Asset
  ): Observable<RecipeWithResult> {
    return this.httpClient
      .post<RecipeWithResult>(this.apiUrl, { assetIns, assetOut })
      .pipe(
        catchError(this.handleError<RecipeWithResult>('getRecipes', undefined))
      );
  }

  public putRecipe(
    id: string,
    assets: AssetIn[],
    out: RecipeWithResult
  ): Observable<RecipeWithResult> {
    const url = `${this.apiUrl}/${id}`;
    return this.httpClient
      .put<RecipeWithResult>(
        url,
        { assetIns: assets, assetOut: out },
        this.httpOptions
      )
      .pipe(
        catchError(this.handleError<RecipeWithResult>('getRecipes', undefined))
      );
  }

  public deleteRecipe(id: string): Observable<boolean> {
    const url = `${this.apiUrl}/${id}`;
    return this.httpClient
      .delete<boolean>(url, this.httpOptions)
      .pipe(catchError(this.handleError<boolean>('deletRecipe', undefined)));
  }

  public getEmptyRecipe(asset: Asset) {
    return {
      asset: asset,
      id: '',
      quantity: 1,
    } as RecipeWithResult;
  }
}
