import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGasoilTransfert } from 'app/shared/model/gasoil-transfert.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GasoilTransfertService } from './gasoil-transfert.service';
import { GasoilTransfertDeleteDialogComponent } from './gasoil-transfert-delete-dialog.component';

@Component({
  selector: 'jhi-gasoil-transfert',
  templateUrl: './gasoil-transfert.component.html'
})
export class GasoilTransfertComponent implements OnInit, OnDestroy {
  gasoilTransferts: IGasoilTransfert[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected gasoilTransfertService: GasoilTransfertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.gasoilTransferts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.gasoilTransfertService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGasoilTransfert[]>) => this.paginateGasoilTransferts(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.gasoilTransferts = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGasoilTransferts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGasoilTransfert) {
    return item.id;
  }

  registerChangeInGasoilTransferts() {
    this.eventSubscriber = this.eventManager.subscribe('gasoilTransfertListModification', () => this.reset());
  }

  delete(gasoilTransfert: IGasoilTransfert) {
    const modalRef = this.modalService.open(GasoilTransfertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gasoilTransfert = gasoilTransfert;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGasoilTransferts(data: IGasoilTransfert[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.gasoilTransferts.push(data[i]);
    }
  }
}
