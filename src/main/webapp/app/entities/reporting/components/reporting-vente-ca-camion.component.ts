import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { Observable, Subject, of, concat } from 'rxjs';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReportingService } from '../reporting.service';
import { FormGroup, FormControl } from '@angular/forms';
import { ISociete } from 'app/shared/model/societe.model';
import { IClient } from 'app/shared/model/client.model';
import { IProduit } from 'app/shared/model/produit.model';
import { SocieteService } from 'app/entities/societe/societe.service';
import { ProduitService } from 'app/entities/produit/produit.service';
import { ClientService } from 'app/entities/client/client.service';

import * as moment from 'moment';
import { format } from 'app/shared/util/date-util';
import { IRecapitulatifVenteCaCamion } from 'app/shared/model/recapitulatif-vente-ca-camion.model';

@Component({
  selector: 'jhi-reporting-vente-ca-camion',
  templateUrl: './reporting-vente-ca-camion.component.html'
})
export class ReportingVenteCaCamionComponent implements OnInit, OnDestroy {
  societes: ISociete[];

  clients$: Observable<IClient[]>;
  clientInput$ = new Subject<string>();
  clientsLoading:Boolean = false;

  produits$: Observable<IProduit[]>;
  produitInput$ = new Subject<string>();
  produitsLoading:Boolean = false;

  reportingForm = new FormGroup({
      societe: new FormControl(),
      client: new FormControl(),
      produit: new FormControl(),
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
    protected societeService: SocieteService,
    protected clientService: ClientService,
    protected produitService: ProduitService,
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
    this.reverse = false;
  }

  private initForm(){
    const defaultDateDebut = moment(new Date()).startOf('month');
    this.reportingForm.get('dateDebut').setValue(defaultDateDebut);
    const defaultDateFin = moment(new Date()).endOf('month');
    this.reportingForm.get('dateFin').setValue(defaultDateFin);
  }

  loadAll() {
    this.societeService
              .query()
              .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loadClients();
    this.loadProduits();
    this.search();
  }

  search(){
    this.isSearching = true;
    this.reportingService
      .getReportingVenteCaCamion(this.buildReportingRequest())
      .subscribe((res: HttpResponse<IRecapitulatifVenteCaCamion[]>) => {
        this.isSearching = false;
        this.paginateRecapitulatifs(res.body, res.headers);
      });
  }

  private loadClients(){
    this.clients$ = concat(
            of([]), // default items
            this.clientInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.clientsLoading = true)),
                switchMap(nom =>
                    this.clientService
                        .query({'nom.contains': nom})
                        .pipe(map((resp: HttpResponse<IClient[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.clientsLoading = false))
            )
        );
  }

  private loadProduits(){
    this.produits$ = concat(
            of([]), // default items
            this.produitInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.produitsLoading = true)),
                switchMap(nom =>
                    this.produitService
                        .query({'code.contains': nom})
                        .pipe(map((resp: HttpResponse<IProduit[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.produitsLoading = false))
            )
        );
  }

  export(){
    this.reportingService
        .exportReporting(this.buildReportingRequest(), '/vente/ca-camion/export');
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
    if(this.reportingForm.get('client').value){
      reportingRequest['clientId'] = this.reportingForm.get('client').value.id;
    }
    if(this.reportingForm.get('produit').value){
      reportingRequest['produitId'] = this.reportingForm.get('produit').value.id;
    }
    if(this.reportingForm.get('dateDebut').value){
      reportingRequest['dateDebut'] = format(this.reportingForm.get('dateDebut').value);
    }
    if(this.reportingForm.get('dateFin').value){
      reportingRequest['dateFin'] = format(this.reportingForm.get('dateFin').value);
    }
    return reportingRequest;
  }

  trackSocieteById(index: number, item: ISociete) {
    return item.id;
  }

  reset() {
    this.page = 0;
    this.recapitulatifs = [];
    this.search();
  }

  loadPage(page) {
    this.page = page;
    this.search();
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
