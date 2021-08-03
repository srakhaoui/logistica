import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { GasoilAchatGrosComponent } from './gasoil-achat-gros.component';
import { GasoilAchatGrosDetailComponent } from './gasoil-achat-gros-detail.component';
import { GasoilAchatGrosUpdateComponent } from './gasoil-achat-gros-update.component';
import { GasoilAchatGrosDeleteDialogComponent } from './gasoil-achat-gros-delete-dialog.component';
import { gasoilAchatGrosRoute } from './gasoil-achat-gros.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(gasoilAchatGrosRoute)],
  declarations: [
    GasoilAchatGrosComponent,
    GasoilAchatGrosDetailComponent,
    GasoilAchatGrosUpdateComponent,
    GasoilAchatGrosDeleteDialogComponent
  ],
  entryComponents: [GasoilAchatGrosDeleteDialogComponent]
})
export class LogisticaGasoilAchatGrosModule {}
