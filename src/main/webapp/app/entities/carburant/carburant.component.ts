import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICarburant } from 'app/shared/model/carburant.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CarburantService } from './carburant.service';
import { CarburantDeleteDialogComponent } from './carburant-delete-dialog.component';

@Component({
  selector: 'jhi-carburant',
  templateUrl: './carburant.component.html'
})
export class CarburantComponent implements OnInit, OnDestroy {
  carburants: ICarburant[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected carburantService: CarburantService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.carburants = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.carburantService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICarburant[]>) => this.paginateCarburants(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.carburants = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCarburants();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICarburant) {
    return item.id;
  }

  registerChangeInCarburants() {
    this.eventSubscriber = this.eventManager.subscribe('carburantListModification', () => this.reset());
  }

  delete(carburant: ICarburant) {
    const modalRef = this.modalService.open(CarburantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.carburant = carburant;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCarburants(data: ICarburant[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.carburants.push(data[i]);
    }
  }
}
