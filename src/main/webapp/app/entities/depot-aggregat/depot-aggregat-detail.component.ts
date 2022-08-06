import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepotAggregat } from 'app/shared/model/depot-aggregat.model';

@Component({
  selector: 'jhi-depot-aggregat-detail',
  templateUrl: './depot-aggregat-detail.component.html'
})
export class DepotAggregatDetailComponent implements OnInit {
  depotAggregat: IDepotAggregat;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ depotAggregat }) => {
      this.depotAggregat = depotAggregat;
    });
  }

  previousState() {
    window.history.back();
  }
}
