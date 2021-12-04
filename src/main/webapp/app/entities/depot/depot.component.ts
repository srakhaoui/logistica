import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDepot } from 'app/shared/model/depot.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DepotService } from './depot.service';
import { DepotDeleteDialogComponent } from './depot-delete-dialog.component';

@Component({
  selector: 'jhi-depot',
  templateUrl: './depot.component.html'
})
export class DepotComponent implements OnInit, OnDestroy {
  depots: IDepot[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected depotService: DepotService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.depots = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.depotService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDepot[]>) => this.paginateDepots(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.depots = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDepots();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDepot) {
    return item.id;
  }

  registerChangeInDepots() {
    this.eventSubscriber = this.eventManager.subscribe('depotListModification', () => this.reset());
  }

  delete(depot: IDepot) {
    const modalRef = this.modalService.open(DepotDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.depot = depot;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDepots(data: IDepot[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.depots.push(data[i]);
    }
  }
}
