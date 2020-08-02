import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGasoil } from 'app/shared/model/gasoil.model';

@Component({
  selector: 'jhi-gasoil-detail',
  templateUrl: './gasoil-detail.component.html'
})
export class GasoilDetailComponent implements OnInit {
  gasoil: IGasoil;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ gasoil }) => {
      this.gasoil = gasoil;
    });
  }

  previousState() {
    window.history.back();
  }
}
