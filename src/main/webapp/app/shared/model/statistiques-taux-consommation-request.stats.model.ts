import { Moment } from 'moment';

export interface IStatistiquesTauxConsommationRequest {
  societeId?: number;
  matricule?: string;
  dateDebut?: Moment;
  dateFin?: Moment;
}
