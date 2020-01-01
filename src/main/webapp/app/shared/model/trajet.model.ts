export interface ITrajet {
  id?: number;
  depart?: string;
  destination?: string;
  commission?: number;
}

export class Trajet implements ITrajet {
  constructor(public id?: number, public depart?: string, public destination?: string, public commission?: number) {}
}
