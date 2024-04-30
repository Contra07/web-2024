import { Component } from '@angular/core';
import { RecipeWithResult } from '../../../Types/RecipeWithResult';
import { RecipeService } from '../../../Services/recipe.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrl: './recipe-list.component.css',
})
export class RecipeListComponent {
  protected recipes: RecipeWithResult[] = [];
  protected recipesFiltred: RecipeWithResult[] = [];
  constructor(private service: RecipeService) {}
  private getRecipes(): void {
    this.service
      .getRecipes()
      .subscribe(
        (recipes) => ((this.recipes = recipes), (this.recipesFiltred = recipes))
      );
  }
  ngOnInit(): void {
    this.getRecipes();
  }
  deleteRecipe(id: string) {
    if (id != null) {
      this.service.deleteRecipe(id).subscribe((result) => {
        this.getRecipes();
      });
    }
  }
  onEnter(value: string) {
    if (value === null || value.match(/^ *$/) !== null) {
      this.recipesFiltred = this.recipes;
    } else {
      this.recipesFiltred = this.recipes.filter((recipe) => {
        return recipe.asset.name.toUpperCase().includes(value.toUpperCase());
      });
    }
  }
}
