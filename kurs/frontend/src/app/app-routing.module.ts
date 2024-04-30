import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AssetListComponent } from './Components/Assets/asset-list/asset-list.component';
import { AssetEditComponent } from './Components/Assets/asset-edit/asset-edit.component';
import { guard } from './Services/guard';
import { LoginComponent } from './Components/Login/login.component';
import { MenuComponent } from './Components/Menu/menu.component';
import { AppComponent } from './Components/Main/app.component';
import { ItemsGroupedComponent } from './Components/Items/items-grouped/items-grouped.component';
import { ItemListComponent } from './Components/Items/item-list/item-list.component';
import { RecipeListComponent } from './Components/Recipe/recipe-list/recipe-list.component';
import { RecipeShowComponent } from './Components/Recipe/recipe-show/recipe-show.component';
import { RecipeEditComponent } from './Components/Recipe/recipe-edit/recipe-edit.component';
import { TradeComponent } from './Components/Trade/trade/trade.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'assets', component: AssetListComponent, canActivate: [guard] },
  {
    path: 'assets/:assetId',
    component: AssetEditComponent,
    canActivate: [guard],
  },
  { path: 'items', component: ItemsGroupedComponent, canActivate: [guard] },
  {
    path: 'items/:assetId',
    component: ItemListComponent,
    canActivate: [guard],
  },
  { path: 'recipes', component: RecipeListComponent, canActivate: [guard] },
  {
    path: 'recipes/:recipeId',
    component: RecipeEditComponent,
    canActivate: [guard],
  },
  { path: 'craft', component: TradeComponent, canActivate: [guard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
