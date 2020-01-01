export interface ISociete {
  id?: number;
  nom?: string;
}

export class Societe implements ISociete {
  constructor(public id?: number, public nom?: string) {}
}
