import { Moment } from 'moment';

export interface IRecapitulatifGasoilGros {
  client?: string;
  carburant?: string;
  quantiteVendue?: number;
  prixUnitaireVente?: number;
  uniteVente?: string;
  dateVente?: Moment;
  prixTotalVente?: number;
}

export class RecapitulatifGasoilGros implements IRecapitulatifGasoilGros {
  constructor(public client?: string, public carburant?: string, public quantiteVendue?: number, public prixUnitaireVente?: number,   public uniteVente?: string, public dateVente?: Moment, public prixTotalVente?: number) {}
}
