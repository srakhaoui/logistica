import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ReportingAchatComponent } from './components/reporting-achat.component';
import { ReportingAchatTrajetComponent } from './components/reporting-achat-trajet.component';
import { Routes } from '@angular/router';

export const reportingRoute: Routes = [
  {
    path: 'achat',
    component: ReportingAchatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.reporting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'achat/trajet',
    component: ReportingAchatTrajetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.reporting.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
