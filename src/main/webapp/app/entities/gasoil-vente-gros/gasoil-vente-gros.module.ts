import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { GasoilVenteGrosComponent } from './gasoil-vente-gros.component';
import { GasoilVenteGrosDetailComponent } from './gasoil-vente-gros-detail.component';
import { GasoilVenteGrosUpdateComponent } from './gasoil-vente-gros-update.component';
import { GasoilVenteGrosDeleteDialogComponent } from './gasoil-vente-gros-delete-dialog.component';
import { gasoilVenteGrosRoute } from './gasoil-vente-gros.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(gasoilVenteGrosRoute)],
  declarations: [
    GasoilVenteGrosComponent,
    GasoilVenteGrosDetailComponent,
    GasoilVenteGrosUpdateComponent,
    GasoilVenteGrosDeleteDialogComponent
  ],
  entryComponents: [GasoilVenteGrosDeleteDialogComponent]
})
export class LogisticaGasoilVenteGrosModule {}
