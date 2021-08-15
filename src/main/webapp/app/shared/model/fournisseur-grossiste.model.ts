export interface IFournisseurGrossiste {
  id?: number;
  nom?: string;
  adresse?: string;
  telephone?: string;
}

export class FournisseurGrossiste implements IFournisseurGrossiste {
  constructor(public id?: number, public nom?: string, public adresse?: string, public telephone?: string) {}
}
