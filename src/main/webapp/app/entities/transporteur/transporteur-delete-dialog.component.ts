import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransporteur } from 'app/shared/model/transporteur.model';
import { TransporteurService } from './transporteur.service';

@Component({
  templateUrl: './transporteur-delete-dialog.component.html'
})
export class TransporteurDeleteDialogComponent {
  transporteur: ITransporteur;

  constructor(
    protected transporteurService: TransporteurService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transporteurService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'transporteurListModification',
        content: 'Deleted an transporteur'
      });
      this.activeModal.dismiss(true);
    });
  }
}
