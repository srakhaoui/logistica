export interface ITrajet {
  id?: number;
  depart?: string;
  destination?: string;
  description?: string;
  commission?: number;
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
