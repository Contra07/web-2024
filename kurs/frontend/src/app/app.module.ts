import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';

import { AssetListComponent } from './Components/Assets/asset-list/asset-list.component';
import { AssetShowComponent } from './Components/Assets/asset-show/asset-show.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AppComponent } from './Components/Main/app.component';
import { AssetEditComponent } from './Components/Assets/asset-edit/asset-edit.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthInterceptor } from './Services/AuthInterceptor';
import { LoginComponent } from './Components/Login/login.component';
import { ItemListComponent } from './Components/Items/item-list/item-list.component';
import { ItemsGroupedComponent } from './Components/Items/items-grouped/items-grouped.component';
import { RecipeListComponent } from './Components/Recipe/recipe-list/recipe-list.component';
import { RecipeShowComponent } from './Components/Recipe/recipe-show/recipe-show.component';
import { MenuComponent } from './Components/Menu/menu.component';
import { RecipeAssetListComponent } from './Components/Recipe/recipe-asset-list/recipe-asset-list.component';
import { RecipeAssetShowComponent } from './Components/Recipe/recipe-asset-show/recipe-asset-show.component';
import { CraftTableComponent } from './Components/Craft/craft-table/craft-table.component';
import { CraftTableCellComponent } from './Components/Craft/craft-table-cell/craft-table-cell.component';
import { RecipeEditComponent } from './Components/Recipe/recipe-edit/recipe-edit.component';
import { TradeItemShowComponent } from './Components/Trade/trade-item-show/trade-item-show.component';
import { TradeComponent } from './Components/Trade/trade/trade.component';
import { TradeItemListComponent } from './Components/Trade/trade-item-list/trade-item-list.component';
import { TradeCellComponent } from './Components/Trade/trade-cell/trade-cell.component';
import { TradeTableComponent } from './Components/Trade/trade-table/trade-table.component';
@NgModule({
  declarations: [
    AppComponent,
    AssetListComponent,
    AssetShowComponent,
    AssetEditComponent,
    LoginComponent,
    ItemListComponent,
    ItemsGroupedComponent,
    RecipeListComponent,
    RecipeShowComponent,
    MenuComponent,
    RecipeAssetListComponent,
    RecipeAssetShowComponent,
    CraftTableComponent,
    CraftTableCellComponent,
    RecipeEditComponent,
    TradeItemShowComponent,
    TradeComponent,
    TradeItemListComponent,
    TradeCellComponent,
    TradeTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    NgbModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
