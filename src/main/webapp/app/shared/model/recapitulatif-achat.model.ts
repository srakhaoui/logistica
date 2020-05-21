import { Moment } from 'moment';

export interface IRecapitulatifAchat {
  livraisonId?: number;
  hasBonCommande?: boolean;
  dateBonCommande?: Moment;
  numeroBonCommande?: number;
  codeProduit?: string;
  totalQuantiteAchetee?: number;
  totalQuantiteConvertie?: number;
  totalPrixAchat?: number;
}

export class RecapitulatifAchat implements IRecapitulatifAchat {
  constructor(public dateBonCommande?: Moment, public numeroBonCommande?: number, public codeProduit?: string, public totalQuantiteAchetee?: number, public totalQuantiteConvertie?: number, public totalPrixAchat?: number) {}
}
