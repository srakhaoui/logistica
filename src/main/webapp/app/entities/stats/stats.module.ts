import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { StatsChiffreAffaireComponent } from './stats-chiffre-affaire.component';
import { statsRoute } from './stats.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(statsRoute)],
  declarations: [StatsChiffreAffaireComponent],
  entryComponents: [StatsChiffreAffaireComponent]
})
export class LogisticaStatsModule {}
