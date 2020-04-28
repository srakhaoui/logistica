import { IAudit } from 'app/shared/model/audit.model';

export interface ISociete {
  id?: number;
  nom?: string;
  audit?: IAudit;
}

export class Societe implements ISociete {
  constructor(public id?: number, public nom?: string) {}
}
