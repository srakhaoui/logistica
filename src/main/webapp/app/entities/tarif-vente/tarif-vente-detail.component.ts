import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifVente } from 'app/shared/model/tarif-vente.model';

@Component({
  selector: 'jhi-tarif-vente-detail',
  templateUrl: './tarif-vente-detail.component.html'
})
export class TarifVenteDetailComponent implements OnInit {
  tarifVente: ITarifVente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tarifVente }) => {
      this.tarifVente = tarifVente;
    });
  }

  previousState() {
    window.history.back();
  }
}
