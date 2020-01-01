import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILivraison } from 'app/shared/model/livraison.model';
import { LivraisonService } from './livraison.service';

@Component({
  templateUrl: './livraison-delete-dialog.component.html'
})
export class LivraisonDeleteDialogComponent {
  livraison: ILivraison;

  constructor(protected livraisonService: LivraisonService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.livraisonService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'livraisonListModification',
        content: 'Deleted an livraison'
      });
      this.activeModal.dismiss(true);
    });
  }
}
