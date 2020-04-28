import { Moment } from 'moment';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { IClient } from 'app/shared/model/client.model';
import { ITransporteur } from 'app/shared/model/transporteur.model';
import { ITrajet } from 'app/shared/model/trajet.model';
import { IProduit } from 'app/shared/model/produit.model';
import { ISociete } from 'app/shared/model/societe.model';
import { Unite } from 'app/shared/model/enumerations/unite.model';
import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';
import { IAudit } from 'app/shared/model/audit.model';

export interface ILivraison {
  id?: number;
  dateBonCommande?: Moment;
  numeroBonCommande?: number;
  numeroBonLivraison?: number;
  dateBonLivraison?: Moment;
  numeroBonFournisseur?: number;
  quantiteVendue?: number;
  uniteVente?: Unite;
  prixTotalVente?: number;
  quantiteAchetee?: number;
  uniteAchat?: Unite;
  prixTotalAchat?: number;
  quantiteConvertie?: number;
  type?: TypeLivraison;
  facture?: boolean;
  dateBonCaisse?: Moment;
  reparationDivers?: number;
  trax?: number;
  balance?: number;
  avance?: number;
  autoroute?: number;
  dernierEtat?: number;
  penaliteEse?: number;
  penaliteChfrs?: number;
  fraisEspece?: number;
  retenu?: number;
  totalComission?: number;
  fournisseur?: IFournisseur;
  client?: IClient;
  transporteur?: ITransporteur;
  trajet?: ITrajet;
  produit?: IProduit;
  societeFacturation?: ISociete;
  audit?: IAudit;
}

export class Livraison implements ILivraison {
  constructor(
    public id?: number,
    public dateBonCommande?: Moment,
    public numeroBonCommande?: number,
    public numeroBonLivraison?: number,
    public dateBonLivraison?: Moment,
    public numeroBonFournisseur?: number,
    public quantiteVendue?: number,
    public uniteVente?: Unite,
    public prixTotalVente?: number,
    public quantiteAchetee?: number,
    public uniteAchat?: Unite,
    public prixTotalAchat?: number,
    public quantiteConvertie?: number,
    public type?: TypeLivraison,
    public facture?: boolean,
    public dateBonCaisse?: Moment,
    public reparationDivers?: number,
    public trax?: number,
    public balance?: number,
    public avance?: number,
    public autoroute?: number,
    public dernierEtat?: number,
    public penaliteEse?: number,
    public penaliteChfrs?: number,
    public fraisEspece?: number,
    public retenu?: number,
    public totalComission?: number,
    public fournisseur?: IFournisseur,
    public client?: IClient,
    public transporteur?: ITransporteur,
    public trajet?: ITrajet,
    public produit?: IProduit,
    public societeFacturation?: ISociete
  ) {
    this.facture = this.facture || false;
  }
}
