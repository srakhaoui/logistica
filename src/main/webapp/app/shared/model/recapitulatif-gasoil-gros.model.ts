import { IRecapitulatifGasoilGrosTransaction } from 'app/shared/model/recapitulatif-gasoil-gros-transaction.model';

export interface IRecapitulatifGasoilGros {
  recapitulatifGasoilTransactionGrosList?: IRecapitulatifGasoilGrosTransaction[];
  totalQuantiteAchetee?: number;
  totalPrixAchat?: number;
  totalQuantiteVendue?: number;
  totalPrixVente?: number;
  margeGlobale?: number;
  tauxMarge?: number;
}
