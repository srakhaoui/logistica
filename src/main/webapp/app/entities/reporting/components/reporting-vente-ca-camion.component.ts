import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReportingService } from '../reporting.service';
import { FormGroup, FormControl } from '@angular/forms';
import * as moment from 'moment';
import { format } from 'app/shared/util/date-util';
import { IRecapitulatifVenteCaCamion } from 'app/shared/model/recapitulatif-vente-ca-camion.model';

@Component({
  selector: 'jhi-reporting-vente-ca-camion',
  templateUrl: './reporting-vente-ca-camion.component.html'
})
export class ReportingVenteCaCamionComponent implements OnInit, OnDestroy {

  reportingForm = new FormGroup({
      dateDebut: new FormControl(),
      dateFin: new FormControl()
    });

  recapitulatifs: IRecapitulatifVenteCaCamion[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  isSearching:Boolean = false;

  constructor(
    protected reportingService: ReportingService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks
  ) {
    this.recapitulatifs = [];
    this.initForm();
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'nomChauffeur';
    this.reverse = true;
  }

  private initForm(){
    const defaultDateDebut = moment(new Date());
    defaultDateDebut.set("day", -7);
    this.reportingForm.get('dateDebut').setValue(defaultDateDebut);
    const defaultDateFin = moment(new Date());
    this.reportingForm.get('dateFin').setValue(defaultDateFin);
  }

  loadAll() {
    this.isSearching = true;
    this.reportingService
      .getReportingVenteCaCamion(this.buildReportingRequest())
      .subscribe((res: HttpResponse<IRecapitulatifVenteCaCamion[]>) => {
        this.isSearching = false;
        this.recapitulatifs = [];
        const data:IRecapitulatifVenteCaCamion[] = res.body;
        for (let i = 0; i < data.length; i++) {
          this.recapitulatifs.push(data[i]);
        }
      });
  }

  private buildReportingRequest(): any {
    const reportingRequest = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    }
    if(this.reportingForm.get('dateDebut').value){
      reportingRequest['dateDebut'] = format(this.reportingForm.get('dateDebut').value);
    }
    if(this.reportingForm.get('dateFin').value){
      reportingRequest['dateFin'] = format(this.reportingForm.get('dateFin').value);
    }
    return reportingRequest;
  }

  reset() {
    this.page = 0;
    this.recapitulatifs = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
  }

  ngOnDestroy() {
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected paginateRecapitulatifs(data: IRecapitulatifVenteCaCamion[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.recapitulatifs.push(data[i]);
    }
  }
}
