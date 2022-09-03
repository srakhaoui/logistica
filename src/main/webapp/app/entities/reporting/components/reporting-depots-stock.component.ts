import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRecapitulatifDepotAgregatStock } from 'app/shared/model/recapitulatif-depot-agregat-stock.model';
import { ReportingService } from '../reporting.service';
import { FormGroup, FormControl } from '@angular/forms';
import { format } from 'app/shared/util/date-util';

@Component({
  selector: 'jhi-reporting-depots-stock',
  templateUrl: './reporting-depots-stock.component.html',
  styleUrls: ['./reporting-depots-stock.component.scss']
})
export class ReportingDepotStockComponent implements OnInit, OnDestroy {
  isSearching: boolean;

  reportingForm = new FormGroup({
      dateDebut: new FormControl()
    });

  recapitulatifStocks: IRecapitulatifDepotAgregatStock[];

  constructor(
    protected reportingService: ReportingService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.recapitulatifStocks = [];
    this.initForm();
    this.isSearching = false;
  }

  private initForm() {
  }

  loadAll() {
    this.search();
  }

  search(){
      this.isSearching = true;
      this.reportingService
        .getReportingDepotAgregatStock(this.buildReportingRequest())
        .subscribe((res: HttpResponse<IRecapitulatifDepotAgregatStock[]>) => {
          this.isSearching = false;
          this.recapitulatifStocks = res.body;
        });
  }

  private buildReportingRequest(): any {
    const reportingRequest = {};
    if(this.reportingForm.get('dateDebut').value){
      reportingRequest['dateDebut'] = format(this.reportingForm.get('dateDebut').value);
    }
    return reportingRequest;
  }

  reset() {
    this.recapitulatifStocks = [];
    this.search();
  }

  ngOnInit() {
    this.loadAll();
  }

  ngOnDestroy() {
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
