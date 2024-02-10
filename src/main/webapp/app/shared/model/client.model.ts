import { IAudit } from 'app/shared/model/audit.model';
import { ISociete } from 'app/shared/model/societe.model';

export interface IClient {
  id?: number;
  nom?: string;
  adresse?: string;
  telephone?: string;
  societeFacturation?: ISociete;
  audit?: IAudit;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public nom?: string,
    public adresse?: string,
    public telephone?: string,
    public societeFacturation?: ISociete
  ) {}
}
