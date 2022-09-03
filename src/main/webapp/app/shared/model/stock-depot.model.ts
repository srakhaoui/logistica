import { Unite } from 'app/shared/model/enumerations/unite.model';

export interface IStockDepot {
    depot?: string;
    unite?: Unite;
    total?: number;
}

export class StockDepot implements IStockDepot {
  constructor(public depot?: string, public unite?: Unite, public total?: number){}
}
