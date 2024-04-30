import { Component } from '@angular/core';
import { ItemWithAsset } from '../../../Types/ItemWithAsset';
import { AccountService } from '../../../Services/account.service';

@Component({
  selector: 'app-trade',
  templateUrl: './trade.component.html',
  styleUrl: './trade.component.css',
})
export class TradeComponent {
  protected itemBuffer: ItemWithAsset | null = null;
  protected accountId: string | null = null;
  constructor(private accountService: AccountService) {}
  ngOnInit(): void {
    this.accountId = this.accountService.getIdToken();
  }

  onSelectItemEvent(item: ItemWithAsset) {
    if (this.itemBuffer == null) {
      this.itemBuffer = item;
    } else {
      if (this.itemBuffer.id == item.id) {
        this.itemBuffer = null;
      } else {
        this.itemBuffer = item;
      }
    }
  }

  bufferChangedEvent(item: ItemWithAsset | null) {
    this.itemBuffer = item;
  }
}
