import { IClient } from 'app/shared/model/client.model';
import { TypeFacture } from 'app/shared/model/enumerations/type-facture.model';
import { StatusFacture } from 'app/shared/model/enumerations/status-facture.model';
import { IArticle } from 'app/shared/model/article.model';
import { IReglement } from 'app/shared/model/reglement.model';
import { Moment } from 'moment';

export interface IFacture {
  id?: number;
  client?: IClient;
  dateFacturation?: Moment;
  mois?: number;
  annee?: number;
  nombreBonsLivraison?: number;
  totalQuantite?: number;
  totalPrixHt?: number;
  totalPrixTtc?: number;
  tva?: number;
  remise?: number;
  type?: TypeFacture;
  status?: StatusFacture;
  articles?: IArticle[];
  reglements?: IReglement[];
}
