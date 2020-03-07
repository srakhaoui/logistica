export interface IRecapitulatifVenteChauffeur {
  prenomChauffeur?: string;
  nomChauffeur?: string;
  description?: string;
  nombreTrajets?: number;
  commissionTrajet?: number;
  reparationDivers?: number;
  trax?: number;
  balance?: number;
  avance?: number;
  penaliteEse?: number;
  penaliteChfrs?: number;
  fraisEspece?: number;
  retenu?: number;
  totalComission?: number;
}

export class RecapitulatifVenteChauffeur implements IRecapitulatifVenteChauffeur {
  constructor(public prenomChauffeur?: string, public nomChauffeur?: string, public description?: string, public nombreTrajets?: number, public commissionTrajet?: number, public reparationDivers?: number, public trax?: number, balance?: number, avance?: number,  penaliteEse?: number,  penaliteChfrs?: number,  fraisEspece?: number,  retenu?: number,  totalComission?: number) {}
}
