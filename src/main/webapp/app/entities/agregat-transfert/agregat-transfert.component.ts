import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAgregatTransfert } from 'app/shared/model/agregat-transfert.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AgregatTransfertService } from './agregat-transfert.service';
import { AgregatTransfertDeleteDialogComponent } from './agregat-transfert-delete-dialog.component';

@Component({
  selector: 'jhi-agregat-transfert',
  templateUrl: './agregat-transfert.component.html'
})
export class AgregatTransfertComponent implements OnInit, OnDestroy {
  agregatTransferts: IAgregatTransfert[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected agregatTransfertService: AgregatTransfertService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.agregatTransferts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.agregatTransfertService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAgregatTransfert[]>) => this.paginateAgregatTransferts(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.agregatTransferts = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAgregatTransferts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAgregatTransfert) {
    return item.id;
  }

  registerChangeInAgregatTransferts() {
    this.eventSubscriber = this.eventManager.subscribe('agregatTransfertListModification', () => this.reset());
  }

  delete(agregatTransfert: IAgregatTransfert) {
    const modalRef = this.modalService.open(AgregatTransfertDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.agregatTransfert = agregatTransfert;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAgregatTransferts(data: IAgregatTransfert[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.agregatTransferts.push(data[i]);
    }
  }
}
