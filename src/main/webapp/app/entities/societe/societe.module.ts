import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { SocieteComponent } from './societe.component';
import { SocieteDetailComponent } from './societe-detail.component';
import { SocieteUpdateComponent } from './societe-update.component';
import { SocieteDeleteDialogComponent } from './societe-delete-dialog.component';
import { societeRoute } from './societe.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(societeRoute)],
  declarations: [SocieteComponent, SocieteDetailComponent, SocieteUpdateComponent, SocieteDeleteDialogComponent],
  entryComponents: [SocieteDeleteDialogComponent]
})
export class LogisticaSocieteModule {}
