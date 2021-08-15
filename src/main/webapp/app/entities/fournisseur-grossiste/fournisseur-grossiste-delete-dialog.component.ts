import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';
import { FournisseurGrossisteService } from './fournisseur-grossiste.service';

@Component({
  templateUrl: './fournisseur-grossiste-delete-dialog.component.html'
})
export class FournisseurGrossisteDeleteDialogComponent {
  fournisseurGrossiste: IFournisseurGrossiste;

  constructor(
    protected fournisseurGrossisteService: FournisseurGrossisteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fournisseurGrossisteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'fournisseurGrossisteListModification',
        content: 'Deleted an fournisseurGrossiste'
      });
      this.activeModal.dismiss(true);
    });
  }
}
