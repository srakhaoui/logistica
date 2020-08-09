export interface IRecapitulatifChargesGasoil {
  societeId?: number;
  societe?: string;
  nomTransporteur?: string;
  prenomTransporteur?: string;
  matricule?: string;
  totalQuantiteEnLitre?: number;
  moyennePrixDuLitre?: number;
  totalPrixGasoil?: number;
  kilometrageParcouru?: number;
  margeGasoil?: number;
  totalCommissionChauffeur?: number;
}

export class RecapitulatifChargesGasoil implements IRecapitulatifChargesGasoil {
  constructor(public societeId?: number, public societe?: string, public nomTransporteur?: string, public prenomTransporteur?: string,   public matricule?: string, public totalQuantiteEnLitre?: number, public moyennePrixDuLitre?: number, public totalPrixGasoil?: number, public kilometrageParcouru?: number, public margeGasoil?: number, public totalCommissionChauffeur?: number) {}
}
