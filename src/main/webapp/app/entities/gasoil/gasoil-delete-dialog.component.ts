import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGasoil } from 'app/shared/model/gasoil.model';
import { GasoilService } from './gasoil.service';

@Component({
  templateUrl: './gasoil-delete-dialog.component.html'
})
export class GasoilDeleteDialogComponent {
  gasoil: IGasoil;

  constructor(protected gasoilService: GasoilService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.gasoilService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'gasoilListModification',
        content: 'Deleted an gasoil'
      });
      this.activeModal.dismiss(true);
    });
  }
}
