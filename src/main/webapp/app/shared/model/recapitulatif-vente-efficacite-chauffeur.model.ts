export interface IRecapitulatifVenteEfficaciteChauffeur {
  id ?: number;
  societe ?: string;
  prenomChauffeur?: string;
  nomChauffeur?: string;
  nombreTrajets?: number;
  totalComission?: number;
  totalPrixVente?: number;
}

export class RecapitulatifVenteEfficaciteChauffeur implements IRecapitulatifVenteEfficaciteChauffeur {
  constructor(public id?: number, public prenomChauffeur?: string, public nomChauffeur?: string, public nombreTrajets?: number, public totalComission?: number, public totalPrixVente?: number) {}
}
