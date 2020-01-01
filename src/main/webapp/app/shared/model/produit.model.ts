export interface IProduit {
  id?: number;
  code?: string;
  categorie?: string;
}

export class Produit implements IProduit {
  constructor(public id?: number, public code?: string, public categorie?: string) {}
}
