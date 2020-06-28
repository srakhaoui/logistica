import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormGroup, FormControl } from '@angular/forms';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';
import { Subscription, Observable, Subject, of, concat } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { format } from 'app/shared/util/date-util';

import { ILivraison } from 'app/shared/model/livraison.model';
import { IProduit } from 'app/shared/model/produit.model';
import { ISociete } from 'app/shared/model/societe.model';
import { IClient } from 'app/shared/model/client.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LivraisonService } from './livraison.service';
import { LivraisonDeleteDialogComponent } from './livraison-delete-dialog.component';
import { ClientService } from 'app/entities/client/client.service';
import { ProduitService } from 'app/entities/produit/produit.service';
import { SocieteService } from 'app/entities/societe/societe.service';

@Component({
  selector: 'jhi-livraison',
  templateUrl: './livraison.component.html'
})
export class LivraisonComponent implements OnInit, OnDestroy {
  societes: ISociete[];

  clients$: Observable<IClient[]>;
  clientInput$ = new Subject<string>();
  clientsLoading:Boolean = false;

  produits$: Observable<IProduit[]>;
  produitInput$ = new Subject<string>();
  produitsLoading:Boolean = false;

  livraisons: ILivraison[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;
  isSearching: Boolean = false;

  reportingForm = new FormGroup({
        societe: new FormControl(),
        client: new FormControl(),
        produit: new FormControl(),
        dateDebut: new FormControl(),
        dateFin: new FormControl()
      });

  constructor(
    protected livraisonService: LivraisonService,
    protected societeService: SocieteService,
    protected clientService: ClientService,
    protected produitService: ProduitService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.livraisons = [];
    this.initForm();
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'dateBonCaisse';
    this.reverse = false;
  }

  private initForm(){
    const defaultDateDebut = moment(new Date());
    defaultDateDebut.set("day", -7);
    this.reportingForm.get('dateDebut').setValue(defaultDateDebut);
    const defaultDateFin = moment(new Date());
    this.reportingForm.get('dateFin').setValue(defaultDateFin);
  }

  loadAll() {
    this.loadSocietes();
    this.loadClients();
    this.loadProduits();
    this.search();
  }

  search(){
    const reportingRequest = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    }
    if(this.reportingForm.get('societe').value){
      reportingRequest['societeFacturationId.equals'] = this.reportingForm.get('societe').value.id;
    }
    if(this.reportingForm.get('client').value){
      reportingRequest['clientId.equals'] = this.reportingForm.get('client').value.id;
    }
    if(this.reportingForm.get('produit').value){
      reportingRequest['produitId.equals'] = this.reportingForm.get('produit').value.id;
    }
    if(this.reportingForm.get('dateDebut').value){
      reportingRequest['dateBonCaisse.greaterThanOrEqual'] = format(this.reportingForm.get('dateDebut').value);
    }
    if(this.reportingForm.get('dateFin').value){
      reportingRequest['dateBonCaisse.lessThanOrEqual'] = format(this.reportingForm.get('dateFin').value);
    }
    this.isSearching = true;
    this.livraisonService
          .query(reportingRequest)
          .subscribe((res: HttpResponse<ILivraison[]>) => {
            this.isSearching = false;
            this.paginateLivraisons(res.body, res.headers);
          });
  }

  reset() {
    this.page = 0;
    this.livraisons = [];
    this.search();
  }

  loadPage(page) {
    this.page = page;
    this.search();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInLivraisons();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILivraison) {
    return item.id;
  }

  registerChangeInLivraisons() {
    this.eventSubscriber = this.eventManager.subscribe('livraisonListModification', () => this.reset());
  }

  delete(livraison: ILivraison) {
    const modalRef = this.modalService.open(LivraisonDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.livraison = livraison;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLivraisons(data: ILivraison[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.livraisons.push(data[i]);
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

    private loadSocietes(){
      this.societeService
        .query()
        .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    }

    protected onError(errorMessage: string) {
      this.jhiAlertService.error(errorMessage, null, null);
    }
}
