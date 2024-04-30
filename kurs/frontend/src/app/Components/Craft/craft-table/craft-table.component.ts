import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  input,
} from '@angular/core';
import { AssetIn } from '../../../Types/AssetIn';
import { RecipeWithResult } from '../../../Types/RecipeWithResult';
import { AssetInService } from '../../../Services/asset-in.service';
import { RecipeService } from '../../../Services/recipe.service';
import { forkJoin } from 'rxjs';
import { Asset } from '../../../Types/Asset';
import { Router } from '@angular/router';

@Component({
  selector: 'app-craft-table',
  templateUrl: './craft-table.component.html',
  styleUrl: './craft-table.component.css',
})
export class CraftTableComponent implements OnInit {
  @Input() retcipeId: string | null = null;
  @Input() assetBuffer: Asset | null = null;
  @Input() isNew: boolean = false;
  private assetIns: AssetIn[] = [];
  private recipeResult: RecipeWithResult | null = null;

  protected assetResultToShow: AssetIn | null = null;
  protected assetInsToShow: AssetIn[][] = [];
  constructor(
    private assetInService: AssetInService,
    private recipeService: RecipeService,
    private router: Router
  ) {}

  ChangeAssets(): void {
    this.assetInsToShow = [
      [
        this.getAssetToShow(0, 0),
        this.getAssetToShow(0, 1),
        this.getAssetToShow(0, 2),
      ],
      [
        this.getAssetToShow(1, 0),
        this.getAssetToShow(1, 1),
        this.getAssetToShow(1, 2),
      ],
      [
        this.getAssetToShow(2, 0),
        this.getAssetToShow(2, 1),
        this.getAssetToShow(2, 2),
      ],
    ];
    if (this.recipeResult != null) {
      this.assetResultToShow = {
        id: '',
        asset: this.recipeResult?.asset,
        recipeId: '',
        quantity: -1,
        column: -1,
        row: -1,
      };
    } else {
      this.assetResultToShow = this.assetInService.getEmptyAsset(-1, -1);
    }
  }
  ngOnInit(): void {
    if (!this.isNew && this.retcipeId != null) {
      this.getAssetIns(this.retcipeId);
    }
    else{
      this.ChangeAssets();
    }

  }

  getAssetIns(id: string) {
    forkJoin([
      this.assetInService.getAssetIns(id),
      this.recipeService.getRecipe(id),
    ]).subscribe((result) => {
      this.assetIns = result[0];
      this.recipeResult = result[1];
      this.ChangeAssets();
    });
  }

  private getAssetToShow(column: number, row: number): AssetIn {
    let asset = this.assetIns.find((asset) => {
      return asset.column == column && asset.row == row;
    });
    if (asset != null) {
      return asset;
    }
    return this.assetInService.getEmptyAsset(column, row);
  }

  onDeleteAssetInEvent(asset: AssetIn | null) {
    if (asset != null) {
      let assetIn = this.assetIns.find((a) => {
        return a.column == asset.column && a.row == asset.row;
      });
      if (assetIn) {
        let newAssetIn = this.assetIns.filter((a) => {
          return a.column != asset.column || a.row != asset.row;
        });
        this.assetIns = newAssetIn;
      } else {
        if (asset.column == -1 && asset.row == -1) {
          if (this.recipeResult != null) {
            this.recipeResult = null;
          }
        }
      }
      this.ChangeAssets();
    }
  }

  onSelectAssetInEvent(asset: AssetIn | null) {
    if (this.assetBuffer != null) {
      if (asset != null) {
        let assetIn = this.assetIns.find((a) => {
          return a.column == asset.column && a.row == asset.row;
        });
        if (assetIn) {
          assetIn.asset = this.assetBuffer;
        } else {
          if (asset.column == -1 && asset.row == -1) {
            if (this.recipeResult != null) {
              this.recipeResult.asset = this.assetBuffer;
            } else {
              this.recipeResult = this.recipeService.getEmptyRecipe(
                this.assetBuffer
              );
            }
          } else {
            if (this.recipeResult != null) {
              asset.recipeId = this.recipeResult?.id;
              asset.asset = this.assetBuffer;
              asset.id = '';
            } else {
              asset.recipeId = '';
              asset.asset = this.assetBuffer;
              asset.id = '';
            }
            this.assetIns.push(asset);
          }
        }
      }
      this.ChangeAssets();
    }
  }

  OnSaveClick() {
    if (this.assetIns.length != 0 && this.recipeResult != null) {
      if(this.isNew){
        this.recipeService
          .postRecipe(this.assetIns, this.recipeResult.asset)
          .subscribe((result) => {
            if (result) {
              this.router.navigate(['recipes']);
            }
          });
      }
      else{
        if(this.retcipeId != null){
          this.recipeService
            .putRecipe(this.retcipeId, this.assetIns, this.recipeResult)
            .subscribe((result) => {
              if (result) {
                this.router.navigate(['recipes']);
              }
            });
        }
      }
    }
  }
}
