import { Asset } from "./Asset";

export interface AssetIn {
  id: string;
  asset: Asset;
  recipeId: string;
  quantity: number;
  column: number;
  row: number;
}
