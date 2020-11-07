export interface IGasoilPrice {
  price?: number;
}

export class GasoilPrice implements IGasoilPrice {
  constructor(public price?: number) {}
}
