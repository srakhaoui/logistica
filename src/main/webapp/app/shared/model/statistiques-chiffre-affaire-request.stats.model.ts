import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';
import { Moment } from 'moment';

export interface IStatistiquesChiffreAffaireRequest {
  societeId?: number;
  produitId?: number;
  matricule?: string;
  trajetId?: number;
  typeLivraison?: TypeLivraison;
  dateDebut?: Moment;
  dateFin?: Moment;
  withTotalChiffreAffaire?: boolean;
  withEvolutionChiffreAffaire?: boolean;
  withRepartitionParTypeLivraison?: boolean;
  withRepartitionParSocieteFacturation?: boolean;
  withRepartitionParProduit?: boolean;
  withRepartitionParTrajet?: boolean;
  withRepartitionParMatricule?: boolean;
}
