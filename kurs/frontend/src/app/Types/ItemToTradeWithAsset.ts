import { ItemWithAsset } from "./ItemWithAsset";

export interface ItemToTradeWithAsset {
  id: string;
  item: ItemWithAsset
  column: number;
  row: number;
  quantity: number
}
