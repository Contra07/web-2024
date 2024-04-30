import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Asset } from '../../../Types/Asset';

@Component({
  selector: 'app-recipe-asset-show',
  templateUrl: './recipe-asset-show.component.html',
  styleUrl: './recipe-asset-show.component.css',
})
export class RecipeAssetShowComponent {
  @Input() asset: Asset | null = null;
  @Input() isSelected: boolean = false;
  @Output() public selectAssetEvent: EventEmitter<string> =
    new EventEmitter<string>();
  @Output() public deselectAssetEvent: EventEmitter<string> =
    new EventEmitter<string>();
  onSelect() {
    if (!this.isSelected) {
      this.selectAssetEvent.emit(this.asset?.id);
    }
    else{
      this.deselectAssetEvent.emit(this.asset?.id);
    }
  }
}
