import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { FournisseurComponent } from './fournisseur.component';
import { FournisseurDetailComponent } from './fournisseur-detail.component';
import { FournisseurUpdateComponent } from './fournisseur-update.component';
import { FournisseurDeleteDialogComponent } from './fournisseur-delete-dialog.component';
import { fournisseurRoute } from './fournisseur.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(fournisseurRoute)],
  declarations: [FournisseurComponent, FournisseurDetailComponent, FournisseurUpdateComponent, FournisseurDeleteDialogComponent],
  entryComponents: [FournisseurDeleteDialogComponent]
})
export class LogisticaFournisseurModule {}
