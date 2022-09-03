import { Moment } from 'moment';
import { IDepotAggregat } from 'app/shared/model/depot-aggregat.model';
import { Unite } from 'app/shared/model/enumerations/unite.model';

export interface IAgregatTransfert {
  id?: number;
  transfertDate?: Moment;
  quantite?: number;
  unite?: Unite,
  source?: IDepotAggregat;
  destination?: IDepotAggregat;
}

export class AgregatTransfert implements IAgregatTransfert {
  constructor(
    public id?: number,
    public transfertDate?: Moment,
    public quantite?: number,
    public unite?: Unite,
    public source?: IDepotAggregat,
    public destination?: IDepotAggregat
  ) {}
}
