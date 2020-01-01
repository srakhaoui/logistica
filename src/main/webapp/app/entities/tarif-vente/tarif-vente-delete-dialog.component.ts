import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifVente } from 'app/shared/model/tarif-vente.model';
import { TarifVenteService } from './tarif-vente.service';

@Component({
  templateUrl: './tarif-vente-delete-dialog.component.html'
})
export class TarifVenteDeleteDialogComponent {
  tarifVente: ITarifVente;

  constructor(
    protected tarifVenteService: TarifVenteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tarifVenteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'tarifVenteListModification',
        content: 'Deleted an tarifVente'
      });
      this.activeModal.dismiss(true);
    });
  }
}
