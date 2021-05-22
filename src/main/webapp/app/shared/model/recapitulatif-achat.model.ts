import { Moment } from 'moment';

export interface IRecapitulatifAchat {
  livraisonId?: number;
  dateBonLivraison?: Moment;
  numeroBonLivraison?: string;
  hasBonCommande?: boolean;
  dateBonCommande?: Moment;
  numeroBonCommande?: string;
  nomFournisseur?: string;
  codeProduit?: string;
  matricule?: string;
  totalQuantiteAchetee?: number;
  totalQuantiteConvertie?: number;
  totalPrixAchat?: number;
}

export class RecapitulatifAchat implements IRecapitulatifAchat {
  constructor(public dateBonLivraison?: Moment, public numeroBonLivraison?: string, public dateBonCommande?: Moment, public numeroBonCommande?: string, public nomFournisseur?: string, public codeProduit?: string, public matricule?: string, public totalQuantiteAchetee?: number, public totalQuantiteConvertie?: number, public totalPrixAchat?: number) {}
}
