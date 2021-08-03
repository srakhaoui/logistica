export interface IClientGrossiste {
  id?: number;
  nom?: string;
  adresse?: string;
  telephone?: string;
}

export class ClientGrossiste implements IClientGrossiste {
  constructor(public id?: number, public nom?: string, public adresse?: string, public telephone?: string) {}
}
