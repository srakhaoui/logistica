import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { TransporteurComponent } from './transporteur.component';
import { TransporteurDetailComponent } from './transporteur-detail.component';
import { TransporteurUpdateComponent } from './transporteur-update.component';
import { TransporteurDeleteDialogComponent } from './transporteur-delete-dialog.component';
import { transporteurRoute } from './transporteur.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(transporteurRoute)],
  declarations: [TransporteurComponent, TransporteurDetailComponent, TransporteurUpdateComponent, TransporteurDeleteDialogComponent],
  entryComponents: [TransporteurDeleteDialogComponent]
})
export class LogisticaTransporteurModule {}
