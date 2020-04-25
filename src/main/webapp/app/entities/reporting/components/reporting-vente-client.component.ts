import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription, Observable, Subject, of, concat } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IRecapitulatifAchat } from 'app/shared/model/recapitulatif-achat.model';
import { ReportingService } from '../reporting.service';
import { FormGroup, FormControl } from '@angular/forms';
import { ISociete } from 'app/shared/model/societe.model';
import { IFournisseur } from 'app/shared/model/fournisseur.model';
import { FournisseurService } from 'app/entities/fournisseur/fournisseur.service';
import { SocieteService } from 'app/entities/societe/societe.service';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';
import { Livraison, ILivraison } from 'app/shared/model/livraison.model';
import { IRecapitulatifVenteClient } from 'app/shared/model/recapitulatif-vente-client.model';
import * as moment from 'moment';
import { format } from 'app/shared/util/date-util';

@Component({
  selector: 'jhi-reporting-vente-client',
  templateUrl: './reporting-vente-client.component.html'
})
export class ReportingVenteClientComponent implements OnInit, OnDestroy {
  societes: ISociete[];

  reportingForm = new FormGroup({
      societe: new FormControl(),
      facture: new FormControl(),
      typeLivraison: new FormControl(),
      dateDebut: new FormControl(),
      dateFin: new FormControl()
    });

  recapitulatifs: IRecapitulatifVenteClient[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  isSearching: Boolean = false;

  constructor(
    protected reportingService: ReportingService,
    protected societeService: SocieteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.recapitulatifs = [];
    this.initForm();
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'fournisseur';
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
    this.societeService
            .query()
            .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.search();
  }

  search(){
    this.isSearching = true;
    this.reportingService
      .getReportingVenteClient(this.buildReportingRequest())
      .subscribe((res: HttpResponse<IRecapitulatifVenteClient[]>) => {
        this.isSearching = false;
        this.recapitulatifs = [];
        const data:IRecapitulatifVenteClient[] = res.body;
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
    if(this.reportingForm.get('societe').value){
      reportingRequest['societeId'] = this.reportingForm.get('societe').value.id;
    }
    if(this.reportingForm.get('typeLivraison').value){
      reportingRequest['typeLivraison'] = this.reportingForm.get('typeLivraison').value;
    }
    if(this.reportingForm.get('facture').value){
      reportingRequest['facture'] = this.reportingForm.get('facture').value;
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

  trackId(index: number, item: IRecapitulatifVenteClient) {
    return item.client;
  }

  trackSocieteById(index: number, item: ISociete) {
    return item.id;
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

  protected paginateRecapitulatifs(data: IRecapitulatifVenteClient[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.recapitulatifs.push(data[i]);
    }
  }
}
