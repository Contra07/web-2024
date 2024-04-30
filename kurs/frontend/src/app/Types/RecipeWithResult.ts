import { Asset } from './Asset';
import { AssetIn } from './AssetIn';

export interface RecipeWithResult {
  id: string
  asset: Asset;
  quantity: number;
}
