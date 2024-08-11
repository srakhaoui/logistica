import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DEVICE } from 'app/app.constants';

@Component({
  selector: 'jhi-facturation-facture',
  templateUrl: './facturation-facture.component.html'
})
export class FacturationFactureComponent implements OnInit, OnDestroy {
  @Input()
  billing: any;

  device: string = DEVICE;

  @Output() billingEventEmitter: EventEmitter<any> = new EventEmitter();

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit() {}

  ngOnDestroy() {}

  facturer() {
    this.billingEventEmitter.emit(this.billing);
  }

  ajuster() {
    this.billing.montant = Math.max(this.billing.montantFacturationMin, Math.min(this.billing.montantFacturationMax, this.billing.montant));
  }
}
