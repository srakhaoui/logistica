import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';

@Component({
  selector: 'jhi-gasoil-vente-gros-detail',
  templateUrl: './gasoil-vente-gros-detail.component.html'
})
export class GasoilVenteGrosDetailComponent implements OnInit {
  gasoilVenteGros: IGasoilVenteGros;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ gasoilVenteGros }) => {
      this.gasoilVenteGros = gasoilVenteGros;
    });
  }

  previousState() {
    window.history.back();
  }
}
