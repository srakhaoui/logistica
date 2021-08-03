import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';

@Component({
  selector: 'jhi-gasoil-achat-gros-detail',
  templateUrl: './gasoil-achat-gros-detail.component.html'
})
export class GasoilAchatGrosDetailComponent implements OnInit {
  gasoilAchatGros: IGasoilAchatGros;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ gasoilAchatGros }) => {
      this.gasoilAchatGros = gasoilAchatGros;
    });
  }

  previousState() {
    window.history.back();
  }
}
