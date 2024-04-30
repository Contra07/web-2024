import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AssetIn } from '../../../Types/AssetIn';
import { ItemToTradeWithAsset } from '../../../Types/ItemToTradeWithAsset';

@Component({
  selector: 'app-trade-cell',
  templateUrl: './trade-cell.component.html',
  styleUrl: './trade-cell.component.css',
})
export class TradeCellComponent {
  @Output() public selectItemToTradeEvent: EventEmitter<ItemToTradeWithAsset | null> =
    new EventEmitter<ItemToTradeWithAsset | null>();
  @Output() public deleteItemToTradeEvent: EventEmitter<ItemToTradeWithAsset | null> =
    new EventEmitter<ItemToTradeWithAsset | null>();
  @Input() item: ItemToTradeWithAsset | null = null;
  onSelectClick() {
    this.selectItemToTradeEvent.emit(this.item);
  }
  onDeleteClick() {
    this.deleteItemToTradeEvent.emit(this.item);
  }
}
