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
import { format } from 'app/shared/util/date-util';

@Component({
  selector: 'jhi-reporting-achat-trajet',
  templateUrl: './reporting-achat-trajet.component.html'
})
export class ReportingAchatTrajetComponent implements OnInit, OnDestroy {
  societes: ISociete[];

  reportingAchatForm = new FormGroup({
      societe: new FormControl(),
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
    this.predicate = 'fournisseur';
    this.reverse = true;
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
}
