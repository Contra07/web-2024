import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Asset } from '../../../Types/Asset';
import { AssetService } from '../../../Services/asset.service';

@Component({
  selector: 'app-asset-show',
  templateUrl: './asset-show.component.html',
  styleUrl: './asset-show.component.css',
})
export class AssetShowComponent implements OnInit {
  @Output() public deleteAssetEvent: EventEmitter<string> =
    new EventEmitter<string>();
  @Input() asset: Asset | null = null;
  constructor(private service: AssetService) {}
  ngOnInit(): void {
    if (this.asset !== null) {
    }
  }
  onDelete() {
    this.deleteAssetEvent.emit(this.asset?.id);
  }
}
