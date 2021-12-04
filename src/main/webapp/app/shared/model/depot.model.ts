import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';

export interface IDepot {
  id?: number;
  stock?: number;
  nom?: string;
  consommationInterne?: boolean;
  alimentation?: IClientGrossiste;
  consommation?: IFournisseurGrossiste;
}

export class Depot implements IDepot {
  constructor(
    public id?: number,
    public stock?: number,
    public nom?: string,
    public consommationInterne?: boolean,
    public alimentation?: IClientGrossiste,
    public consommation?: IFournisseurGrossiste
  ) {
    this.consommationInterne = this.consommationInterne || false;
  }
}
