import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrajet } from 'app/shared/model/trajet.model';

@Component({
  selector: 'jhi-trajet-detail',
  templateUrl: './trajet-detail.component.html'
})
export class TrajetDetailComponent implements OnInit {
  trajet: ITrajet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trajet }) => {
      this.trajet = trajet;
    });
  }

  previousState() {
    window.history.back();
  }
}
