import { Component, EventEmitter, Output } from '@angular/core';
import { ItemService } from '../../../Services/item.service';
import { ItemWithAsset } from '../../../Types/ItemWithAsset';
import { AccountService } from '../../../Services/account.service';
@Component({
  selector: 'app-trade-item-list',
  templateUrl: './trade-item-list.component.html',
  styleUrl: './trade-item-list.component.css',
})
export class TradeItemListComponent {
  protected items: ItemWithAsset[] = [];
  protected selectedItem: ItemWithAsset | null = null;
  private accountId: string | null = null;
  @Output() public bufferChangedEvent: EventEmitter<ItemWithAsset | null> =
    new EventEmitter<ItemWithAsset | null>();
  constructor(
    private itemService: ItemService,
    private accountSevice: AccountService
  ) {}
  ngOnInit(): void {
    this.accountId = this.accountSevice.getIdToken();
    this.getItems();
  }

  getItems() {
    if (this.accountId != null) {
      this.itemService.getItems(this.accountId).subscribe((result) => {
        this.items = result.sort((a,b) => { return a.asset.name.localeCompare(b.asset.name)});
      });
    }
  }

  onSelectItemEvent(id: string) {
    if (id != null && this.accountId != null) {
      this.itemService.getItem(this.accountId, id).subscribe((result) => {
        if (result != undefined) {
          this.selectedItem = result;
          this.bufferChangedEvent.emit(this.selectedItem);
        }
      });
    }
  }

  onDeselectItemEvent(id: string) {
    this.selectedItem = null;
    this.bufferChangedEvent.emit(this.selectedItem);
  }
}
