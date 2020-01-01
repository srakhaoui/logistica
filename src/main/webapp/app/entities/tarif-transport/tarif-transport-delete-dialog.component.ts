import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifTransport } from 'app/shared/model/tarif-transport.model';
import { TarifTransportService } from './tarif-transport.service';

@Component({
  templateUrl: './tarif-transport-delete-dialog.component.html'
})
export class TarifTransportDeleteDialogComponent {
  tarifTransport: ITarifTransport;

  constructor(
    protected tarifTransportService: TarifTransportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tarifTransportService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'tarifTransportListModification',
        content: 'Deleted an tarifTransport'
      });
      this.activeModal.dismiss(true);
    });
  }
}
