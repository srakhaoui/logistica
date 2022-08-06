import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepotAggregat } from 'app/shared/model/depot-aggregat.model';
import { DepotAggregatService } from './depot-aggregat.service';

@Component({
  templateUrl: './depot-aggregat-delete-dialog.component.html'
})
export class DepotAggregatDeleteDialogComponent {
  depotAggregat: IDepotAggregat;

  constructor(
    protected depotAggregatService: DepotAggregatService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.depotAggregatService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'depotAggregatListModification',
        content: 'Deleted an depotAggregat'
      });
      this.activeModal.dismiss(true);
    });
  }
}
