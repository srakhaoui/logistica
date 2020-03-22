import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { ReportingAchatComponent } from './components/reporting-achat.component';
import { ReportingAchatTrajetComponent } from './components/reporting-achat-trajet.component';
import { reportingRoute } from './reporting.route';
import { ReportingVenteClientComponent } from './components/reporting-vente-client.component';
import { ReportingVenteChauffeurComponent } from './components/reporting-vente-chauffeur.component';
import { ReportingVenteFacturationComponent } from './components/reporting-vente-facturation.component';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(reportingRoute)],
  declarations: [ReportingAchatComponent, ReportingAchatTrajetComponent, ReportingVenteClientComponent, ReportingVenteChauffeurComponent, ReportingVenteFacturationComponent],
  entryComponents: [ReportingAchatComponent, ReportingVenteClientComponent, ReportingVenteChauffeurComponent, ReportingVenteFacturationComponent]
})
export class LogisticaReportingModule {}
