import { Component, OnInit } from '@angular/core';
import { ItemsCounted } from '../../../Types/ItemsCounted';
import { ItemService } from '../../../Services/item.service';
import { AccountService } from '../../../Services/account.service';

@Component({
  selector: 'app-items-grouped',
  templateUrl: './items-grouped.component.html',
  styleUrl: './items-grouped.component.css',
})
export class ItemsGroupedComponent implements OnInit {
  protected items: ItemsCounted[] = [];
  protected itemsFiltred: ItemsCounted[] = [];
  constructor(
    private service: ItemService,
    private accountService: AccountService
  ) {}
  private getItemList(): void {
    const id = this.accountService.getIdToken();
    if (id != null) {
      this.service
        .getItemsCounted(id)
        .subscribe(
          (items) => ((this.items = items), (this.itemsFiltred = items))
        );
    }
  }

  ngOnInit(): void {
    this.getItemList();
  }
  onEnter(value: string): void {
    if (value === null || value.match(/^ *$/) !== null) {
      this.itemsFiltred = this.items;
    } else {
      this.itemsFiltred = this.items.filter((item) => {
        return item.asset.name.toUpperCase().includes(value.toUpperCase());
      });
    }
  }
  onGetRandomItem() {
    const id = this.accountService.getIdToken();
    if (id != null) {
      this.service.postRandomItem(id).subscribe((result) =>{
        this.getItemList();
      });
    }
  }
}
