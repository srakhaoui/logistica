import { Moment } from 'moment';

export interface IRecapitulatifGasoilGrosTransaction {
  fournisseur?: string;
  acheteur?: string;
  carburant?: string;
  dateReception?: Moment;
  quantiteAchetee?: number;
  uniteAchat?: string;
  prixAchatUnitaire?: number;
  prixAchatTotal?: number;
  client?: string;
  transporteur?: string;
  dateVente?: Moment;
  quantiteVendue?: number;
  uniteVente?: string;
  prixVenteUnitaire?: number;
  prixVenteTotal?: number;
  margeGlobale?: number;
  tauxMarge?: number;
}
