import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';

@Component({
  templateUrl: './produit-delete-dialog.component.html'
})
export class ProduitDeleteDialogComponent {
  produit: IProduit;

  constructor(protected produitService: ProduitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.produitService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'produitListModification',
        content: 'Deleted an produit'
      });
      this.activeModal.dismiss(true);
    });
  }
}
