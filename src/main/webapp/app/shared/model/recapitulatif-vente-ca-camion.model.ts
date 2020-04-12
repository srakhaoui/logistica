import { Unite } from './enumerations/unite.model';

export interface IRecapitulatifVenteCaCamion {
  camion?: string;
  uniteVente?: Unite;
  totalQuantiteVendue?: number;
  totalPrixVente?: number;
}

export class RecapitulatifVenteCaCamion implements IRecapitulatifVenteCaCamion {
  constructor(public camion?: string, public uniteVente?: Unite, public totalQuantiteeVendue?: number, public totalPrixVente?: number) {}
}
