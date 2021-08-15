import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarburant } from 'app/shared/model/carburant.model';

@Component({
  selector: 'jhi-carburant-detail',
  templateUrl: './carburant-detail.component.html'
})
export class CarburantDetailComponent implements OnInit {
  carburant: ICarburant;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ carburant }) => {
      this.carburant = carburant;
    });
  }

  previousState() {
    window.history.back();
  }
}
