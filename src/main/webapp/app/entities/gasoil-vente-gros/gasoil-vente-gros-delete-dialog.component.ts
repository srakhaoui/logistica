import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGasoilVenteGros } from 'app/shared/model/gasoil-vente-gros.model';
import { GasoilVenteGrosService } from './gasoil-vente-gros.service';

@Component({
  templateUrl: './gasoil-vente-gros-delete-dialog.component.html'
})
export class GasoilVenteGrosDeleteDialogComponent {
  gasoilVenteGros: IGasoilVenteGros;

  constructor(
    protected gasoilVenteGrosService: GasoilVenteGrosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.gasoilVenteGrosService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'gasoilVenteGrosListModification',
        content: 'Deleted an gasoilVenteGros'
      });
      this.activeModal.dismiss(true);
    });
  }
}
