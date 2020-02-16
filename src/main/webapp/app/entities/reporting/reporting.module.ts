import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LogisticaSharedModule } from 'app/shared/shared.module';
import { ReportingAchatComponent } from './components/reporting-achat.component';
import { ReportingAchatTrajetComponent } from './components/reporting-achat-trajet.component';
import { reportingRoute } from './reporting.route';

@NgModule({
  imports: [LogisticaSharedModule, RouterModule.forChild(reportingRoute)],
  declarations: [ReportingAchatComponent, ReportingAchatTrajetComponent],
  entryComponents: [ReportingAchatComponent]
})
export class LogisticaReportingModule {}
