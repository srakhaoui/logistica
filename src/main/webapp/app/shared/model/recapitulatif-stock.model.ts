
export interface IRecapitulatifStock {
  depot?: string;
  depotReserve?: boolean;
  stockInitial?: number;
  entreesAchat?: number;
  entreesTransfert?: number;
  sorties?: number;
  consommationInterne?: number;
  stock?: number;
}

export class RecapitulatifStock implements IRecapitulatifStock {
  constructor(
    public depot?: string,
    public depotReserve?: boolean,
    public stockInitial?: number,
    public entreesAchat?: number,
    public entreesTransfert?: number,
    public sorties?: number,
    public consommationInterne?: number,
    public stock?: number
  ) {
  }
}
