import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransporteur } from 'app/shared/model/transporteur.model';

@Component({
  selector: 'jhi-transporteur-detail',
  templateUrl: './transporteur-detail.component.html'
})
export class TransporteurDetailComponent implements OnInit {
  transporteur: ITransporteur;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transporteur }) => {
      this.transporteur = transporteur;
    });
  }

  previousState() {
    window.history.back();
  }
}
