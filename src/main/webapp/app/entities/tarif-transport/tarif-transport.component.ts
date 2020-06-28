import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITarifTransport } from 'app/shared/model/tarif-transport.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TarifTransportService } from './tarif-transport.service';
import { TarifTransportDeleteDialogComponent } from './tarif-transport-delete-dialog.component';

@Component({
  selector: 'jhi-tarif-transport',
  templateUrl: './tarif-transport.component.html'
})
export class TarifTransportComponent implements OnInit, OnDestroy {
  tarifTransports: ITarifTransport[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected tarifTransportService: TarifTransportService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tarifTransports = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = false;
  }

  loadAll() {
    this.tarifTransportService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITarifTransport[]>) => this.paginateTarifTransports(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.tarifTransports = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTarifTransports();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITarifTransport) {
    return item.id;
  }

  registerChangeInTarifTransports() {
    this.eventSubscriber = this.eventManager.subscribe('tarifTransportListModification', () => this.reset());
  }

  delete(tarifTransport: ITarifTransport) {
    const modalRef = this.modalService.open(TarifTransportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tarifTransport = tarifTransport;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTarifTransports(data: ITarifTransport[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.tarifTransports.push(data[i]);
    }
  }
}
