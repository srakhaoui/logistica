import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription, Observable, Subject, of, concat } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReportingService } from '../reporting.service';
import { FormGroup, FormControl } from '@angular/forms';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';
import { IRecapitulatifVenteClient } from 'app/shared/model/recapitulatif-vente-client.model';
import * as moment from 'moment';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';
import { ITransporteur } from 'app/shared/model/transporteur.model';
import { IRecapitulatifVenteChauffeur } from 'app/shared/model/recapitulatif-vente-chauffeur.model';
import { format } from 'app/shared/util/date-util';
import { ISociete } from 'app/shared/model/societe.model';
import { IClient } from 'app/shared/model/client.model';
import { IRecapitulatifVenteFacturation } from 'app/shared/model/recapitulatif-vente-facturation.model';
import { ClientService } from 'app/entities/client/client.service';
import { SocieteService } from 'app/entities/societe/societe.service';

@Component({
  selector: 'jhi-reporting-vente-facturation',
  templateUrl: './reporting-vente-facturation.component.html'
})
export class ReportingVenteFacturationComponent implements OnInit, OnDestroy {
  
  societes: ISociete[];
  
  clients$: Observable<IClient[]>;
  clientInput$ = new Subject<string>();
  clientsLoading:Boolean = false;

  reportingForm = new FormGroup({
      societe: new FormControl(),
      client: new FormControl(),
      facture: new FormControl(),
      dateDebut: new FormControl(),
      dateFin: new FormControl()
    });

  recapitulatifs: IRecapitulatifVenteFacturation[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected reportingService: ReportingService,
    protected societeService: SocieteService,
    protected clientService: ClientService,    
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
    this.reportingService
      .getReportingVenteFacturation(this.buildReportingRequest())
      .subscribe((res: HttpResponse<IRecapitulatifVenteFacturation[]>) => {
        this.recapitulatifs = [];
        const data:IRecapitulatifVenteFacturation[] = res.body;
        for (let i = 0; i < data.length; i++) {
          this.recapitulatifs.push(data[i]);
        }
      });
    this.loadSocietes();
    this.loadClients();
  }

  private buildReportingRequest(): any {
    const reportingRequest = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    }
    if(this.reportingForm.get('societe').value){
      reportingRequest['societe'] = this.reportingForm.get('societe').value.id;
    }
    if(this.reportingForm.get('client').value){
      reportingRequest['client'] = this.reportingForm.get('client').value.id;
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

  protected paginateRecapitulatifs(data: IRecapitulatifVenteFacturation[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.recapitulatifs.push(data[i]);
    }
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

  private loadSocietes(){
    this.societeService
      .query()
      .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }
}
