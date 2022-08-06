export interface IDepotAggregat {
  id?: number;
  stock?: number;
  nom?: string;
}

export class DepotAggregat implements IDepotAggregat {
  constructor(public id?: number, public stock?: number, public nom?: string) {}
}
