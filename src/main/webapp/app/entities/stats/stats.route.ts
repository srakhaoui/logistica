import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const statsRoute: Routes = [
  {
    path: 'stats/chiffre-affaire',
    component: StatsChiffreAffaireComponent,
    data: {
      authorities: ['ROLE_STATS'],
      pageTitle: 'logisticaApp.stats.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
