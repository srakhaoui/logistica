import { Courbe } from 'app/shared/model/courbe.stats.model';
import { Moment } from 'moment';

export interface IStatistiquesTauxConsommation {
  evolutionLitrage?: Courbe;
  evolutionKilometrage?: Courbe;
  evolutionTauxConsommation?: Courbe;
  tauxConsommationParMatricule?: Courbe;
  litrageTotal?: number;
  kilometrageTotal?: number;
  tauxConsommation?: number;
}

export class StatistiquesTauxRentabilite implements IStatistiquesTauxConsommation {
  constructor(
     public evolutionLitrage?: Courbe,
     public evolutionKilometrage?: Courbe,
     public evolutionTauxConsommation?: Courbe,
     public tauxConsommationParMatricule?: Courbe,
     public litrageTotal?: number,
     public kilometrageTotal?: number,
     public tauxConsommation?: number
  ) {}
}
