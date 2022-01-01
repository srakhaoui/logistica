import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable, Subject, of, concat } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IRecapitulatifStock } from 'app/shared/model/recapitulatif-stock.model';
import { IDepot } from 'app/shared/model/depot.model';
import { ReportingService } from '../reporting.service';
import { DepotService } from 'app/entities/depot/depot.service';
import { FormGroup, FormControl } from '@angular/forms';
import { startWith, debounceTime, distinctUntilChanged, tap, switchMap, catchError, map } from 'rxjs/operators';

@Component({
  selector: 'jhi-reporting-stock',
  templateUrl: './reporting-stock.component.html'
})
export class ReportingStockComponent implements OnInit, OnDestroy {

  depots$: Observable<IDepot[]>;
  depotInput$ = new Subject<string>();
  depotsLoading:Boolean = false;

  isSearching: boolean;

  reportingStockForm = new FormGroup({
      depot: new FormControl()
    });

  recapitulatifStocks: IRecapitulatifStock[];
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;



  constructor(
    protected reportingService: ReportingService,
    protected depotService: DepotService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.recapitulatifStocks = [];
    this.initForm();
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'depot';
    this.reverse = false;
    this.isSearching = false;
  }

  private initForm() {
  }

  loadAll() {
    this.loadDepots();
    this.search();
  }

  search(){
      this.isSearching = true;
      this.reportingService
        .getReportingStock(this.buildReportingRequest())
        .subscribe((res: HttpResponse<IRecapitulatifStock[]>) => {
          this.isSearching = false;
          this.paginateRecapitulatifStocks(res.body);
        });
  }

  private buildReportingRequest(): any {
    const reportingRequest = {
      page: this.page,
      size: this.itemsPerPage,
      sort: this.sort()
    }
    if(this.reportingStockForm.get('depot').value){
      reportingRequest['depot'] = this.reportingStockForm.get('depot').value;
    }
    return reportingRequest;
  }

  reset() {
    this.page = 0;
    this.recapitulatifStocks = [];
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

  trackId(index: number, item: IRecapitulatifStock) {
    return item.depot;
  }

  sort() {
    return [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected paginateRecapitulatifStocks(data: IRecapitulatifStock[]) {
    for (let i = 0; i < data.length; i++) {
      this.recapitulatifStocks.push(data[i]);
    }
  }

  private loadDepots(){
    this.depots$ = concat(
            of([]), // default items
            this.depotInput$.pipe(
                startWith(''),
                debounceTime(500),
                distinctUntilChanged(),
                tap(() => (this.depotsLoading = true)),
                switchMap(nom =>
                    this.depotService
                        .query({'nom.contains': nom})
                        .pipe(map((resp: HttpResponse<IDepot[]>) => resp.body), catchError(() => of([])))
                ),
                tap(() => (this.depotsLoading = false))
              )
      );
  }

}
