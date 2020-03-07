import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ReportingAchatComponent } from './components/reporting-achat.component';
import { ReportingAchatTrajetComponent } from './components/reporting-achat-trajet.component';
import { Routes } from '@angular/router';
import { ReportingVenteClientComponent } from './components/reporting-vente-client.component';
import { ReportingVenteChauffeurComponent } from './components/reporting-vente-chauffeur.component';

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
  },
  {
    path: 'vente/client',
    component: ReportingVenteClientComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.reporting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'vente/chauffeur',
    component: ReportingVenteChauffeurComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.reporting.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
