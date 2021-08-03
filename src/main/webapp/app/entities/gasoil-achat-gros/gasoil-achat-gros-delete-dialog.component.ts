import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';
import { GasoilAchatGrosService } from './gasoil-achat-gros.service';

@Component({
  templateUrl: './gasoil-achat-gros-delete-dialog.component.html'
})
export class GasoilAchatGrosDeleteDialogComponent {
  gasoilAchatGros: IGasoilAchatGros;

  constructor(
    protected gasoilAchatGrosService: GasoilAchatGrosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.gasoilAchatGrosService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'gasoilAchatGrosListModification',
        content: 'Deleted an gasoilAchatGros'
      });
      this.activeModal.dismiss(true);
    });
  }
}
