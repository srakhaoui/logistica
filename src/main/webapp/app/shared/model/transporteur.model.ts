import { ISociete } from 'app/shared/model/societe.model';
import { IAudit } from 'app/shared/model/audit.model';

export interface ITransporteur {
  id?: number;
  nom?: string;
  prenom?: string;
  telephone?: string;
  matricule?: string;
  description?: string;
  proprietaire?: ISociete;
  audit?: IAudit;
}

export class Transporteur implements ITransporteur {
  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public telephone?: string,
    public matricule?: string,
    public proprietaire?: ISociete
  ) {}
}
