import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { GasoilComponent } from './gasoil.component';
import { GasoilDetailComponent } from './gasoil-detail.component';
import { GasoilUpdateComponent } from './gasoil-update.component';
import { GasoilDeleteDialogComponent } from './gasoil-delete-dialog.component';
import { gasoilRoute } from './gasoil.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(gasoilRoute)],
  declarations: [GasoilComponent, GasoilDetailComponent, GasoilUpdateComponent, GasoilDeleteDialogComponent],
  entryComponents: [GasoilDeleteDialogComponent]
})
export class LogisticaGasoilModule {}
