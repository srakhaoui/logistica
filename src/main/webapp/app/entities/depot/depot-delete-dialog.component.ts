import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';

@Component({
  templateUrl: './depot-delete-dialog.component.html'
})
export class DepotDeleteDialogComponent {
  depot: IDepot;

  constructor(protected depotService: DepotService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.depotService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'depotListModification',
        content: 'Deleted an depot'
      });
      this.activeModal.dismiss(true);
    });
  }
}
