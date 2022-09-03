import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgregatTransfert } from 'app/shared/model/agregat-transfert.model';

@Component({
  selector: 'jhi-agregat-transfert-detail',
  templateUrl: './agregat-transfert-detail.component.html'
})
export class AgregatTransfertDetailComponent implements OnInit {
  agregatTransfert: IAgregatTransfert;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agregatTransfert }) => {
      this.agregatTransfert = agregatTransfert;
    });
  }

  previousState() {
    window.history.back();
  }
}
