import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGasoilTransfert } from 'app/shared/model/gasoil-transfert.model';

@Component({
  selector: 'jhi-gasoil-transfert-detail',
  templateUrl: './gasoil-transfert-detail.component.html'
})
export class GasoilTransfertDetailComponent implements OnInit {
  gasoilTransfert: IGasoilTransfert;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ gasoilTransfert }) => {
      this.gasoilTransfert = gasoilTransfert;
    });
  }

  previousState() {
    window.history.back();
  }
}
