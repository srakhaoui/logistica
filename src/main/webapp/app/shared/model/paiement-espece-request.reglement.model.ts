import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';
import { Moment } from 'moment';

export interface IReglementEspeceRequest {
  societeId?: number;
  typeLivraison?: TypeLivraison;
  chantier?: string;
  clientId?: number;
  produitId?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
}
