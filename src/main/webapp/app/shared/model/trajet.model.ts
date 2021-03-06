import { IAudit } from 'app/shared/model/audit.model';

export interface ITrajet {
  id?: number;
  depart?: string;
  destination?: string;
  description?: string;
  commission?: number;
  audit?: IAudit;
}

export class Trajet implements ITrajet {
  constructor(
    public id?: number,
    public depart?: string,
    public destination?: string,
    public description?: string,
    public commission?: number
  ) {}
}
