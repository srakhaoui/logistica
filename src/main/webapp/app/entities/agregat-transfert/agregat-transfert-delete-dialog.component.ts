import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgregatTransfert } from 'app/shared/model/agregat-transfert.model';
import { AgregatTransfertService } from './agregat-transfert.service';

@Component({
  templateUrl: './agregat-transfert-delete-dialog.component.html'
})
export class AgregatTransfertDeleteDialogComponent {
  agregatTransfert: IAgregatTransfert;

  constructor(
    protected agregatTransfertService: AgregatTransfertService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agregatTransfertService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'agregatTransfertListModification',
        content: 'Deleted an agregatTransfert'
      });
      this.activeModal.dismiss(true);
    });
  }
}
