import { Component, Input, OnInit } from '@angular/core';
import { ItemService } from '../../../Services/item.service';
import { AccountService } from '../../../Services/account.service';
import { ItemWithAsset } from '../../../Types/ItemWithAsset';
import { Asset } from '../../../Types/Asset';
import { ActivatedRoute, Router } from '@angular/router';
import { AssetService } from '../../../Services/asset.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrl: './item-list.component.css',
})
export class ItemListComponent implements OnInit {
  protected asset: Asset | null = null;
  protected items: ItemWithAsset[] = [];
  constructor(
    private service: ItemService,
    private accountService: AccountService,
    private activeRoute: ActivatedRoute,
    private assetService: AssetService,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.getItems();
  }
  private getItems(): void {
    this.activeRoute.paramMap.subscribe((routeMap) => {
      const assetId = routeMap.get('assetId');
      if (assetId !== null) {
        this.assetService.getAsset(assetId).subscribe((asset) => {
          this.asset = asset;
          const id = this.accountService.getIdToken();
          if (id != null) {
            this.service.getItemsForAsset(id, assetId).subscribe((items) => {
              this.items = items;
              if (this.items.length == 0) {
                this.router.navigateByUrl('/items');
              }
            });
          }
        });
      }
    });
  }
  onDelete(id: string) {
    const accountId = this.accountService.getIdToken();
    if (accountId != null) {
      this.service.deleteItem(accountId, id).subscribe((response) => {
        if (response != 'error') {
          this.getItems();
        }
      });
    }
  }
}
