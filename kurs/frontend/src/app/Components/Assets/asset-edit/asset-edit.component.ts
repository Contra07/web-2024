import { Component, OnInit } from '@angular/core';
import { Asset } from '../../../Types/Asset';
import { AssetService } from '../../../Services/asset.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-asset-edit',
  templateUrl: './asset-edit.component.html',
  styleUrl: './asset-edit.component.css',
})
export class AssetEditComponent implements OnInit {
  protected asset: Asset;
  private isNewAsset: boolean = true;
  protected isError: boolean = false;
  constructor(
    private service: AssetService,
    private router: Router,
    private activeRoute: ActivatedRoute
  ) {
    this.asset = service.getEmptyAsset();
  }
  private getAsset(id: string) {
    this.service.getAsset(id).subscribe((asset) => {
      this.asset = asset;
    });
  }
  ngOnInit(): void {
    this.activeRoute.paramMap.subscribe((routeMap) => {
      const assetId = routeMap.get('assetId');
      this.isNewAsset = assetId === 'new';
      if (this.isNewAsset) {
        this.asset = this.service.getEmptyAsset();
      } else {
        if (assetId !== null) {
          this.getAsset(assetId);
        }
      }
    });
  }
  protected onSubmit() {
    if (this.isNewAsset) {
      this.service.addAsset(this.asset).subscribe((result) => {
        this.isError = result === undefined;
        if (!this.isError) {
          this.router.navigate(['assets']);
        }
      });
    } else {
      this.service.updateAsset(this.asset).subscribe((result) => {
        this.isError = result === undefined;
        if (!this.isError) {
          this.router.navigate(['assets']);
        }
      });
    }
  }
}
