import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { CarburantComponent } from './carburant.component';
import { CarburantDetailComponent } from './carburant-detail.component';
import { CarburantUpdateComponent } from './carburant-update.component';
import { CarburantDeleteDialogComponent } from './carburant-delete-dialog.component';
import { carburantRoute } from './carburant.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(carburantRoute)],
  declarations: [CarburantComponent, CarburantDetailComponent, CarburantUpdateComponent, CarburantDeleteDialogComponent],
  entryComponents: [CarburantDeleteDialogComponent]
})
export class LogisticaCarburantModule {}
