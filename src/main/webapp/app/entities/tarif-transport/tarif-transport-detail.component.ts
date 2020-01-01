import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifTransport } from 'app/shared/model/tarif-transport.model';

@Component({
  selector: 'jhi-tarif-transport-detail',
  templateUrl: './tarif-transport-detail.component.html'
})
export class TarifTransportDetailComponent implements OnInit {
  tarifTransport: ITarifTransport;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tarifTransport }) => {
      this.tarifTransport = tarifTransport;
    });
  }

  previousState() {
    window.history.back();
  }
}
