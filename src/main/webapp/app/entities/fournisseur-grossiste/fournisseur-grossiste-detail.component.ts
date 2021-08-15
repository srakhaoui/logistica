import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';

@Component({
  selector: 'jhi-fournisseur-grossiste-detail',
  templateUrl: './fournisseur-grossiste-detail.component.html'
})
export class FournisseurGrossisteDetailComponent implements OnInit {
  fournisseurGrossiste: IFournisseurGrossiste;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fournisseurGrossiste }) => {
      this.fournisseurGrossiste = fournisseurGrossiste;
    });
  }

  previousState() {
    window.history.back();
  }
}
