import { Moment } from 'moment';

export interface IRecapitulatifGasoilGros {
  client?: string;
  carburant?: string;
  quantite?: number;
  prixVenteUnitaire?: number;
  unite?: string;
  dateVente?: Moment;
  prixVenteTotal?: number;
}

export class RecapitulatifGasoilGros implements IRecapitulatifGasoilGros {
  constructor(public client?: string, public carburant?: string, public quantite?: number, public prixVenteUnitaire?: number,   public unite?: string, public dateVente?: Moment, public prixVenteTotal?: number) {}
}
