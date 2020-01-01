import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransporteur } from 'app/shared/model/transporteur.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TransporteurService } from './transporteur.service';
import { TransporteurDeleteDialogComponent } from './transporteur-delete-dialog.component';

@Component({
  selector: 'jhi-transporteur',
  templateUrl: './transporteur.component.html'
})
export class TransporteurComponent implements OnInit, OnDestroy {
  transporteurs: ITransporteur[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected transporteurService: TransporteurService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.transporteurs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.transporteurService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITransporteur[]>) => this.paginateTransporteurs(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.transporteurs = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTransporteurs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransporteur) {
    return item.id;
  }

  registerChangeInTransporteurs() {
    this.eventSubscriber = this.eventManager.subscribe('transporteurListModification', () => this.reset());
  }

  delete(transporteur: ITransporteur) {
    const modalRef = this.modalService.open(TransporteurDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transporteur = transporteur;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTransporteurs(data: ITransporteur[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.transporteurs.push(data[i]);
    }
  }
}
