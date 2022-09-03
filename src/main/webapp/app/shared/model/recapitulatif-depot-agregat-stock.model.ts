import { Unite } from 'app/shared/model/enumerations/unite.model';
import { IStockDepot } from 'app/shared/model/stock-depot.model';

export interface IRecapitulatifDepotAgregatStock {

  nom?: string;
  initialStock?: number;
  uniteIntialStock?: Unite;
  achats?: IStockDepot[];
  ventes?: IStockDepot[];
  transfertsEntrants?: IStockDepot[];
  transfertsSortants?: IStockDepot[];
  stockByUnite?: Map<string, number>;
}

export class RecapitulatifDepotAgregatStock implements IRecapitulatifDepotAgregatStock {
  constructor(public nom?: string, initialStock?: number, uniteIntialStock?: Unite, achats?: IStockDepot[], ventes?: IStockDepot[], transfertsEntrants?: IStockDepot[], transfertsSortants?: IStockDepot[], stockByUnite?: Map<string, number>) {}
}
