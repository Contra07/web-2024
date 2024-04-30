import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ItemWithAsset } from '../../../Types/ItemWithAsset';

@Component({
  selector: 'app-trade-item-show',
  templateUrl: './trade-item-show.component.html',
  styleUrl: './trade-item-show.component.css',
})
export class TradeItemShowComponent {
  @Input() item: ItemWithAsset | null = null;
  @Input() isSelected: boolean = false;
  @Output() public selectItemEvent: EventEmitter<string> =
    new EventEmitter<string>();
  @Output() public deselectItemEvent: EventEmitter<string> =
    new EventEmitter<string>();
  onSelect() {
    if (!this.isSelected) {
      this.selectItemEvent.emit(this.item?.id);
    }
    else{
      this.deselectItemEvent.emit(this.item?.id);
    }
  }
}
