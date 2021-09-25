import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { Observable, Subject, of, concat } from 'rxjs';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReportingService } from '../reporting.service';
import { FormGroup, FormControl } from '@angular/forms';
import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from 'app/entities/societe/societe.service';

import * as moment from 'moment';
import { format } from 'app/shared/util/date-util';
import { IRecapitulatifGasoilGrosVente } from 'app/shared/model/recapitulatif-gasoil-gros-vente.model';

import { IFournisseurGrossiste } from 'app/shared/model/fournisseur-grossiste.model';
import { FournisseurGrossisteService } from 'app/entities/fournisseur-grossiste/fournisseur-grossiste.service';
import { IClientGrossiste } from 'app/shared/model/client-grossiste.model';
import { ClientGrossisteService } from 'app/entities/client-grossiste/client-grossiste.service';

@Component({
  selector: 'jhi-reporting-gasoil-gros-vente',
  templateUrl: './reporting-gasoil-gros-vente.component.html'
})
export class ReportingGasoilGrosVenteComponent implements OnInit, OnDestroy {
  societes: ISociete[];
  acheteur: ISociete;
  transporteur: ISociete;

  fournisseurs$: Observable<IFournisseurGrossiste[]>;
  fournisseurInput$ = new Subject<string>();
  fournisseursLoading:Boolean = false;

   clientsGrossistes$: Observable<IClientGrossiste[]>;
   clientsGrossistesInput$ = new Subject<string>();
   clientsGrossistesLoading:Boolean = false;

  reportingForm = new FormGroup({
      numeroBonReception: new FormControl(),
      acheteur: new FormControl(),
      transporteur: new FormControl(),
      client: new FormControl(),
      fournisseurGrossiste: new FormControl(),
      dateDebut: new FormControl(),
      dateFin: new FormControl()
    });

  recapitulatifs: IRecapitulatifGasoilGrosVente[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  isSearching:Boolean = false;

  constructor(
    protected societeService: SocieteService,
    protected fournisseurGrossisteService: FournisseurGrossisteService,
    protected clientGrossisteService: ClientGrossisteService,
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
    this.predicate = 'client';
    this.reverse = true;
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
    this.loadFournisseurs();
    this.loadClientsGrossistes();
    this.search();
  }

  search(){
    this.isSearching = true;
    this.reportingService
      .getReportingGasoilGrosVente(this.buildReportingRequest())
      .subscribe((res: HttpResponse<IRecapitulatifGasoilGrosVente[]>) => {
        this.isSearching = false;
        this.paginateRecapitulatifs(res.body, res.headers);
      });
  }

  export(){
    this.reportingService
        .exportGasoilGrosVenteReporting(this.buildReportingRequest(), '/ventes/export');
  }

  private buildReportingRequest(): any {
    const reportingRequest = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    }
    if(this.reportingForm.get('numeroBonReception').value){
      reportingRequest['numeroBonReception'] = this.reportingForm.get('numeroBonReception').value;
    }
    if(this.reportingForm.get('acheteur').value){
      reportingRequest['acheteurId'] = this.reportingForm.get('acheteur').value.id;
    }
    if(this.reportingForm.get('transporteur').value){
      reportingRequest['transporteurId'] = this.reportingForm.get('transporteur').value.id;
    }
    if(this.reportingForm.get('client').value){
      reportingRequest['clientId'] = this.reportingForm.get('client').value.id;
    }
    if(this.reportingForm.get('fournisseurGrossiste').value){
      reportingRequest['fournisseurId'] = this.reportingForm.get('fournisseurGrossiste').value.id;
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

  protected paginateRecapitulatifs(data: IRecapitulatifGasoilGrosVente[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.recapitulatifs.push(data[i]);
    }
  }

  private loadFournisseurs(){
    this.fournisseurs$ = concat(
            of([]), // default items
            this.fournisseurInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.fournisseursLoading = true)),
                switchMap(nom =>
                    this.fournisseurGrossisteService
                        .query({'nom.contains': nom})
                        .pipe(map((resp: HttpResponse<IFournisseurGrossiste[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.fournisseursLoading = false))
            )
        );
  }

  private loadClientsGrossistes(){
    this.clientsGrossistes$ = concat(
            of([]), // default items
            this.clientsGrossistesInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.clientsGrossistesLoading = true)),
                switchMap(nom =>
                    this.clientGrossisteService
                        .query({'nom.contains': nom})
                        .pipe(map((resp: HttpResponse<IClientGrossiste[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.clientsGrossistesLoading = false))
            )
        );
  }

}
