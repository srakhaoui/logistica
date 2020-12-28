import { Moment } from 'moment';

export interface IStatistiquesTauxRentabiliteRequest {
  societeId?: number;
  matricule?: string;
  dateDebut?: Moment;
  dateFin?: Moment;
}
