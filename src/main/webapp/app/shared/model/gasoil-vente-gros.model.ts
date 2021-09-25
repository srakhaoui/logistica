import { Moment } from 'moment';
import { ISociete } from 'app/shared/model/societe.model';
import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { IGasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';
import { UniteGasoilGros } from 'app/shared/model/enumerations/unite-gasoil-gros.model';

export interface IGasoilVenteGros {
  id?: number;
  prixVenteUnitaire?: number;
  quantite?: number;
  prixVenteTotal?: number;
  margeGlobale?: number;
  tauxMarge?: number;
  uniteGasoilGros?: UniteGasoilGros;
  transporteur?: ISociete;
  client?: IClientGrossiste;
  achatGasoil?: IGasoilAchatGros;
  dateVente?: Moment;
}

export class GasoilVenteGros implements IGasoilVenteGros {
  constructor(
    public id?: number,
    public prixVenteUnitaire?: number,
    public quantite?: number,
    public prixVenteTotal?: number,
    public margeGlobale?: number,
    public tauxMarge?: number,
    public uniteGasoilGros?: UniteGasoilGros,
    public transporteur?: ISociete,
    public client?: IClientGrossiste,
    public achatGasoil?: IGasoilAchatGros,
    public dateVente?: Moment
  ) {}
}
