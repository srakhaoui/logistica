import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from './societe.service';

@Component({
  templateUrl: './societe-delete-dialog.component.html'
})
export class SocieteDeleteDialogComponent {
  societe: ISociete;

  constructor(protected societeService: SocieteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.societeService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'societeListModification',
        content: 'Deleted an societe'
      });
      this.activeModal.dismiss(true);
    });
  }
}
