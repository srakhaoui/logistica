import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { LivraisonComponent } from './livraison.component';
import { LivraisonDetailComponent } from './livraison-detail.component';
import { LivraisonUpdateComponent } from './livraison-update.component';
import { LivraisonDeleteDialogComponent } from './livraison-delete-dialog.component';
import { livraisonRoute } from './livraison.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(livraisonRoute)],
  declarations: [LivraisonComponent, LivraisonDetailComponent, LivraisonUpdateComponent, LivraisonDeleteDialogComponent],
  entryComponents: [LivraisonDeleteDialogComponent]
})
export class LogisticaLivraisonModule {}
