import { Moment } from 'moment';

export interface IRecapitulatifGasoilGrosTransaction {
  fournisseur?: string;
  acheteur?: string;
  carburant?: string;
  dateReception?: Moment;
  quantiteAchetee?: number;
  uniteAchat?: string;
  prixAchatUnitaire?: number;
  prixAchatTotal?: number;;
  client?: string;
  transporteur?: string;
  dateVente?: Moment;;
  quantiteVendue?: number;
  uniteVente?: string;
  prixVenteUnitaire?: number;
  prixVenteTotal?: number;
  margeGlobale?: number;
  tauxMarge?: number;
}

/*export class RecapitulatifGasoilGrosVente implements IRecapitulatifGasoilGrosVente {
  constructor(public client?: string, public carburant?: string, public quantite?: number, public prixVenteUnitaire?: number,   public unite?: string, public dateVente?: Moment, public prixVenteTotal?: number) {}
}*/
