import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISociete } from 'app/shared/model/societe.model';

@Component({
  selector: 'jhi-societe-detail',
  templateUrl: './societe-detail.component.html'
})
export class SocieteDetailComponent implements OnInit {
  societe: ISociete;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ societe }) => {
      this.societe = societe;
    });
  }

  previousState() {
    window.history.back();
  }
}
