import { Moment } from 'moment';

export interface IRecapitulatifVenteClient {
  livraisonId?: number;
  client?: string;
  societeFacturation?: string;
  type?: string;
  hasBonLivraison?: boolean;
  dateBonLivraison?: Moment;
  numeroBonLivraison?: number;
  matricule?: string;
  produit?: string;
  totalQuantiteeVendue?: number;
  totalPrixVente?: number;
  facture?: boolean;
}

export class RecapitulatifVenteClient implements IRecapitulatifVenteClient {
  constructor(public client?: string, public dateBonLivraison?: Moment, public numeroBonLivraison?: number, public matricule?: string, public produit?: string, public totalQuantiteeVendue?: number, public totalPrixVente?: number, public facture?: boolean) {}
}
