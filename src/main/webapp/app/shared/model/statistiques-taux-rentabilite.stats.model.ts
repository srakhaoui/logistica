import { Courbe } from 'app/shared/model/courbe.stats.model';
import { Moment } from 'moment';

export interface IStatistiquesTauxRentabilite {
  evolutionChiffreAffaire?: Courbe;
  evolutionChargeGasoil?: Courbe;
  evolutionTauxRentabilite?: Courbe;
  tauxRentabiliteParMatricule?: Courbe;
  chiffreAffaireTotal?: number;
  chargeGasoilTotal?: number;
  tauxRentabilite?: number;
}

export class StatistiquesTauxRentabilite implements IStatistiquesTauxRentabilite {
  constructor(
     public evolutionChiffreAffaire?: Courbe,
     public evolutionChargeGasoil?: Courbe,
     public evolutionTauxRentabilite?: Courbe,
     public tauxRentabiliteParMatricule?: Courbe,
     public chiffreAffaireTotal?: number,
     public chargeGasoilTotal?: number,
     public tauxRentabilite?: number
  ) {}
}
