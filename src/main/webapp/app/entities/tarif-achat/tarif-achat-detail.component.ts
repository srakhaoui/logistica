import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITarifAchat } from 'app/shared/model/tarif-achat.model';

@Component({
  selector: 'jhi-tarif-achat-detail',
  templateUrl: './tarif-achat-detail.component.html'
})
export class TarifAchatDetailComponent implements OnInit {
  tarifAchat: ITarifAchat;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tarifAchat }) => {
      this.tarifAchat = tarifAchat;
    });
  }

  previousState() {
    window.history.back();
  }
}
