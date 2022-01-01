import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { ReportingAchatComponent } from './components/reporting-achat.component';
import { ReportingAchatTrajetComponent } from './components/reporting-achat-trajet.component';
import { reportingRoute } from './reporting.route';
import { ReportingVenteClientComponent } from './components/reporting-vente-client.component';
import { ReportingVenteChauffeurComponent } from './components/reporting-vente-chauffeur.component';
import { ReportingVenteEfficaciteChauffeurComponent } from './components/reporting-vente-efficacite-chauffeur.component';
import { ReportingVenteFacturationComponent } from './components/reporting-vente-facturation.component';
import { ReportingVenteCaCamionComponent } from './components/reporting-vente-ca-camion.component';
import { ReportingBonComponent } from './components/reporting-bon.component';
import { ReportingGasoilChargesComponent } from './components/reporting-gasoil-charges.component';
import { ReportingGasoilGrosVenteComponent } from './components/reporting-gasoil-gros-vente.component';
import { ReportingGasoilGrosAchatComponent } from './components/reporting-gasoil-gros-achat.component';
import { ReportingGasoilGrosComponent } from './components/reporting-gasoil-gros.component';
import { ReportingStockComponent } from './components/reporting-stock.component';


@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(reportingRoute)],
  declarations: [ReportingAchatComponent, ReportingAchatTrajetComponent, ReportingVenteClientComponent, ReportingVenteChauffeurComponent, ReportingVenteFacturationComponent, ReportingVenteCaCamionComponent, ReportingBonComponent, ReportingGasoilChargesComponent, ReportingVenteEfficaciteChauffeurComponent, ReportingGasoilGrosVenteComponent, ReportingGasoilGrosAchatComponent, ReportingGasoilGrosComponent, ReportingStockComponent],
  entryComponents: [ReportingAchatComponent, ReportingVenteClientComponent, ReportingVenteChauffeurComponent, ReportingVenteFacturationComponent, ReportingVenteCaCamionComponent, ReportingBonComponent, ReportingGasoilChargesComponent, ReportingVenteEfficaciteChauffeurComponent, ReportingGasoilGrosVenteComponent, ReportingGasoilGrosAchatComponent, ReportingGasoilGrosComponent, ReportingStockComponent]
})
export class LogisticaReportingModule {}
