import { ITransporteur } from 'app/shared/model/transporteur.model';

export interface IGasoil {
  id?: number;
  societe?: string;
  numeroBonGasoil?: number;
  quantiteEnLitre?: number;
  prixDuLitre?: number;
  prixTotalGasoil?: number;
  kilometrageInitial?: number;
  kilometrageFinal?: number;
  kilometrageParcouru?: number;
  transporteur?: ITransporteur;
}

export class Gasoil implements IGasoil {
  constructor(
    public id?: number,
    public societe?: string,
    public numeroBonGasoil?: number,
    public quantiteEnLitre?: number,
    public prixDuLitre?: number,
    public prixTotalGasoil?: number,
    public kilometrageInitial?: number,
    public kilometrageFinal?: number,
    public kilometrageParcouru?: number,
    public transporteur?: ITransporteur
  ) {}
}
