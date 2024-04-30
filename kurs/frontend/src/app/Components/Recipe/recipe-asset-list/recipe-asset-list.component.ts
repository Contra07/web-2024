import { Component, EventEmitter, Input, OnInit, Output, input } from '@angular/core';
import { Asset } from '../../../Types/Asset';
import { AssetService } from '../../../Services/asset.service';

@Component({
  selector: 'app-recipe-asset-list',
  templateUrl: './recipe-asset-list.component.html',
  styleUrl: './recipe-asset-list.component.css',
})
export class RecipeAssetListComponent implements OnInit {
  protected assets: Asset[] = [];
  protected selectedAsset: Asset | null = null;

  @Output() public bufferChangedEvent: EventEmitter<Asset | null> =
    new EventEmitter<Asset | null>();
  constructor(private assetService: AssetService) {}
  ngOnInit(): void {
    this.getAssets();
  }

  getAssets() {
    this.assetService.getAssets().subscribe((result) => {
      this.assets = result;
    });
  }

  onSelectAssetEvent(id: string) {
    if (id != null) {
      this.assetService.getAsset(id).subscribe((result) => {
        if (result != undefined) {
          this.selectedAsset = result;
          this.bufferChangedEvent.emit(this.selectedAsset);
        }
      });
    }
  }

  onDeselectAssetEvent(id: string) {
    this.selectedAsset = null;
    this.bufferChangedEvent.emit(this.selectedAsset);
  }
}
