import { Moment } from 'moment';

export interface IRecapitulatifVenteClient {
  client?: string;
  dateBonLivraison?: Moment;
  numeroBonLivraison?: number;
  matricule?: string;
  produit?: string;
  totalQuantiteeVendue?: number;
  totalPrixVente?: number;
}

export class RecapitulatifVenteClient implements IRecapitulatifVenteClient {
  constructor(public client?: string, public dateBonLivraison?: Moment, public numeroBonLivraison?: number, public matricule?: string, public produit?: string, public totalQuantiteeVendue?: number, public totalPrixVente?: number) {}
}
