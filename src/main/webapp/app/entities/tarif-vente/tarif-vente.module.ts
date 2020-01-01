import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { TarifVenteComponent } from './tarif-vente.component';
import { TarifVenteDetailComponent } from './tarif-vente-detail.component';
import { TarifVenteUpdateComponent } from './tarif-vente-update.component';
import { TarifVenteDeleteDialogComponent } from './tarif-vente-delete-dialog.component';
import { tarifVenteRoute } from './tarif-vente.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(tarifVenteRoute)],
  declarations: [TarifVenteComponent, TarifVenteDetailComponent, TarifVenteUpdateComponent, TarifVenteDeleteDialogComponent],
  entryComponents: [TarifVenteDeleteDialogComponent]
})
export class LogisticaTarifVenteModule {}
