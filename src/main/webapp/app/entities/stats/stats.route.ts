import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { StatsTauxRentabiliteComponent } from './components/stats-taux-rentabilite.component';
import { StatsTauxConsommationComponent } from './components/stats-taux-consommation.component';
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
  },
  {
    path: 'taux-rentabilite',
    component: StatsTauxRentabiliteComponent,
    data: {
      authorities: ['ROLE_STATS'],
      pageTitle: 'logisticaApp.stats.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
      path: 'taux-consommation',
      component: StatsTauxConsommationComponent,
      data: {
        authorities: ['ROLE_STATS'],
        pageTitle: 'logisticaApp.stats.home.title'
      },
      canActivate: [UserRouteAccessService]
    }
];
