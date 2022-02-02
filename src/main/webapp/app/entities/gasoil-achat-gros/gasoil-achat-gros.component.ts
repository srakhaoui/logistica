import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGasoilAchatGros } from 'app/shared/model/gasoil-achat-gros.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GasoilAchatGrosService } from './gasoil-achat-gros.service';
import { GasoilAchatGrosDeleteDialogComponent } from './gasoil-achat-gros-delete-dialog.component';

@Component({
  selector: 'jhi-gasoil-achat-gros',
  templateUrl: './gasoil-achat-gros.component.html'
})
export class GasoilAchatGrosComponent implements OnInit, OnDestroy {
  gasoilAchatGros: IGasoilAchatGros[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected gasoilAchatGrosService: GasoilAchatGrosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.gasoilAchatGros = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.gasoilAchatGrosService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGasoilAchatGros[]>) => this.paginateGasoilAchatGros(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.gasoilAchatGros = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGasoilAchatGros();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGasoilAchatGros) {
    return item.id;
  }

  registerChangeInGasoilAchatGros() {
    this.eventSubscriber = this.eventManager.subscribe('gasoilAchatGrosListModification', () => this.reset());
  }

  delete(gasoilAchatGros: IGasoilAchatGros) {
    const modalRef = this.modalService.open(GasoilAchatGrosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gasoilAchatGros = gasoilAchatGros;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGasoilAchatGros(data: IGasoilAchatGros[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.gasoilAchatGros.push(data[i]);
    }
  }
}
