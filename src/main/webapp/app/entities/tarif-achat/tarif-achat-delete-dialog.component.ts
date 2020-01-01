import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITarifAchat } from 'app/shared/model/tarif-achat.model';
import { TarifAchatService } from './tarif-achat.service';

@Component({
  templateUrl: './tarif-achat-delete-dialog.component.html'
})
export class TarifAchatDeleteDialogComponent {
  tarifAchat: ITarifAchat;

  constructor(
    protected tarifAchatService: TarifAchatService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tarifAchatService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'tarifAchatListModification',
        content: 'Deleted an tarifAchat'
      });
      this.activeModal.dismiss(true);
    });
  }
}
