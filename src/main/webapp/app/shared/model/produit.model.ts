import { IAudit } from 'app/shared/model/audit.model';

export interface IProduit {
  id?: number;
  code?: string;
  categorie?: string;
  audit?: IAudit;
}

export class Produit implements IProduit {
  constructor(public id?: number, public code?: string, public categorie?: string) {}
}
