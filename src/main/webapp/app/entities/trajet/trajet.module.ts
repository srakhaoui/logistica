import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { TrajetComponent } from './trajet.component';
import { TrajetDetailComponent } from './trajet-detail.component';
import { TrajetUpdateComponent } from './trajet-update.component';
import { TrajetDeleteDialogComponent } from './trajet-delete-dialog.component';
import { trajetRoute } from './trajet.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(trajetRoute)],
  declarations: [TrajetComponent, TrajetDetailComponent, TrajetUpdateComponent, TrajetDeleteDialogComponent],
  entryComponents: [TrajetDeleteDialogComponent]
})
export class LogisticaTrajetModule {}
