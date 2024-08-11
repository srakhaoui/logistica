import { IClient } from 'app/shared/model/client.model';
import { TypeFacture } from 'app/shared/model/enumerations/type-facture.model';
import { StatusFacture } from 'app/shared/model/enumerations/status-facture.model';

export interface IArticle {
  id?: number;
  code?: string;
  designation?: string;
  quantite?: number;
  prixUnitaireHt?: number;
  montant?: number;
  factureId?: number;
}
