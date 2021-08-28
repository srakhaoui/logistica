import { Moment } from 'moment';

export interface IRecapitulatifGasoilGrosAchat {
  fournisseur?: string;
  acheteur?: string;
  carburant?: string;
  dateReception?: Moment;
  quantity?: number;
  unite?: string;
  prixUnitaire?: number;
}

export class RecapitulatifGasoilGrosAchat implements IRecapitulatifGasoilGrosAchat {
  constructor(public fournisseur?: string, public acheteur?: string, public carburant?: string, public dateReception?: Moment, public quantity?: number, public unite?: string, public prixUnitaire?: number) {}
}
