import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, Subject, of, concat } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReportingService } from '../reporting.service';
import { FormGroup, FormControl } from '@angular/forms';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';
import * as moment from 'moment';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';
import { ITransporteur } from 'app/shared/model/transporteur.model';
import { IRecapitulatifVenteEfficaciteChauffeur } from 'app/shared/model/recapitulatif-vente-efficacite-chauffeur.model';
import { format } from 'app/shared/util/date-util';

@Component({
  selector: 'jhi-reporting-vente-efficacite-chauffeur',
  templateUrl: './reporting-vente-efficacite-chauffeur.component.html'
})
export class ReportingVenteEfficaciteChauffeurComponent implements OnInit, OnDestroy {

  transporteurs$: Observable<ITransporteur[]>;
  transporteurInput$ = new Subject<string>();
  transporteursLoading:Boolean = false;

  reportingForm = new FormGroup({
      transporteur: new FormControl(),
      dateDebut: new FormControl(),
      dateFin: new FormControl()
    });

  recapitulatifs: IRecapitulatifVenteEfficaciteChauffeur[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  isSearching:Boolean = false;

  constructor(
    protected reportingService: ReportingService,
    protected transporteurService: TransporteurService,
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
    const defaultDateDebut = moment(new Date()).startOf('month');
    this.reportingForm.get('dateDebut').setValue(defaultDateDebut);
    const defaultDateFin = moment(new Date()).endOf('month');
    this.reportingForm.get('dateFin').setValue(defaultDateFin);
  }

  loadAll() {
    this.loadTransporteurs();
    this.search();
  }

  search(){
    this.isSearching = true;
    this.reportingService
      .getReportingVenteEfficaciteChauffeur(this.buildReportingRequest())
      .subscribe((res: HttpResponse<IRecapitulatifVenteEfficaciteChauffeur[]>) => {
        this.isSearching = false;
        this.paginateRecapitulatifs(res.body, res.headers);
      });
  }

  export(){
    this.reportingService
        .exportReporting(this.buildReportingRequest(), '/vente/chauffeur/efficacite/export');
  }

  private buildReportingRequest(): any {
    const reportingRequest = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    }
    if(this.reportingForm.get('transporteur').value){
      reportingRequest['transporteurId'] = this.reportingForm.get('transporteur').value.id;
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

  trackId(index: number, item: IRecapitulatifVenteEfficaciteChauffeur) {
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

  protected paginateRecapitulatifs(data: IRecapitulatifVenteEfficaciteChauffeur[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.recapitulatifs.push(data[i]);
    }
  }

    private loadTransporteurs(){
      this.transporteurs$ = concat(
        of([]), // default items
        this.transporteurInput$.pipe(
            startWith(''),
            debounceTime(500),
            distinctUntilChanged(),
            tap(() => (this.transporteursLoading = true)),
            switchMap(nom =>
                this.transporteurService
                    .query({'nom.contains': nom})
                    .pipe(
                      map((resp: HttpResponse<ITransporteur[]>) => resp.body),
                      catchError(() => of([])),
                      map((transporteurs: ITransporteur[]) => {
                        const enriched:ITransporteur[] = [];
                        transporteurs.forEach(transporteur => {
                          transporteur.description = `${transporteur.nom} | ${transporteur.prenom} | ${transporteur.matricule}`
                          enriched.push(transporteur);
                        });
                        return enriched;
                      })
                      )
            ),
            tap(() => (this.transporteursLoading = false))
        )
    );
  }
}
