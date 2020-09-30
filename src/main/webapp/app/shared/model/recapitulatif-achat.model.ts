import { Moment } from 'moment';

export interface IRecapitulatifAchat {
  livraisonId?: number;
  dateBonLivraison?: Moment;
  numeroBonLivraison?: number;
  hasBonCommande?: boolean;
  dateBonCommande?: Moment;
  numeroBonCommande?: number;
  nomFournisseur?: string;
  codeProduit?: string;
  matricule?: string;
  totalQuantiteAchetee?: number;
  totalQuantiteConvertie?: number;
  totalPrixAchat?: number;
}

export class RecapitulatifAchat implements IRecapitulatifAchat {
  constructor(public dateBonLivraison?: Moment, public numeroBonLivraison?: number, public dateBonCommande?: Moment, public numeroBonCommande?: number, public nomFournisseur?: string, public codeProduit?: string, public matricule?: string, public totalQuantiteAchetee?: number, public totalQuantiteConvertie?: number, public totalPrixAchat?: number) {}
}
