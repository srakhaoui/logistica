import { Unite } from './enumerations/unite.model';

export interface IRecapitulatifVenteFacturation {
  moisBonLivraison?: number;
  produit?: string;
  uniteVente ?: Unite;
  totalQuantiteeVendue?: number;
  totalPrixVente?: number;
}

export class RecapitulatifVenteFacturation implements IRecapitulatifVenteFacturation {
  constructor(public moisBonLivraison?: number, public produit?: string, public uniteVente?: Unite, public totalQuantiteeVendue?: number, public totalPrixVente?: number) {}
}
