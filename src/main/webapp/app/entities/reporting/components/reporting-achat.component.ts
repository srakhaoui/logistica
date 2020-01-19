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

@Component({
  selector: 'jhi-reporting-achat',
  templateUrl: './reporting-achat.component.html'
})
export class ReportingAchatComponent implements OnInit, OnDestroy {
  societes: ISociete[];
  fournisseurs$: Observable<IFournisseur[]>;
  fournisseurInput$ = new Subject<string>();
  fournisseursLoading:Boolean = false;

  reportingAchatForm = new FormGroup({
      fournisseur: new FormControl(),
      societe: new FormControl()
    });

  recapitulatifAchats: IRecapitulatifAchat[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;



  constructor(
    protected reportingService: ReportingService,
    protected fournisseurService: FournisseurService,
    protected societeService: SocieteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.recapitulatifAchats = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'dateBonCommande';
    this.reverse = true;
  }

  loadAll() {
    this.reportingService
      .getReportingAchat(this.buildReportingRequest())
      .subscribe((res: HttpResponse<IRecapitulatifAchat[]>) => {
        this.recapitulatifAchats = [];
        const data:IRecapitulatifAchat[] = res.body;
        for (let i = 0; i < data.length; i++) {
          this.recapitulatifAchats.push(data[i]);
        }
      });
    this.societeService
            .query()
            .subscribe((res: HttpResponse<ISociete[]>) => (this.societes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.loadFournisseurs();
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
    if(this.reportingAchatForm.get('fournisseur').value){
      reportingRequest['fournisseurId'] = this.reportingAchatForm.get('fournisseur').value.id;
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
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRecapitulatifAchat) {
    return item.dateBonCommande.unix + '' + item.numeroBonCommande + item.codeProduit;
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

  protected paginateRecapitulatifAchats(data: IRecapitulatifAchat[], headers: HttpHeaders) {
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
}
