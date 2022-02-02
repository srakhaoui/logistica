import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { GasoilTransfertComponent } from './gasoil-transfert.component';
import { GasoilTransfertDetailComponent } from './gasoil-transfert-detail.component';
import { GasoilTransfertUpdateComponent } from './gasoil-transfert-update.component';
import { GasoilTransfertDeleteDialogComponent } from './gasoil-transfert-delete-dialog.component';
import { gasoilTransfertRoute } from './gasoil-transfert.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(gasoilTransfertRoute)],
  declarations: [
    GasoilTransfertComponent,
    GasoilTransfertDetailComponent,
    GasoilTransfertUpdateComponent,
    GasoilTransfertDeleteDialogComponent
  ],
  entryComponents: [GasoilTransfertDeleteDialogComponent]
})
export class LogisticaGasoilTransfertModule {}
