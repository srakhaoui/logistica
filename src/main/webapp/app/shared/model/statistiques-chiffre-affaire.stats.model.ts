import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';
import { Courbe } from 'app/shared/model/courbe.stats.model';
import { Moment } from 'moment';

export interface IStatistiquesChiffreAffaire {
  totalChiffreAffaire?: number;
  evolution?: Courbe;
  chiffreAffaireParTrajet?: Courbe;
  chiffreAffaireParProduit?: Courbe;
  chiffreAffaireParSociete?: Courbe;
  chiffreAffaireParMatricule?: Courbe;
  chiffreAffaireParType?: Courbe;
}

export class StatistiquesChiffreAffaire implements IStatistiquesChiffreAffaire {
  constructor(
     public totalChiffreAffaire?: number,
     public evolution?: Courbe,
     public chiffreAffaireParTrajet?: Courbe,
     public chiffreAffaireParProduit?: Courbe,
     public chiffreAffaireParSociete?: Courbe,
     public chiffreAffaireParMatricule?: Courbe,
     public chiffreAffaireParType?: Courbe
  ) {}
}
