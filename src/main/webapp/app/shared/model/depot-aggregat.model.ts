import { Unite } from 'app/shared/model/enumerations/unite.model';

export interface IDepotAggregat {
  id?: number;
  stock?: number;
  unite?: Unite;
  nom?: string;
}

export class DepotAggregat implements IDepotAggregat {
  constructor(public id?: number, public stock?: number, public unite?: Unite, public nom?: string) {}
}
