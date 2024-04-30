import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AssetIn } from '../../../Types/AssetIn';

@Component({
  selector: 'app-craft-table-cell',
  templateUrl: './craft-table-cell.component.html',
  styleUrl: './craft-table-cell.component.css',
})
export class CraftTableCellComponent {
  @Output() public selectAssetInEvent: EventEmitter<AssetIn | null> =
    new EventEmitter<AssetIn | null>();
  @Output() public deleteAssetInEvent: EventEmitter<AssetIn | null> =
    new EventEmitter<AssetIn | null>();
  @Input() asset: AssetIn | null = null;
  onSelectClick() {
    this.selectAssetInEvent.emit(this.asset);
  }
  onDeleteClick() {
    this.deleteAssetInEvent.emit(this.asset);
  }
}
