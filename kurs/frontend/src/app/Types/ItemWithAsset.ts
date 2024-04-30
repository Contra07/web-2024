import { Asset } from "./Asset";

export interface ItemWithAsset {
  id: string;
  accountId: string;
  asset: Asset
  createdOn: Date;
}
