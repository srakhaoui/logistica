import { Moment } from 'moment';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { ISociete } from 'app/shared/model/societe.model';
import { IProduit } from 'app/shared/model/produit.model';
import { UniteGasoilGros } from 'app/shared/model/enumerations/unite-gasoil-gros.model';

export interface IGasoilAchatGros {
  id?: number;
  dateReception?: Moment;
  numeroBonReception?: string;
  description?: string;
  quantity?: number;
  prixUnitaire?: number;
  uniteGasoilGros?: UniteGasoilGros;
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
    public uniteGasoilGros?: UniteGasoilGros,
    public fournisseur?: IFournisseur,
    public transporteur?: ISociete,
    public produit?: IProduit
  ) {}
}
