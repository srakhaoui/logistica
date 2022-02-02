import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGasoilTransfert } from 'app/shared/model/gasoil-transfert.model';
import { GasoilTransfertService } from './gasoil-transfert.service';

@Component({
  templateUrl: './gasoil-transfert-delete-dialog.component.html'
})
export class GasoilTransfertDeleteDialogComponent {
  gasoilTransfert: IGasoilTransfert;

  constructor(
    protected gasoilTransfertService: GasoilTransfertService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.gasoilTransfertService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'gasoilTransfertListModification',
        content: 'Deleted an gasoilTransfert'
      });
      this.activeModal.dismiss(true);
    });
  }
}
