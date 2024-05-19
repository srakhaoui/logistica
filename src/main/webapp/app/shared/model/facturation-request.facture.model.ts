import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';
import { Moment } from 'moment';

export interface IFacturationRequest {
  typeLivraison?: TypeLivraison;
  chantier?: string;
  clientId?: number;
  dateDebut?: Moment;
  dateFin?: Moment;
}
