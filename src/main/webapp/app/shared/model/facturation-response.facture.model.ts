export interface IFacturationResponse {
  factureId?: number;
  nombreBonsLivraison?: number;
  prixTtc?: number;
  nomClient?: string;
  id?: number;
}

export class FacturationResponse implements IFacturationResponse {
  constructor(
    public factureId?: number,
    public nombreBonsLivraison?: number,
    public prixTtc?: number,
    public nomClient?: string,
    public id?: number
  ) {}
}
