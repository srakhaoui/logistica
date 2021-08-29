import { IRecapitulatifGasoilGrosTransaction } from 'app/shared/model/recapitulatif-gasoil-gros-transaction.model';

export interface IRecapitulatifGasoilGros {
  recapitulatifGasoilTransactionGrosList?: IRecapitulatifGasoilGrosTransaction[];
  totalQuantiteAchetee?: number;
  totalPrixAchat?: number;
  totalQuantiteVendue?: number;
  totalPrixVente?: number;
  margeGlobale?: number;
  tauxMarge;?: number;
}

/*export class RecapitulatifGasoilGrosVente implements IRecapitulatifGasoilGrosVente {
  constructor(public client?: string, public carburant?: string, public quantite?: number, public prixVenteUnitaire?: number,   public unite?: string, public dateVente?: Moment, public prixVenteTotal?: number) {}
}*/
