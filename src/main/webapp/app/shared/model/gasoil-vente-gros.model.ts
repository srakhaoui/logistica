import { ISociete } from 'app/shared/model/societe.model';
import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { IGasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';

export interface IGasoilVenteGros {
  id?: number;
  prixVenteUnitaire?: number;
  quantite?: number;
  prixVenteTotal?: number;
  margeGlobale?: number;
  tauxMarge?: number;
  societeFacturation?: ISociete;
  client?: IClientGrossiste;
  achatGasoil?: IGasoilAchatGros;
}

export class GasoilVenteGros implements IGasoilVenteGros {
  constructor(
    public id?: number,
    public prixVenteUnitaire?: number,
    public quantite?: number,
    public prixVenteTotal?: number,
    public margeGlobale?: number,
    public tauxMarge?: number,
    public societeFacturation?: ISociete,
    public client?: IClientGrossiste,
    public achatGasoil?: IGasoilAchatGros
  ) {}
}
