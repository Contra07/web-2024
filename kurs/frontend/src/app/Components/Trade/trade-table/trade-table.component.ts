import { Component, Input, OnInit } from '@angular/core';
import { ItemToTradeWithAsset } from '../../../Types/ItemToTradeWithAsset';
import { RecipeWithResult } from '../../../Types/RecipeWithResult';
import { RecipeService } from '../../../Services/recipe.service';
import { Router } from '@angular/router';
import { TradeService } from '../../../Services/trade.service';
import { ItemService } from '../../../Services/item.service';
import { ItemWithAsset } from '../../../Types/ItemWithAsset';
import { AccountService } from '../../../Services/account.service';

@Component({
  selector: 'app-trade-table',
  templateUrl: './trade-table.component.html',
  styleUrl: './trade-table.component.css',
})
export class TradeTableComponent implements OnInit {
  @Input() itemBuffer: ItemWithAsset | null = null;
  private itemsToTrade: ItemToTradeWithAsset[] = [];
  private recipeResult: RecipeWithResult | null = null;
  private accountId: string | null = null;

  protected itemResultToShow: ItemToTradeWithAsset | null = null;
  protected itemsToTradeToShow: ItemToTradeWithAsset[][] = [];
  constructor(
    private accountService: AccountService,
    private recipeService: RecipeService,
    private router: Router,
    private tradeService: TradeService,
    private itemService: ItemService
  ) {}

  ChangeItems(): void {
    this.itemsToTradeToShow = [
      [
        this.getItemToShow(0, 0),
        this.getItemToShow(0, 1),
        this.getItemToShow(0, 2),
      ],
      [
        this.getItemToShow(1, 0),
        this.getItemToShow(1, 1),
        this.getItemToShow(1, 2),
      ],
      [
        this.getItemToShow(2, 0),
        this.getItemToShow(2, 1),
        this.getItemToShow(2, 2),
      ],
    ];
    if (this.accountId != null) {
      if (this.recipeResult != null) {
        this.itemResultToShow = {
          id: '',
          item: {
            asset: this.recipeResult.asset,
            id: '',
            accountId: this.accountId,
            createdOn: new Date(),
          },
          quantity: -1,
          column: -1,
          row: -1,
        };
      } else {
        this.itemResultToShow = this.tradeService.getEmptyItemToTrade(
          this.accountId,
          -1,
          -1
        );
      }
    }
  }
  ngOnInit(): void {
    if (this.accountService.getIdToken() != null) {
      this.accountId = this.accountService.getIdToken();
    }
    this.ChangeItems();
  }

  private getItemToShow(column: number, row: number): ItemToTradeWithAsset {
    let item = this.itemsToTrade.find((item) => {
      return item.column == column && item.row == row;
    });
    if (item != null) {
      return item;
    }
    return this.tradeService.getEmptyItemToTrade(
      this.accountId as string,
      column,
      row
    );
  }

  onDeleteItemToTradeEvent(item: ItemToTradeWithAsset | null) {
    if (item != null) {
      let itemToTrade = this.itemsToTrade.find((t) => {
        return t.column == item.column && t.row == item.row;
      });
      if (itemToTrade) {
        let newItemsToTrade = this.itemsToTrade.filter((t) => {
          return t.column != item.column || t.row != item.row;
        });
        this.itemsToTrade = newItemsToTrade;
      }
      this.checkTrade();
      this.ChangeItems();
    }
  }

  onSelectItemToTradeEvent(item: ItemToTradeWithAsset | null) {
    if (this.itemBuffer != null) {
      if (item != null) {
        let itemToTrade = this.itemsToTrade.find((t) => {
          return t.column == item.column && t.row == item.row;
        });
        if (itemToTrade) {
          itemToTrade.item = this.itemBuffer;
        }
        else{
          item.item = this.itemBuffer;
          item.id = '';
          this.itemsToTrade.push(item);
        }
      }
      this.checkTrade();
      this.ChangeItems();
    }
  }

  OnSaveClick() {
    if (
      this.itemsToTrade.length != 0 &&
      this.recipeResult != null &&
      this.accountId != null
    ) {
      this.tradeService
        .postTrade(this.accountId, this.itemsToTrade)
        .subscribe((result) => {
          if (result) {
            this.router.navigate(['recipes']);
          }
        });
    }
  }

  private checkTrade() {
    if (this.itemsToTrade.length != 0 && this.accountId != null) {
      this.tradeService
        .postCheckTrade(this.itemsToTrade)
        .subscribe((result) => {
          if (result) {
            this.recipeResult = this.recipeService.getEmptyRecipe(result);
            this.ChangeItems();
          }
          else{
            this.recipeResult = null;
            this.ChangeItems();
          }
        });
    }
  }
}
