import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { AgregatTransfertComponent } from './agregat-transfert.component';
import { AgregatTransfertDetailComponent } from './agregat-transfert-detail.component';
import { AgregatTransfertUpdateComponent } from './agregat-transfert-update.component';
import { AgregatTransfertDeleteDialogComponent } from './agregat-transfert-delete-dialog.component';
import { agregatTransfertRoute } from './agregat-transfert.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(agregatTransfertRoute)],
  declarations: [
    AgregatTransfertComponent,
    AgregatTransfertDetailComponent,
    AgregatTransfertUpdateComponent,
    AgregatTransfertDeleteDialogComponent
  ],
  entryComponents: [AgregatTransfertDeleteDialogComponent]
})
export class LogisticaAgregatTransfertModule {}
