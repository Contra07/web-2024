import { Component, OnInit } from '@angular/core';
import { Asset } from '../../../Types/Asset';
import { AssetService } from '../../../Services/asset.service';
@Component({
  selector: 'app-asset-list',
  templateUrl: './asset-list.component.html',
  styleUrl: './asset-list.component.css',
})
export class AssetListComponent implements OnInit {
  protected assets: Asset[] = [];
  protected assetsFiltred: Asset[] = [];
  constructor(private service: AssetService) {}
  private getAssets(): void {
    this.service.getAssets().subscribe((assets) => (this.assets = assets, this.assetsFiltred = assets));
  }
  ngOnInit(): void {
    this.getAssets();
  }
  onEnter(value: string): void {
    if (value === null || value.match(/^ *$/) !== null) {
      this.assetsFiltred = this.assets;
    } else {
      this.assetsFiltred = this.assets.filter((asset) => {
        return (
          asset.name.toUpperCase().includes(value.toUpperCase())
        );
      });
    }
  }
  deleteAsset(id: string){
    if(id != null){
      this.service.deleteAsset(id).subscribe((result)=>{
        this.getAssets();
      })
    }
  }
}
