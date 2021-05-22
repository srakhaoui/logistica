import { ITransporteur } from 'app/shared/model/transporteur.model';
import { ISociete } from 'app/shared/model/societe.model';
import { Moment } from 'moment';

export interface IGasoil {
  id?: number;
  numeroBonGasoil?: string;
  quantiteEnLitre?: number;
  prixDuLitre?: number;
  prixTotalGasoil?: number;
  kilometrageInitial?: number;
  kilometrageFinal?: number;
  kilometrageParcouru?: number;
  transporteur?: ITransporteur;
  societeFacturation?: ISociete;
  dateBonGasoil?: Moment;
  dateSaisie?: Moment;
}

export class Gasoil implements IGasoil {
  constructor(
    public id?: number,
    public numeroBonGasoil?: string,
    public quantiteEnLitre?: number,
    public prixDuLitre?: number,
    public prixTotalGasoil?: number,
    public kilometrageInitial?: number,
    public kilometrageFinal?: number,
    public kilometrageParcouru?: number,
    public transporteur?: ITransporteur,
    public societeFacturation?: ISociete,
    public dateBonGasoil?: Moment,
    public dateSaisie?: Moment
  ) {}
}
