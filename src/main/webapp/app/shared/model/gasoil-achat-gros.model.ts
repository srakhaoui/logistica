import { Moment } from 'moment';
import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';
import { ISociete } from 'app/shared/model/societe.model';
import { ICarburant } from 'app/shared/model/carburant.model';
import { UniteGasoilGros } from 'app/shared/model/enumerations/unite-gasoil-gros.model';

export interface IGasoilAchatGros {
  id?: number;
  dateReception?: Moment;
  numeroBonReception?: string;
  description?: string;
  quantity?: number;
  prixUnitaire?: number;
  uniteGasoilGros?: UniteGasoilGros;
  fournisseurGrossiste?: IFournisseurGrossiste;
  acheteur?: ISociete;
  carburant?: ICarburant;
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
    public fournisseurGrossiste?: IFournisseurGrossiste,
    public acheteur?: ISociete,
    public carburant?: ICarburant
  ) {}
}
