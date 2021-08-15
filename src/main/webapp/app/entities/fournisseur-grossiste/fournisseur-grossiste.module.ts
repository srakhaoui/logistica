import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { FournisseurGrossisteComponent } from './fournisseur-grossiste.component';
import { FournisseurGrossisteDetailComponent } from './fournisseur-grossiste-detail.component';
import { FournisseurGrossisteUpdateComponent } from './fournisseur-grossiste-update.component';
import { FournisseurGrossisteDeleteDialogComponent } from './fournisseur-grossiste-delete-dialog.component';
import { fournisseurGrossisteRoute } from './fournisseur-grossiste.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(fournisseurGrossisteRoute)],
  declarations: [
    FournisseurGrossisteComponent,
    FournisseurGrossisteDetailComponent,
    FournisseurGrossisteUpdateComponent,
    FournisseurGrossisteDeleteDialogComponent
  ],
  entryComponents: [FournisseurGrossisteDeleteDialogComponent]
})
export class LogisticaFournisseurGrossisteModule {}
