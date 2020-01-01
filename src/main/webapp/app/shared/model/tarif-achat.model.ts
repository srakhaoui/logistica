import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { IProduit } from 'app/shared/model/produit.model';
import { Unite } from 'app/shared/model/enumerations/unite.model';

export interface ITarifAchat {
  id?: number;
  unite?: Unite;
  prix?: number;
  fournisseur?: IFournisseur;
  produit?: IProduit;
}

export class TarifAchat implements ITarifAchat {
  constructor(
    public id?: number,
    public unite?: Unite,
    public prix?: number,
    public fournisseur?: IFournisseur,
    public produit?: IProduit
  ) {}
}
