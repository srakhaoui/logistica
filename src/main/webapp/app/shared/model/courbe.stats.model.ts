
export interface ICourbe {
  abscisses?: string[];
  ordonnees?: number[];
}

export class Courbe implements ICourbe {
  constructor(
     public abscisses?: string[],
     public ordonnees?: number[]
  ) {}
}

