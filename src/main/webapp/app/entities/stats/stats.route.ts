import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { StatsChiffreAffaireComponent } from './components/stats-chiffre-affaire.component';

export const statsRoute: Routes = [
  {
    path: 'chiffre-affaire',
    component: StatsChiffreAffaireComponent,
    data: {
      authorities: ['ROLE_STATS'],
      pageTitle: 'logisticaApp.stats.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
