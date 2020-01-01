export interface IClient {
  id?: number;
  nom?: string;
  adresse?: string;
  telephone?: string;
}

export class Client implements IClient {
  constructor(public id?: number, public nom?: string, public adresse?: string, public telephone?: string) {}
}
