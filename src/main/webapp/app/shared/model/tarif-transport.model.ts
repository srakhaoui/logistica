import { IClient } from 'app/shared/model/client.model';
import { ITrajet } from 'app/shared/model/trajet.model';
import { IProduit } from 'app/shared/model/produit.model';
import { Unite } from 'app/shared/model/enumerations/unite.model';

export interface ITarifTransport {
  id?: number;
  unite?: Unite;
  prix?: number;
  client?: IClient;
  trajet?: ITrajet;
  produit?: IProduit;
}

export class TarifTransport implements ITarifTransport {
  constructor(
    public id?: number,
    public unite?: Unite,
    public prix?: number,
    public client?: IClient,
    public trajet?: ITrajet,
    public produit?: IProduit
  ) {}
}
