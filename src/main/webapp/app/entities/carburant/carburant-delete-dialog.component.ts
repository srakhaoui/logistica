import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarburant } from 'app/shared/model/carburant.model';
import { CarburantService } from './carburant.service';

@Component({
  templateUrl: './carburant-delete-dialog.component.html'
})
export class CarburantDeleteDialogComponent {
  carburant: ICarburant;

  constructor(protected carburantService: CarburantService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.carburantService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'carburantListModification',
        content: 'Deleted an carburant'
      });
      this.activeModal.dismiss(true);
    });
  }
}
