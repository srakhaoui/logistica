import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-reporting-bon',
  templateUrl: './reporting-bon.component.html'
})
export class ReportingBonComponent implements OnInit, OnDestroy {

  @Input()
  livraisonId: number;

  @Input()
  bonType: string;

  bonUri: string;

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit() {
    this.bonUri = `/api/livraisons/${this.livraisonId}/bon/${this.bonType}`;
  }

  ngOnDestroy() {
  }
}
