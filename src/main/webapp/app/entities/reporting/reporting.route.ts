import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ReportingAchatComponent } from './components/reporting-achat.component';
import { ReportingAchatTrajetComponent } from './components/reporting-achat-trajet.component';
import { Routes } from '@angular/router';
import { ReportingVenteClientComponent } from './components/reporting-vente-client.component';
import { ReportingVenteChauffeurComponent } from './components/reporting-vente-chauffeur.component';
import { ReportingVenteEfficaciteChauffeurComponent } from './components/reporting-vente-efficacite-chauffeur.component';
import { ReportingVenteFacturationComponent } from './components/reporting-vente-facturation.component';
import { ReportingVenteCaCamionComponent } from './components/reporting-vente-ca-camion.component';
import { ReportingGasoilChargesComponent } from './components/reporting-gasoil-charges.component';

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
  },
  {
    path: 'vente/chauffeur/efficacite',
    component: ReportingVenteEfficaciteChauffeurComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.reporting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'vente/facturation',
    component: ReportingVenteFacturationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.reporting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'vente/ca-camion',
    component: ReportingVenteCaCamionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'logisticaApp.reporting.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
      path: 'gasoil/charges',
      component: ReportingGasoilChargesComponent,
      data: {
        authorities: ['ROLE_GASOIL'],
        pageTitle: 'logisticaApp.reporting.home.title'
      },
      canActivate: [UserRouteAccessService]
    }
];
