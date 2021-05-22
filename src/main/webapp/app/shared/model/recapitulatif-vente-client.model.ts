import { Moment } from 'moment';

export interface IRecapitulatifVenteClient {
  livraisonId?: number;
  client?: string;
  societeFacturation?: string;
  type?: string;
  hasBonLivraison?: boolean;
  dateBonLivraison?: Moment;
  numeroBonLivraison?: string;
  matricule?: string;
  produit?: string;
  totalQuantiteeVendue?: number;
  uniteVente?: string;
  totalPrixVente?: number;
  facture?: boolean;
  fournisseur?: string;
  quantiteAchetee?: number;
  uniteAchat?: string;
  prixTotalAchat?: number;
}

export class RecapitulatifVenteClient implements IRecapitulatifVenteClient {
  constructor(public client?: string, public dateBonLivraison?: Moment, public numeroBonLivraison?: string, public matricule?: string, public produit?: string, public totalQuantiteeVendue?: number, public uniteVente?: string, public totalPrixVente?: number, public facture?: boolean, public fournisseur?: string, public quantiteAchetee?: number, public uniteAchat?: string, public prixTotalAchat ?: number) {}
}
