import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { ClientGrossisteComponent } from './client-grossiste.component';
import { ClientGrossisteDetailComponent } from './client-grossiste-detail.component';
import { ClientGrossisteUpdateComponent } from './client-grossiste-update.component';
import { ClientGrossisteDeleteDialogComponent } from './client-grossiste-delete-dialog.component';
import { clientGrossisteRoute } from './client-grossiste.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(clientGrossisteRoute)],
  declarations: [
    ClientGrossisteComponent,
    ClientGrossisteDetailComponent,
    ClientGrossisteUpdateComponent,
    ClientGrossisteDeleteDialogComponent
  ],
  entryComponents: [ClientGrossisteDeleteDialogComponent]
})
export class LogisticaClientGrossisteModule {}
