export interface IFacturationFactureResponse {
  factureId?: number;
  nombreBonsLivraison?: number;
  prixTtc?: number;
  nomClient?: string;
  id?: number;
}

export class FacturationFactureResponse implements IFacturationFactureResponse {
  constructor(
    public factureId?: number,
    public nombreBonsLivraison?: number,
    public prixTtc?: number,
    public nomClient?: string,
    public id?: number
  ) {}
}
