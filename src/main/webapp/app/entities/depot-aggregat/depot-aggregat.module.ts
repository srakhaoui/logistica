import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { DepotAggregatComponent } from './depot-aggregat.component';
import { DepotAggregatDetailComponent } from './depot-aggregat-detail.component';
import { DepotAggregatUpdateComponent } from './depot-aggregat-update.component';
import { DepotAggregatDeleteDialogComponent } from './depot-aggregat-delete-dialog.component';
import { depotAggregatRoute } from './depot-aggregat.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(depotAggregatRoute)],
  declarations: [DepotAggregatComponent, DepotAggregatDetailComponent, DepotAggregatUpdateComponent, DepotAggregatDeleteDialogComponent],
  entryComponents: [DepotAggregatDeleteDialogComponent]
})
export class LogisticaDepotAggregatModule {}
