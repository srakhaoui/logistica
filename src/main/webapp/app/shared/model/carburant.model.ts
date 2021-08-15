export interface ICarburant {
  id?: number;
  code?: string;
  categorie?: string;
}

export class Carburant implements ICarburant {
  constructor(public id?: number, public code?: string, public categorie?: string) {}
}
