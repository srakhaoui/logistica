import { IRecapitulatifVenteClient } from 'app/shared/model/recapitulatif-vente-client.model';

export interface IRecapitulatifVentesClient {
  recapitulatifClients: IRecapitulatifVenteClient[];
  montantFacturationMax: number;
  montantFacturationMin: number;
}

export class RecapitulatifVentesClient implements IRecapitulatifVentesClient {
  constructor(
    public recapitulatifClients: IRecapitulatifVenteClient[],
    public montantFacturationMax: number,
    public montantFacturationMin: number
  ) {}
}
