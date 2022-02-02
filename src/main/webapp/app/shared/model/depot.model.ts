
export interface IDepot {
  id?: number;
  stock?: number;
  nom?: string;
  consommationInterne?: boolean;
}

export class Depot implements IDepot {
  constructor(
    public id?: number,
    public stock?: number,
    public nom?: string,
    public consommationInterne?: boolean
  ) {
    this.consommationInterne = this.consommationInterne || false;
  }
}
