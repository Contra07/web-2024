import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RecipeWithResult } from '../../../Types/RecipeWithResult';
import { Asset } from '../../../Types/Asset';
import { RecipeService } from '../../../Services/recipe.service';
import { ActivatedRoute } from '@angular/router';
import { AssetIn } from '../../../Types/AssetIn';
import { AssetInService } from '../../../Services/asset-in.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-recipe-edit',
  templateUrl: './recipe-edit.component.html',
  styleUrl: './recipe-edit.component.css',
})
export class RecipeEditComponent {
  protected isNewRecipe: boolean = false;
  protected recipeId: string | null = null;
  protected assetBuffer: Asset | null = null;

  constructor(private activeRoute: ActivatedRoute) {}
  ngOnInit(): void {
    this.activeRoute.paramMap.subscribe((routeMap) => {
      const recipeId = routeMap.get('recipeId');
      this.isNewRecipe = recipeId === 'new';
      if (this.isNewRecipe) {
        this.recipeId = null;
      } else {
        if (recipeId != null) {
          this.recipeId = recipeId;
        }
      }
    });
  }

  onSelectAssetEvent(asset: Asset) {
    if (this.assetBuffer == null) {
      this.assetBuffer = asset;
    } else {
      if (this.assetBuffer.id == asset.id) {
        this.assetBuffer = null;
      } else {
        this.assetBuffer = asset;
      }
    }
  }

  bufferChangedEvent(asset: Asset | null) {
    this.assetBuffer = asset;
  }
}
