import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription, Observable, Subject, of, concat } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import * as moment from 'moment';
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
import { format } from 'app/shared/util/date-util';
import { IClient } from 'app/shared/model/client.model';
import { ITransporteur } from 'app/shared/model/transporteur.model';
import { ITrajet } from 'app/shared/model/trajet.model';
import { ClientService } from 'app/entities/client/client.service';
import { TransporteurService } from 'app/entities/transporteur/transporteur.service';
import { TrajetService } from 'app/entities/trajet/trajet.service';

@Component({
  selector: 'jhi-reporting-achat-trajet',
  templateUrl: './reporting-achat-trajet.component.html'
})
export class ReportingAchatTrajetComponent implements OnInit, OnDestroy {
  societes: ISociete[];

  fournisseurs$: Observable<IFournisseur[]>;
  fournisseurInput$ = new Subject<string>();
  fournisseursLoading:Boolean = false;

  clients$: Observable<IClient[]>;
  clientInput$ = new Subject<string>();
  clientsLoading:Boolean = false;

  transporteurs$: Observable<ITransporteur[]>;
  transporteurInput$ = new Subject<string>();
  transporteursLoading:Boolean = false;

  trajets$: Observable<ITrajet[]>;
  trajetInput$ = new Subject<string>();
  trajetsLoading:Boolean = false;

  reportingAchatForm = new FormGroup({
      societe: new FormControl(),
      client: new FormControl(),
      fournisseur: new FormControl(),
      trajet: new FormControl(),
      transporteur: new FormControl(),
      dateDebut: new FormControl(),
      dateFin: new FormControl()
    });

  recapitulatifAchats: ILivraison[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected reportingService: ReportingService,
    protected societeService: SocieteService,
    protected fournisseurService: FournisseurService,
    protected clientService: ClientService,
    protected transporteurService: TransporteurService,
    protected trajetService: TrajetService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.recapitulatifAchats = [];
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
    this.reportingAchatForm.get('dateDebut').setValue(defaultDateDebut);
    const defaultDateFin = moment(new Date());
    this.reportingAchatForm.get('dateFin').setValue(defaultDateFin);
  }

  loadAll() {
    this.reportingService
      .getReportingAchatTrajet(this.buildReportingRequest())
      .subscribe((res: HttpResponse<ILivraison[]>) => {
        this.recapitulatifAchats = [];
        const data:ILivraison[] = res.body;
        for (let i = 0; i < data.length; i++) {
          this.recapitulatifAchats.push(data[i]);
        }
      });
    this.societeService
            .query()
            .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loadClients();
    this.loadFournisseurs();
    this.loadTrajets();
    this.loadTransporteurs();  
  }

  private buildReportingRequest(): any {
    const reportingRequest = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    }
    if(this.reportingAchatForm.get('societe').value){
      reportingRequest['societeId'] = this.reportingAchatForm.get('societe').value.id;
    }
    if(this.reportingAchatForm.get('client').value){
      reportingRequest['clientId'] = this.reportingAchatForm.get('client').value.id;
    }
    if(this.reportingAchatForm.get('fournisseur').value){
      reportingRequest['fournisseurId'] = this.reportingAchatForm.get('fournisseur').value.id;
    }
    if(this.reportingAchatForm.get('trajet').value){
      reportingRequest['trajetId'] = this.reportingAchatForm.get('trajet').value.id;
    }
    if(this.reportingAchatForm.get('transporteur').value){
      reportingRequest['transporteurId'] = this.reportingAchatForm.get('transporteur').value.id;
    }
    if(this.reportingAchatForm.get('dateDebut').value){
      reportingRequest['dateDebut'] = format(this.reportingAchatForm.get('dateDebut').value);
    }
    if(this.reportingAchatForm.get('dateFin').value){
      reportingRequest['dateFin'] = format(this.reportingAchatForm.get('dateFin').value);
    }
    return reportingRequest;
  }

  reset() {
    this.page = 0;
    this.recapitulatifAchats = [];
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

  trackId(index: number, item: IRecapitulatifAchat) {
    return item.dateBonCommande.unix + '' + item.numeroBonCommande + item.codeProduit;
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

  protected paginateRecapitulatifAchats(data: ILivraison[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.recapitulatifAchats.push(data[i]);
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
                      this.fournisseurService
                          .query({'nom.contains': nom})
                          .pipe(map((resp: HttpResponse<IFournisseur[]>) => resp.body), catchError(() => of([])))
                  ),
                  tap(() => (this.fournisseursLoading = false))
              )
          );
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

    private loadTrajets(){
      this.trajets$ = concat(
              of([]), // default items
              this.trajetInput$.pipe(
                  startWith(''),
                  debounceTime(500),
                  distinctUntilChanged(),
                  tap(() => (this.trajetsLoading = true)),
                  switchMap(nom =>
                      this.trajetService
                          .query({'description.contains': nom})
                          .pipe(map((resp: HttpResponse<ITrajet[]>) => resp.body), catchError(() => of([])))
                  ),
                  tap(() => (this.trajetsLoading = false))
              )
          );
    }

    trackFournisseurById(index: number, item: IFournisseur) {
      return item.id;
    }

    trackClientById(index: number, item: IClient) {
      return item.id;
    }

    trackTransporteurById(index: number, item: ITransporteur) {
      return item.id;
    }

    trackTrajetById(index: number, item: ITrajet) {
      return item.id;
    }
}
