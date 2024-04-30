import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { Asset } from '../Types/Asset';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export class AssetService extends BaseService {
  private apiUrl: string = `${this.baseUrl}/assets`;
  public getAssets(): Observable<Asset[]> {
    return this.httpClient
      .get<Asset[]>(this.apiUrl)
      .pipe(catchError(this.handleError<Asset[]>('getAssets', [])));
  }

  public getAsset(id: string): Observable<Asset> {
    const url = `${this.apiUrl}/${id}`;
    return this.httpClient
      .get<Asset>(url)
      .pipe(catchError(this.handleError<Asset>('getAssets', undefined)));
  }

  public getEmptyAsset(): Asset {
    return {
      id: '',
      name: '',
      picturePath: '',
    } as Asset;
  }

  public updateAsset(asset: Asset): Observable<Asset> {
    const url = `${this.apiUrl}/${asset.id}`;
    return this.httpClient
      .put<Asset>(url, asset, this.httpOptions)
      .pipe(catchError(this.handleError<Asset>('updateProject', undefined)));
  }

  public addAsset(asset: Asset): Observable<Asset> {
    return this.httpClient
      .post<Asset>(this.apiUrl, asset, this.httpOptions)
      .pipe(catchError(this.handleError<Asset>('addProject', undefined)));
  }

  public deleteAsset(id: string): Observable<Asset> {
    const url = `${this.apiUrl}/${id}`;
    return this.httpClient
      .delete<Asset>(url, this.httpOptions)
      .pipe(catchError(this.handleError<Asset>('deleteProject', undefined)));
  }
}
