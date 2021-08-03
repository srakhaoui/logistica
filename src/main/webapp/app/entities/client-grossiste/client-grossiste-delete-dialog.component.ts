import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { ClientGrossisteService } from './client-grossiste.service';

@Component({
  templateUrl: './client-grossiste-delete-dialog.component.html'
})
export class ClientGrossisteDeleteDialogComponent {
  clientGrossiste: IClientGrossiste;

  constructor(
    protected clientGrossisteService: ClientGrossisteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.clientGrossisteService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'clientGrossisteListModification',
        content: 'Deleted an clientGrossiste'
      });
      this.activeModal.dismiss(true);
    });
  }
}
