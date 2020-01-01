import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { TarifAchatComponent } from './tarif-achat.component';
import { TarifAchatDetailComponent } from './tarif-achat-detail.component';
import { TarifAchatUpdateComponent } from './tarif-achat-update.component';
import { TarifAchatDeleteDialogComponent } from './tarif-achat-delete-dialog.component';
import { tarifAchatRoute } from './tarif-achat.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(tarifAchatRoute)],
  declarations: [TarifAchatComponent, TarifAchatDetailComponent, TarifAchatUpdateComponent, TarifAchatDeleteDialogComponent],
  entryComponents: [TarifAchatDeleteDialogComponent]
})
export class LogisticaTarifAchatModule {}
