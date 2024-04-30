import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RecipeWithResult } from '../../../Types/RecipeWithResult';
import { RecipeService } from '../../../Services/recipe.service';

@Component({
  selector: 'app-recipe-show',
  templateUrl: './recipe-show.component.html',
  styleUrl: './recipe-show.component.css',
})
export class RecipeShowComponent {
  @Output() public deleteRecipeEvent: EventEmitter<string> =
    new EventEmitter<string>();
  @Input() recipe: RecipeWithResult | null = null;
  constructor(private service: RecipeService) {}
  ngOnInit(): void {
  }
  onDelete() {
    this.deleteRecipeEvent.emit(this.recipe?.id);
  }
}
