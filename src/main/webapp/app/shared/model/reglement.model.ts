import { ModeReglement } from 'app/shared/model/enumerations/mode-reglement.model';
import { Moment } from 'moment';

export interface IReglement {
  id?: number;
  factureId?: number;
  modeReglement?: ModeReglement;
  montant?: number;
  dateReglement?: Moment;
  reference?: string;
}
