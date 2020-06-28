import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrajet } from 'app/shared/model/trajet.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TrajetService } from './trajet.service';
import { TrajetDeleteDialogComponent } from './trajet-delete-dialog.component';

@Component({
  selector: 'jhi-trajet',
  templateUrl: './trajet.component.html'
})
export class TrajetComponent implements OnInit, OnDestroy {
  trajets: ITrajet[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected trajetService: TrajetService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.trajets = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.trajetService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITrajet[]>) => this.paginateTrajets(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.trajets = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTrajets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITrajet) {
    return item.id;
  }

  registerChangeInTrajets() {
    this.eventSubscriber = this.eventManager.subscribe('trajetListModification', () => this.reset());
  }

  delete(trajet: ITrajet) {
    const modalRef = this.modalService.open(TrajetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trajet = trajet;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTrajets(data: ITrajet[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.trajets.push(data[i]);
    }
  }
}
