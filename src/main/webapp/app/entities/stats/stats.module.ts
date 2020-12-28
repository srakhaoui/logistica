import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { StatsChiffreAffaireComponent } from './components/stats-chiffre-affaire.component';
import { StatsTauxRentabiliteComponent } from './components/stats-taux-rentabilite.component';
import { statsRoute } from './stats.route';
import { ThemeService, ChartsModule } from 'ng2-charts';

@NgModule({
  imports: [LogisticaSharedModule, ChartsModule, RouterModule.forChild(statsRoute)],
  providers: [ThemeService],
  declarations: [StatsChiffreAffaireComponent, StatsTauxRentabiliteComponent],
  entryComponents: [StatsChiffreAffaireComponent, StatsTauxRentabiliteComponent]
})
export class LogisticaStatsModule {}
