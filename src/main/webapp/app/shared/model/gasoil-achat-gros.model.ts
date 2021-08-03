import { Moment } from 'moment';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { ISociete } from 'app/shared/model/societe.model';
import { IProduit } from 'app/shared/model/produit.model';

export interface IGasoilAchatGros {
  id?: number;
  dateReception?: Moment;
  numeroBonReception?: string;
  description?: string;
  quantity?: number;
  prixUnitaire?: number;
  fournisseur?: IFournisseur;
  transporteur?: ISociete;
  produit?: IProduit;
}

export class GasoilAchatGros implements IGasoilAchatGros {
  constructor(
    public id?: number,
    public dateReception?: Moment,
    public numeroBonReception?: string,
    public description?: string,
    public quantity?: number,
    public prixUnitaire?: number,
    public fournisseur?: IFournisseur,
    public transporteur?: ISociete,
    public produit?: IProduit
  ) {}
}
