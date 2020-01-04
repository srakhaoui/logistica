import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILivraison } from 'app/shared/model/livraison.model';
import { TypeLivraison } from 'app/shared/model/enumerations/type-livraison.model';

@Component({
  selector: 'jhi-livraison-detail',
  templateUrl: './livraison-detail.component.html'
})
export class LivraisonDetailComponent implements OnInit {
  livraison: ILivraison;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ livraison }) => {
      this.livraison = livraison;
    });
  }

  previousState() {
    window.history.back();
  }

  isMarchandise(): boolean{
      return this.livraison.type === TypeLivraison.Marchandise;
  }
}
