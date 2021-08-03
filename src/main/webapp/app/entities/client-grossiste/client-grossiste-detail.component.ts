import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';

@Component({
  selector: 'jhi-client-grossiste-detail',
  templateUrl: './client-grossiste-detail.component.html'
})
export class ClientGrossisteDetailComponent implements OnInit {
  clientGrossiste: IClientGrossiste;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clientGrossiste }) => {
      this.clientGrossiste = clientGrossiste;
    });
  }

  previousState() {
    window.history.back();
  }
}
