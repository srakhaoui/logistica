import { IClient } from 'app/shared/model/client.model';
import { IProduit } from 'app/shared/model/produit.model';
import { Unite } from 'app/shared/model/enumerations/unite.model';
import { IAudit } from 'app/shared/model/audit.model';

export interface ITarifVente {
  id?: number;
  unite?: Unite;
  prix?: number;
  client?: IClient;
  produit?: IProduit;
  audit?: IAudit;
}

export class TarifVente implements ITarifVente {
  constructor(public id?: number, public unite?: Unite, public prix?: number, public client?: IClient, public produit?: IProduit) {}
}
