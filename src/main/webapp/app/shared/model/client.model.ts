import { IAudit } from 'app/shared/model/audit.model';

export interface IClient {
  id?: number;
  nom?: string;
  adresse?: string;
  telephone?: string;
  audit?: IAudit;
}

export class Client implements IClient {
  constructor(public id?: number, public nom?: string, public adresse?: string, public telephone?: string) {}
}
