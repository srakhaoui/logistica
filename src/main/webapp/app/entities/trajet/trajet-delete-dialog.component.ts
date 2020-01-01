import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrajet } from 'app/shared/model/trajet.model';
import { TrajetService } from './trajet.service';

@Component({
  templateUrl: './trajet-delete-dialog.component.html'
})
export class TrajetDeleteDialogComponent {
  trajet: ITrajet;

  constructor(protected trajetService: TrajetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.trajetService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'trajetListModification',
        content: 'Deleted an trajet'
      });
      this.activeModal.dismiss(true);
    });
  }
}
