import { Moment } from 'moment';
import { IDepot } from 'app/shared/model/depot.model';

export interface IGasoilTransfert {
  id?: number;
  transfertDate?: Moment;
  quantite?: number;
  source?: IDepot;
  destination?: IDepot;
}

export class GasoilTransfert implements IGasoilTransfert {
  constructor(
    public id?: number,
    public transfertDate?: Moment,
    public quantite?: number,
    public source?: IDepot,
    public destination?: IDepot
  ) {}
}
