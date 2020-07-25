import { IAudit } from 'app/shared/model/audit.model';

export interface IFournisseur {
  id?: number;
  nom?: string;
  adresse?: string;
  telephone?: string;
  audit?: IAudit;
}

export class Fournisseur implements IFournisseur {
  constructor(public id?: number, public nom?: string, public adresse?: string, public telephone?: string) {}
}
