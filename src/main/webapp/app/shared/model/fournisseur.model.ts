export interface IFournisseur {
  id?: number;
  nom?: string;
  adresse?: string;
  telephone?: string;
}

export class Fournisseur implements IFournisseur {
  constructor(public id?: number, public nom?: string, public adresse?: string, public telephone?: string) {}
}
